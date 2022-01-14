package com.fefuproject.druzhbank.dirmaps

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.databinding.FragmentMapsBinding
import com.fefuproject.shared.account.domain.models.BankomatModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.data.geojson.GeoJsonLayer
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class MapsFragment(val bank_list: List<BankomatModel>) : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private val callback = OnMapReadyCallback { googleMap ->
        var coord: String = "1"
        for (i in bank_list) {
            coord = i.coordinates
            val geoJsonData = JSONObject(coord)
            val layer = GeoJsonLayer(
                googleMap,
                geoJsonData
            )
            layer.addLayerToMap()
            //       googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))


        }
        val sydney = LatLng(
            43.09540407292499,
            131.89900696277616
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(12f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }
}