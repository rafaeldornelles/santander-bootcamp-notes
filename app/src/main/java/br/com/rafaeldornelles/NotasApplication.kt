package br.com.rafaeldornelles

import android.app.Application

class NotasApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        NotasApplication.instance = this
    }

    companion object{
        lateinit var instance: NotasApplication
    }
}