package com.ubaya.bakul_160419038.view

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.model.CartMenu
import com.ubaya.bakul_160419038.util.convertRupiah
import com.ubaya.bakul_160419038.util.loadImage
import kotlinx.android.synthetic.main.cart_list_item.view.*
import kotlinx.android.synthetic.main.history_menu_list_item.view.*

class CartListAdapter(val cartMenuList: ArrayList<CartMenu>): RecyclerView
.Adapter<CartListAdapter.CartViewHolder>(){
    class  CartViewHolder(var view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cart_list_item, parent, false)

        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = cartMenuList[position]
        with(holder.view) {
            txtMenuNameCart.text = if(cart.count > 1) "${cart.menuName} (x${cart.count})" else "${cart.menuName}"
            txtNotesCart.text = "Catatan: " + if(cart.notes == "") "-" else cart.notes
            txtMenuPriceCart.text = (cart.price * cart.count).convertRupiah()
            val noImageURL = "https://cdn-icons-png.flaticon.com/512/1829/1829548.png"
            imageMenuCart.loadImage(cart.menuPhotoURL ?: noImageURL, progressBarMenuCart)

            imageButtonDelete.setOnClickListener {
                AlertDialog.Builder(context).apply {
                    setTitle("Hapus Menu di Keranjang")
                    setMessage("Apakah Anda yakin ingin menghapus ${cart.menuName} (x${cart.count}) " +
                            "dari keranjang?")
                    setPositiveButton("Hapus") { dialogInterface, i -> }
                    setNegativeButton("Batal") { dialogInterface, i ->
                        dialogInterface.cancel()
                    }
                    setCancelable(false)
                    create().show()
                }
            }
        }
    }

    override fun getItemCount() = cartMenuList.size

    fun updateCartMenuList(newCartMenuList: ArrayList<CartMenu>) {
        cartMenuList.clear()
        cartMenuList.addAll(newCartMenuList)
        notifyDataSetChanged() //Merefresh tampilan recycler view
    }
}