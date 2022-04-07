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
import com.ubaya.bakul_160419038.model.Menu
import com.ubaya.bakul_160419038.model.Place

class MenuListViewModel(application: Application): AndroidViewModel(application) {
    val placeLiveData = MutableLiveData<Place>()
    val menusLiveData = MutableLiveData<ArrayList<Menu>>()
    val menusLoadErrorLiveData = MutableLiveData<Boolean>()
    val menusLoadingLiveData = MutableLiveData<Boolean>()
    val TAG1 = "volleyTag1"
    val TAG2 = "volleyTag2"
    private var queue: RequestQueue? = null

    fun fetch(placeID : Int) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://bakul-fake-api.herokuapp.com/place/$placeID"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val result = Gson().fromJson(it, Place::class.java)
                placeLiveData.value = result

                Log.d("showvolleyplace", it)
            },
            {
                Log.d("errorvolleyplace", it.toString())
            }
        ).apply {
            tag = TAG1
        }
        queue?.add(stringRequest)
    }

    fun refresh(placeID: Int) {
        menusLoadErrorLiveData.value = false
        menusLoadingLiveData.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://bakul-fake-api.herokuapp.com/menu?place_id=$placeID"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Menu>>() {}.type
                val result = Gson().fromJson<ArrayList<Menu>>(it, sType)
                menusLiveData.value = result
                menusLoadingLiveData.value = false

                Log.d("showvolleymenu", it)
            },
            {
                menusLoadingLiveData.value = false
                menusLoadErrorLiveData.value = true

                Log.d("errorvolleymenu", it.toString())
            }
        ).apply {
            tag = TAG2
        }
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG1)
        queue?.cancelAll(TAG2)
    }
}