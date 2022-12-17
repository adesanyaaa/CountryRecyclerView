package com.adesoftware.countryrecyclerview.data.api

import com.adesoftware.countryrecyclerview.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountriesApi {
    @GET(ApiConstants.END_POINTS)
    suspend fun getCountries(): Response<List<Country>>
}