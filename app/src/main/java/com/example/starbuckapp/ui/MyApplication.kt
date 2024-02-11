package com.example.starbuckapp.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidApp
class MyApplication: Application()
{
    override fun onCreate() {
        super.onCreate()
    }

}