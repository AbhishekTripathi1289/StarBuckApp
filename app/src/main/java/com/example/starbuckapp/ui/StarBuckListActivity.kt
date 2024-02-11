package com.example.starbuckapp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingwithmitch.daggerhiltplayground.util.DataState
import com.example.starbuckapp.R
import com.example.starbuckapp.databinding.ActivityStarBuckListBinding
import com.example.starbuckapp.models.StarbuckData
import com.example.starbuckapp.utils.Constants
import com.example.starbuckapp.viewmodel.StarBuckViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StarBuckListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStarBuckListBinding
    private val MY_PERMISSIONS_REQUEST_LOCATION_PERMISSION = 101

    private val viewModel: StarBuckViewModel by viewModels()
    private lateinit var adapter: StartBuckListAdapter
    private lateinit var focusedLocationProviderClient: FusedLocationProviderClient

    private var selectionCallback: (StarbuckData) -> Unit =  { starbuck ->
    var intent = Intent(this@StarBuckListActivity, StarBuckMapsActivity::class.java).apply {
        putExtra(Constants.LATITUDE, starbuck.Latitude)
        putExtra(Constants.LONGITUDE, starbuck.Longitude)
    }
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_star_buck_list)

        binding = ActivityStarBuckListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareUi()
        checkForLocationPermission()
        addObserver()
        setRecyclerViewAdapter()
    }


    private fun prepareUi() {
        val bar: ActionBar? = supportActionBar
        bar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#E94F61")))
    }

    private fun checkForLocationPermission() {

        var permissionCheck = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION_PERMISSION
            )
        } else
        {
            getLocation()
        }
    }

    private fun getLocation() {

        focusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        focusedLocationProviderClient.lastLocation.addOnSuccessListener { location->
            viewModel.fetchStarBuckList(location.latitude, location.longitude)

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (permissions != null) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION_PERMISSION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()

            } else
            {
                binding.group.visibility = View.GONE
                binding.erroText.visibility = View.VISIBLE
                binding.erroText.text = "Please give location permission \nfrom setting."
            }
        }
    }

    private fun setRecyclerViewAdapter() {
        adapter = StartBuckListAdapter(arrayListOf(), selectionCallback)
        binding.recyclerView.layoutManager = LinearLayoutManager(this).apply { orientation  = LinearLayoutManager.VERTICAL }
        binding.recyclerView.adapter = adapter
    }

    private fun addObserver() {
        viewModel._starBucksLiveData.observe(this) { starBucksList ->
            when (starBucksList) {
                is DataState.Success<ArrayList<StarbuckData>> -> {
                    displayProgressBar(false)
                    binding.erroText.visibility = View.GONE
                    adapter.updateList(starBucksList.data)
                    adapter.notifyDataSetChanged()
                }

                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(starBucksList.message)
                }

                is DataState.Loading -> {
                    displayProgressBar(true)
                }

                else -> {}
            }
        }

    }

    private fun displayProgressBar(isDisplayed: Boolean){
        binding.erroText.visibility = View.GONE
        binding.group.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(message: String?){

        binding.erroText.visibility = View.VISIBLE
        if(message != null)
            binding.erroText.text = message
        else binding.erroText.text = "Unknown error."
    }

}