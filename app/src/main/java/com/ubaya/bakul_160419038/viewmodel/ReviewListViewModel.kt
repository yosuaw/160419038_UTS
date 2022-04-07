package com.ubaya.bakul_160419038.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.bakul_160419038.model.Review

class ReviewListViewModel(application: Application): AndroidViewModel(application) {
    val reviewsLiveData = MutableLiveData<ArrayList<Review>>()
    val reviewsLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh(placeID : Int) {
        reviewsLoadErrorLiveData.value = false
        loadingLiveData.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://bakul-fake-api.herokuapp.com/review?place_id=$placeID"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Review>>() {}.type
                val result = Gson().fromJson<ArrayList<Review>>(it, sType)
                reviewsLiveData.value = result
                loadingLiveData.value = false

                Log.d("showvolley", it)
            },
            {
                loadingLiveData.value = false
                reviewsLoadErrorLiveData.value = true

                Log.d("errorvolley", it.toString())
            }
        ).apply {
            tag = TAG
        }
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}