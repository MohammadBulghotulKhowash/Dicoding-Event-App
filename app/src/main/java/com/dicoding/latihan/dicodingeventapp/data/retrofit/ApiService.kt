package com.dicoding.latihan.dicodingeventapp.data.retrofit

import com.dicoding.latihan.dicodingeventapp.data.response.DetailEventResponse
import com.dicoding.latihan.dicodingeventapp.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("events")
    fun getEvent(
        @Query("active") active: Int
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String
    ): Call<DetailEventResponse>

    @GET("events")
    fun getEventActiveLimit(
        @Query("active") active: Int,
        @Query("limit") limit : Int
    ): Call<EventResponse>

    @GET("events")
    fun getEventSearch(
        @Query("active") active: Int,
        @Query("q") q: String
    ): Call<EventResponse>
}