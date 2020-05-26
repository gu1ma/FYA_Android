package io.klineapps.fya.fragments


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback


import java.lang.Exception

import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.klineapps.fya.App
import io.klineapps.fya.R
import io.klineapps.fya.model.Sport
import io.klineapps.fya.model.SportResponse


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraMoveStartedListener {

    private lateinit var mMap: GoogleMap
    private var currentLocationMarker:Marker? = null
    private var fab:FloatingActionButton? = null

    companion object {
        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fab = view.findViewById(R.id.fab)
        configFloatActionBar()
        configFloatActionBar()
    }

    private fun configFloatActionBar() {
        fab?.hide()
        fab?.setOnClickListener(View.OnClickListener {

            if(currentLocationMarker != null) {
                val latLng = currentLocationMarker?.position
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f))
                it.animate()
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            it.visibility = View.GONE
                        }
                    })
            }
        })
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnCameraMoveStartedListener(this)
        mMap.setOnMarkerClickListener(this)
        try {
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context,R.raw.style_json))
        }catch (e: Exception){
            e.printStackTrace()
        }

        setUserLocationMarker()
        setupSportsMarkers()

    }

    private fun setupSportsMarkers() {
        SportResponse.getInstance().sports.forEachIndexed { _, sport ->

            val latitude = sport.latitude!!.toDouble()
            val longitude = sport.longitude!!.toDouble()

            val latLng = LatLng(latitude, longitude)

            val marker = mMap.addMarker(MarkerOptions()
                .position(latLng)
                .title(sport.dsSport)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_mapa))
            )

            marker.tag = sport

        }
    }



    override fun onMarkerClick(p0: Marker?): Boolean {
        if (p0?.tag != null) {
            val co = p0.tag as Sport
            println("Address: ${co.address} ")
            val coworkingLoc = Location("")
            coworkingLoc.latitude = co.latitude!!.toDouble()
            coworkingLoc.longitude = co.longitude!!.toDouble()

            //val currentLoc = Location("")
        }
        p0?.showInfoWindow()
        return true
    }

    override fun onCameraMoveStarted(p0: Int) {
        fab?.show()
    }

    private fun setUserLocationMarker(){
        val location = App.instance!!.getLocation()
        val latitude = location.latitude
        val longitude = location.longitude
        val latLng = LatLng(latitude,longitude)
        if (currentLocationMarker == null) {
            setupCurrentLocationMarker(latLng)
        }
    }

    private fun setupCurrentLocationMarker(latLng: LatLng){
        val markerOptions = MarkerOptions()
            .position(latLng)
            .title("Meu local")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_localizacao_atual))
        currentLocationMarker = mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13.0f))
    }



}

