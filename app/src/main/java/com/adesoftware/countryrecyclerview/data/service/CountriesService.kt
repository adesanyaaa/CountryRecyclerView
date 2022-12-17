package com.adesoftware.countryrecyclerview.data.service

import com.adesoftware.countryrecyclerview.data.api.ApiConstants.BASE_URL
import com.adesoftware.countryrecyclerview.data.api.CountriesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountriesService {
    private val url = BASE_URL

    fun getCountriesService(): CountriesApi {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }
}