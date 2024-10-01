package com.wguproject.videogamebacklog.igdbWrapper.api

import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper



class IGDB {
    private val apiKey = "LyTid97mtE6PnFHSnIRn5rOQdYyo0JR9mq2AtWL7"
    private val proxy = "https://pxatfro29b.execute-api.us-east-1.amazonaws.com/production/v4/"

    init {
        IGDBWrapper.setupProxy(proxy, mapOf("x-api-key" to apiKey))
    }
    val apicalypse = APICalypse()
            .fields("*")
            .limit(10)
            .offset(0)
            .sort("release_dates.date", Sort.ASCENDING)
            .buildQuery()




}