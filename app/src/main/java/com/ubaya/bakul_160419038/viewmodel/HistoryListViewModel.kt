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
import com.ubaya.bakul_160419038.model.History

class HistoryListViewModel(application: Application): AndroidViewModel(application) {
    val historiesLiveData = MutableLiveData<ArrayList<History>>()
    val historiesLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh(username: String) {
        historiesLoadErrorLiveData.value = false
        loadingLiveData.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://bakul-fake-api.herokuapp.com/history?username=$username"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<History>>() {}.type
                val result = Gson().fromJson<ArrayList<History>>(it, sType)
                historiesLiveData.value = result
                loadingLiveData.value = false

                Log.d("showvolley", it)
            },
            {
                loadingLiveData.value = false
                historiesLoadErrorLiveData.value = true

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