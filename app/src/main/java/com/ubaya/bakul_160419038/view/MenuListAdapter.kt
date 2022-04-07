package com.ubaya.bakul_160419038.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.model.Menu
import com.ubaya.bakul_160419038.util.convertRupiah
import com.ubaya.bakul_160419038.util.loadImage
import kotlinx.android.synthetic.main.menu_list_item.view.*

class MenuListAdapter(val menuList: ArrayList<Menu>): RecyclerView
.Adapter<MenuListAdapter.MenuViewHolder>(){
    class MenuViewHolder(val view: View) :RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.menu_list_item, parent, false)

        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = menuList[position]
        with(holder.view) {
            txtMenuName.text = menu.name
            txtMenuPrice.text = menu.price.convertRupiah()
            val noImageURL = "https://cdn-icons-png.flaticon.com/512/1829/1829548.png"
            imageMenu.loadImage(menu.photoURL ?: noImageURL, progressBarMenu)

            if(menu.count <= 0) {
                txtNumber.visibility = View.GONE
                imageButtonMin.visibility = View.GONE
            } else {
                txtNumber.text = menu.count.toString()
            }

            imageButtonAdd.setOnClickListener {
                if(txtNumber.visibility == View.GONE)
                    txtNumber.text = "1"
                else
                    txtNumber.text = (txtNumber.text.toString().toInt() + 1).toString()
                txtNumber.visibility = View.VISIBLE
                imageButtonMin.visibility = View.VISIBLE
            }

            imageButtonMin.setOnClickListener {
                txtNumber.text = (txtNumber.text.toString().toInt() - 1).toString()

                if(txtNumber.text.toString().toInt() <= 0) {
                    imageButtonMin.visibility = View.GONE
                    txtNumber.visibility = View.GONE
                }
            }

            cardMenuList.setOnClickListener {
                val action = MenuListFragmentDirections.actionMenuDetail(menu.id)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount() = menuList.size

    fun updateMenuList(newMenuList: ArrayList<Menu>) {
        menuList.clear()
        menuList.addAll(newMenuList)
        notifyDataSetChanged() //Merefresh tampilan recycler view
    }
}