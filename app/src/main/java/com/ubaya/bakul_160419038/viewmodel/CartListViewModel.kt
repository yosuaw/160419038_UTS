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
import com.ubaya.bakul_160419038.model.CartMenu

class CartListViewModel(application: Application): AndroidViewModel(application) {
    val cartMenusLiveData = MutableLiveData<ArrayList<CartMenu>>()
    val cartMenusLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh(username: String) {
        cartMenusLoadErrorLiveData.value = false
        loadingLiveData.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://bakul-fake-api.herokuapp.com/cart?username=$username"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<CartMenu>>() {}.type
                val result = Gson().fromJson<ArrayList<CartMenu>>(it, sType)
                cartMenusLiveData.value = result
                loadingLiveData.value = false

                Log.d("showvolley", it)
            },
            {
                loadingLiveData.value = false
                cartMenusLoadErrorLiveData.value = true

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