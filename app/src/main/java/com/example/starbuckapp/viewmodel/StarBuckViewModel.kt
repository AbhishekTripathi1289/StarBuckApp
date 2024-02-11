package com.example.starbuckapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithmitch.daggerhiltplayground.util.DataState
import com.example.starbuckapp.models.StarbuckData
import com.example.starbuckapp.repo.StartBuckRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarBuckViewModel @Inject constructor(val starBucksRepo: StartBuckRepo) : ViewModel() {
    private val starBucksLiveData: MutableLiveData<DataState<ArrayList<StarbuckData>>> = MutableLiveData()
    val _starBucksLiveData
        get() = starBucksLiveData


    fun fetchStarBuckList(lat: Double, lang: Double)
    {
        viewModelScope.launch {
            starBucksRepo.getStarBuckList(lat, lang).onEach {
                starBucksLiveData.value = it
            }.launchIn(viewModelScope)
        }
    }
}