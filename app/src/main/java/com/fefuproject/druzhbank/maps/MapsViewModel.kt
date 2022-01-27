package com.fefuproject.druzhbank.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fefuproject.shared.account.domain.models.BankomatModel
import com.fefuproject.shared.account.domain.usecase.GetBankomatsUseCase
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.data.geojson.GeoJsonLayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val getBankomatsUseCase: GetBankomatsUseCase
) : ViewModel() {

    private var _bank_list: List<BankomatModel>? = null

    private var googleMap1: GoogleMap? = null

    init {
        viewModelScope.launch {
            _bank_list = getBankomatsUseCase.invoke()
            init_map()

        }
    }

    fun onMapReady(googleMap: GoogleMap) {
        googleMap1 = googleMap
        viewModelScope.launch { init_map() }
    }
  fun init_map() {
      if (_bank_list==null || googleMap1==null) return
        for (i in _bank_list!!) {
            val cord = i.coordinates
            val geoJsonData = JSONObject(cord)
            val layer = GeoJsonLayer(
                googleMap1,
                geoJsonData
            )
            layer.addLayerToMap()
        }
        val vdk = LatLng(
            43.09540407292499,
            131.89900696277616
        )
        googleMap1?.moveCamera(CameraUpdateFactory.newLatLng(vdk))
        googleMap1?.moveCamera(CameraUpdateFactory.zoomTo(12f))

    }

}
