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
import com.ubaya.bakul_160419038.model.FAQ

class FAQViewModel(application: Application): AndroidViewModel(application) {
    val faqLiveData = MutableLiveData<ArrayList<FAQ>>()
    val faqLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        faqLoadErrorLiveData.value = false
        loadingLiveData.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://bakul-fake-api.herokuapp.com/faq"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<FAQ>>() {}.type
                val result = Gson().fromJson<ArrayList<FAQ>>(it, sType)
                faqLiveData.value = result
                loadingLiveData.value = false

                Log.d("showvolley", it)
            },
            {
                loadingLiveData.value = false
                faqLoadErrorLiveData.value = true

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