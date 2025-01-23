package com.dicoding.latihan.dicodingeventapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.latihan.dicodingeventapp.data.response.DetailEventResponse
import com.dicoding.latihan.dicodingeventapp.data.response.Event
import com.dicoding.latihan.dicodingeventapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventViewModel: ViewModel() {
    private val _eventDetail = MutableLiveData<Event?>()
    val eventDetail: LiveData<Event?> = _eventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailEventViewModel"
    }

    fun getDetailEvent(eventId: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailEvent(eventId)
        client.enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _eventDetail.value = response.body()?.event
                }else {
                    Log.e(TAG, "onResponseFail: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure: ${t.message}" )
            }
        })
    }
}