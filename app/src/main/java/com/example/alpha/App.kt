package com.example.alpha

import android.app.Application
import android.content.Context
import com.example.alpha.data.Repository
import com.example.alpha.data.api.ApiClient

class App : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
    override fun onCreate() {
        super.onCreate()

    }



}