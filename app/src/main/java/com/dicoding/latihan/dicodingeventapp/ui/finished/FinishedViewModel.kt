package com.dicoding.latihan.dicodingeventapp.ui.finished

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.latihan.dicodingeventapp.data.response.EventResponse
import com.dicoding.latihan.dicodingeventapp.data.response.ListEventsItem
import com.dicoding.latihan.dicodingeventapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel : ViewModel() {

    private val _eventList = MutableLiveData<List<ListEventsItem>>()
    val eventList: LiveData<List<ListEventsItem>> = _eventList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FinishedViewModel"
    }

    init {
        findFinishedEvent()
    }

    private fun findFinishedEvent() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getEvent(0)
        client.enqueue(object: Callback<EventResponse>{
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _eventList.value = response.body()?.listEvents
                } else {
                    Log.d(TAG, "onResponseFail: ${response.message()}" )
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure: ${t.message}" )
            }

        })
    }
}