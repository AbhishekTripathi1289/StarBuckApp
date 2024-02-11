package com.example.starbuckapp.repo


import com.codingwithmitch.daggerhiltplayground.util.DataState
import com.example.starbuckapp.api.AssetManager
import com.example.starbuckapp.models.StarbuckData
import com.example.starbuckapp.ui.DistanceCalculator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StartBuckRepo @Inject constructor(val assetManager: AssetManager) {

    suspend fun getStarBuckList(lat: Double, lang: Double) : Flow<DataState<ArrayList<StarbuckData>>> {

        return flow {
            try {
                emit(DataState.Loading)
                val response = assetManager.loadAssetFromFile()

                if(response.isNotEmpty())
                {
                    var finalStarbuckList = response.map {
                        it.copy(distance = DistanceCalculator.calculateDistance(lat, lang, it.Latitude, it.Longitude ))
                    }.sortedWith(compareBy { it.distance }).toCollection(ArrayList())

                    emit(DataState.Success(finalStarbuckList))
                }
                else{
                    emit(DataState.Error(message = "Starbucks information not found"))
                }
            }
            catch (e: Exception)
            {
                e.printStackTrace()
                emit(DataState.Error(message = e.message))
            }
        }
    }

}