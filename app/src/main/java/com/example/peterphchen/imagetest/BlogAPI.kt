package com.example.peterphchen.imagetest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BlogAPI {

    @GET("{url}")
    fun getURLs(@Path("url")url:String): Call<ArrayList<String>>

}