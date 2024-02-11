package com.example.starbuckapp.api

import android.content.Context
import com.example.starbuckapp.models.StarbuckData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AssetManager @Inject constructor(@ApplicationContext var context: Context)
{

    fun loadAssetFromFile(): ArrayList<StarbuckData>
    {
        var startBuckList: ArrayList<StarbuckData>
        val inputStream = context.assets.open("starbuck.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        startBuckList = gson.fromJson(json, object : TypeToken<ArrayList<StarbuckData?>?>() {}.type)
       return startBuckList
    }
}