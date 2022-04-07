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
import com.ubaya.bakul_160419038.model.User

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    val userLiveData = MutableLiveData<User>()
    val userLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun fetch(username : String) {
        userLoadErrorLiveData.value = false
        loadingLiveData.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://bakul-fake-api.herokuapp.com/user?username=$username"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<User>>() {}.type
                val result = Gson().fromJson<ArrayList<User>>(it, sType)
                userLiveData.value = result[0]
                loadingLiveData.value = false

                Log.d("showvolley", it)
            },
            {
                loadingLiveData.value = false
                userLoadErrorLiveData.value = true

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