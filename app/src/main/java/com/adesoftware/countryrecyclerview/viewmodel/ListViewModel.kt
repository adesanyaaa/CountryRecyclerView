package com.adesoftware.countryrecyclerview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adesoftware.countryrecyclerview.data.service.CountriesService
import com.adesoftware.countryrecyclerview.model.Country
import kotlinx.coroutines.*

class ListViewModel: ViewModel() {

    private val countriesService = CountriesService.getCountriesService()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler {
        coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }
    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<List<String>?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = countriesService.getCountries()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    countries.value = response.body()
                    countryLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }


    private fun onError(message: String) {
        countryLoadError.value = listOf(message)
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}