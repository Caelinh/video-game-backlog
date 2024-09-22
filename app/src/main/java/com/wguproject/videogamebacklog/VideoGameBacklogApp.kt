package com.wguproject.videogamebacklog

import android.app.Application

class VideoGameBacklogApp: Application(){
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}