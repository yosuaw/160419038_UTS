package com.ubaya.bakul_160419038.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.model.HistoryMenu
import com.ubaya.bakul_160419038.util.convertRupiah
import com.ubaya.bakul_160419038.util.loadImage
import kotlinx.android.synthetic.main.history_menu_list_item.view.*

class HistoryDetailAdapter(var historyMenuList: ArrayList<HistoryMenu>): RecyclerView
.Adapter<HistoryDetailAdapter.HistoryDetailViewHolder>(){
    class HistoryDetailViewHolder(var view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.history_menu_list_item, parent, false)

        return HistoryDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryDetailViewHolder, position: Int) {
        val menu = historyMenuList[position]
        with(holder.view) {
            txtMenuHistory.text = if(menu.menuCount > 1) "${menu.menuName} (x${menu.menuCount})" else "${menu.menuName}"
            txtNotesHistory.text = "Catatan: " + if(menu.notes == "") "-" else menu.notes
            txtMenuPriceHistory.text = (menu.price * menu.menuCount).convertRupiah()
            val noImageURL = "https://cdn-icons-png.flaticon.com/512/1829/1829548.png"
            imageHistoryMenu.loadImage(menu.menuPhotoURL ?: noImageURL, progressBarHistoryMenu)
        }
    }

    override fun getItemCount() = historyMenuList.size

    fun updateHistoryMenuList(newHistoryMenuList: ArrayList<HistoryMenu>) {
        historyMenuList.clear()
        historyMenuList.addAll(newHistoryMenuList)
        notifyDataSetChanged() //Merefresh tampilan recycler view
    }

}