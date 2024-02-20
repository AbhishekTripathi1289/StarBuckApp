package com.example.starbuckapp.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.starbuckapp.R
import com.example.starbuckapp.databinding.ActivityStarBuckMapsBinding
import com.example.starbuckapp.utils.Constants.LATITUDE
import com.example.starbuckapp.utils.Constants.LONGITUDE
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class StarBuckMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStarBuckMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStarBuckMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareUi()
    }

    private fun prepareUi() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#E94F61")))
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var lat = intent.getDoubleExtra(LATITUDE, 17.42)
        var long = intent.getDoubleExtra(LONGITUDE, 78.45)

        // Add a marker in Sydney and move the camera
        val latlng = LatLng(lat, long)
        mMap.addMarker(MarkerOptions().position(latlng).title("StarBuck"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16f))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}