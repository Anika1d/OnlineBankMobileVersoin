package viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import libs.addNullList
import libs.mergeFromList
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.shared.account.domain.usecase.GetAllInstrumentHistoryUseCase
import com.fefuproject.shared.account.domain.usecase.PayUniversalUseCase
import kotlinx.coroutines.Job
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
    protected var refreshingJob: Job? = null
    protected var currentQuery = ""

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
        refreshingJob = viewModelScope.launch {
            isPageLoading = true
            val originalList = _cardEvents.value //caching in case of failure
            if (_cardEvents.value.isEmpty() || (_cardEvents.value.isNotEmpty() && _cardEvents.value[0] != null)) _cardEvents.value =
                _cardEvents.value.addNullList(pageSize) // add nulls to the list only if it's not in loading state already
            val temp = getAllInstrumentHistoryUseCase(
                preferenceProvider.token!!,
                currentPage,
                pageSize,
                currentQuery
            )?.historyList
            if (temp != null) _cardEvents.value = _cardEvents.value.mergeFromList(temp, pageSize)
            else _cardEvents.value = originalList
            _isRefreshing.value = false
            if (temp != null && temp.size < pageSize) currentPage = -1
            isPageLoading = false
        }
    }

    fun updateQuery(s: String) {
        currentQuery = s
        refresh()
    }

    fun refresh() {
        _isRefreshing.value = true
        currentPage = 0
        if (refreshingJob?.isCompleted == false) refreshingJob!!.cancel()
        if (_cardEvents.value.isNotEmpty() && _cardEvents.value[0] != null) _cardEvents.value =
            listOf() // purge the list only if it's not in loading state already
        else if (_cardEvents.value.isEmpty()) _cardEvents.value =
            _cardEvents.value.addNullList(pageSize) // workaround for init state crash
        isPageLoading = false
        loadNextPage()
    }
}