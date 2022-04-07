package com.ubaya.bakul_160419038.model

import com.google.gson.annotations.SerializedName

data class Place(val id: Int, val name: String, val rating: Double, val reviews: Int, val address: String,
                 val open: String, val closed: String, @SerializedName("photo_url") val photoURL: String?)

data class Menu(val id: Int, val name: String, val price: Int, val description: String?, val count: Int,
                @SerializedName("photo_url") val photoURL: String?, val place_id: Int)

data class Review(val id: Int, @SerializedName("person_name") val personName: String, val content: String,
                  @SerializedName("photo_url") val photoURL: String?, val place_id: Int)

data class CartMenu(val id: Int, @SerializedName("menu_name") val menuName: String, val price: Int,
                    @SerializedName("total_price") val totalPrice: Int, val count: Int, val notes: String?,
                    @SerializedName("menu_photo_url") val menuPhotoURL: String?)

data class History(val id: Int, @SerializedName("place_name") val placeName: String,
                   @SerializedName("time_at") val timeAt: String,
                   @SerializedName("total_price") val totalPrice: Int,
                   @SerializedName("place_photo_url") val placePhotoURL: String?)

data class HistoryMenu(val history_id: Int, @SerializedName("menu_name") val menuName: String,
                       val price: Int, @SerializedName("menu_photo_url") val menuPhotoURL: String?,
                       @SerializedName("menu_count") val menuCount: Int, val notes: String?,
                       @SerializedName("place_name") val placeName: String,
                       @SerializedName("place_photo_url") val placePhotoURL: String?,
                       @SerializedName("time_at") val timeAt: String)

data class User(val username: String, val password: String, val name: String,
                @SerializedName("phone_number") val phoneNumber: String, val email: String,
                val balance: Int, @SerializedName("photo_url") val photoURL: String?)

data class FAQ(val question: String, val answer: String)
