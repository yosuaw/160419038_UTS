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
import com.ubaya.bakul_160419038.model.HistoryMenu

class HistoryDetailViewModel(application: Application): AndroidViewModel(application) {
    val historyMenuLiveData = MutableLiveData<ArrayList<HistoryMenu>>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun fetch(historyID : Int) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://bakul-fake-api.herokuapp.com/history_menu?history_id=$historyID"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<HistoryMenu>>() {}.type
                val result = Gson().fromJson<ArrayList<HistoryMenu>>(it, sType)
                historyMenuLiveData.value = result

                Log.d("showvolley", it)
            },
            {
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