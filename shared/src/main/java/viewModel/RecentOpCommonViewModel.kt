package viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import libs.addNullList
import libs.mergeFromList
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.shared.account.domain.usecase.GetAllInstrumentHistoryUseCase
import com.fefuproject.shared.account.domain.usecase.PayUniversalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.interfaces.IPreferenceProvider
import kotlin.math.absoluteValue

open class RecentOpCommonViewModel constructor(
    private val preferenceProvider: IPreferenceProvider,
    private val getAllInstrumentHistoryUseCase: GetAllInstrumentHistoryUseCase,
    private val payUniversalUseCase: PayUniversalUseCase,
    protected val pageSize: Int,
) : ViewModel() {

    protected var currentPage = 0
    protected var _cardEvents = MutableStateFlow<List<HistoryInstrumentModel?>>(listOf())
    val cardEvents get() = _cardEvents.asStateFlow()
    protected val _isRefreshing = MutableStateFlow(true)
    val isRefreshing get() = _isRefreshing.asStateFlow()

    var isPageLoading = false

    init {
        refresh()
    }

    fun repeatPayment(payment: HistoryInstrumentModel) {
        val cnt = payment.count.toDouble()
        if (isRefreshing.value || cnt > 0) return
        viewModelScope.launch {
            _isRefreshing.value = true
            payUniversalUseCase.invoke(
                payment.source,
                payment.dest,
                cnt.absoluteValue,
                payment.sourceType,
                payment.destType,
                preferenceProvider.token!!
            )
            refresh()
        }
    }

    fun loadNextPage() {
        if (isPageLoading) return
        if (currentPage == -1) return // we've reached the lowest point
        currentPage++
        viewModelScope.launch {
            isPageLoading = true
            val originalList = _cardEvents.value //caching in case of failure
            _cardEvents.value = _cardEvents.value.addNullList(pageSize)
            val temp = getAllInstrumentHistoryUseCase(
                preferenceProvider.token!!,
                currentPage,
                pageSize
            )?.historyList
            if (temp != null) _cardEvents.value = _cardEvents.value.mergeFromList(temp, pageSize)
            else _cardEvents.value = originalList
            _isRefreshing.value = false
            if (cardEvents.value.size - originalList.size < pageSize) currentPage = -1
            isPageLoading = false
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        currentPage = 0
        _cardEvents.value = listOf()
        loadNextPage()
    }
}