package com.example.starbuckapp.ui

object DistanceCalculator {
    private const val EARTH_RADIUS = 6371.0

    fun calculateDistance(
        startLatitude: Double, startLongitude: Double,
        endLatitude: Double, endLongitude: Double
    ): Double {
        val dLat = Math.toRadians(endLatitude - startLatitude)
        val dLon = Math.toRadians(endLongitude - startLongitude)
        val a =
            Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(startLatitude)) * Math.cos(
                Math.toRadians(endLatitude)
            ) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return EARTH_RADIUS * c
    }

}