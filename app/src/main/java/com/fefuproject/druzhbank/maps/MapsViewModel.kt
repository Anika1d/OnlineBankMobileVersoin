package com.fefuproject.druzhbank.maps

import android.widget.PopupWindow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cocoahero.android.geojson.FeatureCollection
import com.cocoahero.android.geojson.GeoJSON
import com.cocoahero.android.geojson.GeoJSONObject
import com.fefuproject.shared.account.domain.models.BankomatModel
import com.fefuproject.shared.account.domain.usecase.GetBankomatsUseCase
import com.google.android.gms.common.Feature
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.collections.MarkerManager
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

    private var googleMap: GoogleMap? = null

    fun getGoogleMap(): GoogleMap {
        return googleMap!!
    }

    init {
        viewModelScope.launch {
            _bank_list = getBankomatsUseCase.invoke()
            init_map()

        }
    }

    fun onMapReady(_googleMap: GoogleMap) {
        googleMap = _googleMap
        viewModelScope.launch { init_map() }
    }

    fun init_map() {
        if (_bank_list == null || googleMap == null) return
        for (i in _bank_list!!) {
            val cord = i.coordinates
            val geoJsonData = JSONObject(cord)
            val gj: GeoJSONObject = GeoJSON.parse(geoJsonData)
            val arrayCoord =
                (gj as FeatureCollection).features[0].geometry.toJSON().getJSONArray("coordinates")
            val markerOptions = MarkerOptions()
            markerOptions.apply {
                val atm = if (i.is_atm) {
                    "Банкомант"
                } else {
                    "Отделение"
                }
                title(atm + ": " + i.adress)
                snippet(
                    "Работает с " + i.time_start.substring(0, 5) + " до " +
                            i.time_end.substring(0, 5)
                )
                position(
                    LatLng(
                        arrayCoord[1].toString().toDouble(),
                        arrayCoord[0].toString().toDouble()
                    )
                )
            }
            val layer = GeoJsonLayer(
                googleMap,
                geoJsonData,
            )
            layer.map.addMarker(markerOptions)
            layer.addLayerToMap()
        }
        val vdk = LatLng(
            43.09540407292499,
            131.89900696277616
        )
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(vdk))
        googleMap?.moveCamera(CameraUpdateFactory.zoomTo(12f))

    }

    fun focusInBank(cords: String) {
        val cord = cords
        val geoJsonData = JSONObject(cord)
        val gj: GeoJSONObject = GeoJSON.parse(geoJsonData)
        val arrayCoord =
            (gj as FeatureCollection).features[0].geometry.toJSON().getJSONArray("coordinates")
        val googleMap = getGoogleMap()
        val clickerInRecycleViewItem = LatLng(
            arrayCoord[1].toString().toDouble(),
            arrayCoord[0].toString().toDouble()
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(clickerInRecycleViewItem))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(20f))
    }

}




