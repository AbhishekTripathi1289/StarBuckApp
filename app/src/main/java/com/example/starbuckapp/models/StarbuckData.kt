package com.example.starbuckapp.models

data class StarbuckData(
    var store_name: String?,
    var address: String?,
    var City: String,
    var Longitude: Double,
    var Latitude: Double,
    var distance: Double = 0.0
)