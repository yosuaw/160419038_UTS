package com.ubaya.bakul_160419038.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.model.Place
import com.ubaya.bakul_160419038.util.loadImage
import kotlinx.android.synthetic.main.place_list_item.view.*

class PlaceListAdapter(val placeList: ArrayList<Place>) : RecyclerView.
Adapter<PlaceListAdapter.PlaceViewHolder>() {
    class PlaceViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.place_list_item, parent, false)

        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = placeList[position]
        with(holder.view) {
            txtPlaceName.text = place.name
            txtPlaceRating.text = place.rating.toString()
            txtPlaceCountReviews.text = "${place.reviews} Ulasan"
            txtPlaceOpenClosed.text = "${place.open} - ${place.closed}"
            val noImageURL = "https://cdn-icons-png.flaticon.com/512/1829/1829548.png"
            imagePlace.loadImage(place.photoURL ?: noImageURL, progressBarPlaceList)

            cardPlaceList.setOnClickListener {
                val action = MainFragmentDirections.actionMenuList(place.id)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount() = placeList.size

    fun updatePlaceList(newPlaceList: ArrayList<Place>) {
        placeList.clear()
        placeList.addAll(newPlaceList)
        notifyDataSetChanged() //Merefresh tampilan recycler view
    }
}