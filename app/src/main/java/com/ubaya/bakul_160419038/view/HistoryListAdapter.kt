package com.ubaya.bakul_160419038.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.model.History
import com.ubaya.bakul_160419038.util.convertRupiah
import com.ubaya.bakul_160419038.util.loadImage
import kotlinx.android.synthetic.main.history_list_item.view.*

class HistoryListAdapter(val historyList: ArrayList<History>): RecyclerView
.Adapter<HistoryListAdapter.HistoryViewHolder>(){
    class HistoryViewHolder(var view:View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.history_list_item, parent, false)

        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val parser = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm")
        val formatter = java.text.SimpleDateFormat("d MMMM yyyy  |  HH:mm")

        val history = historyList[position]
        val formattedTime = formatter.format(parser.parse(history.timeAt))

        with(holder.view) {
            txtPlaceNameHistory.text = history.placeName
            txtDateTime.text = formattedTime
            txtPriceHistory.text = history.totalPrice.convertRupiah()
            val noImageURL = "https://cdn-icons-png.flaticon.com/512/1829/1829548.png"
            imagePlaceHistory.loadImage(history.placePhotoURL ?: noImageURL, progressBarPlaceHistory)

            cardHistory.setOnClickListener {
                val action = HistoryFragmentDirections.actionHistoryDetail(history.id, history.totalPrice)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount() = historyList.size

    fun updateHistoryList(newHistoryList: ArrayList<History>) {
        historyList.clear()
        historyList.addAll(newHistoryList)
        notifyDataSetChanged() //Merefresh tampilan recycler view
    }
}