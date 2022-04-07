package com.ubaya.bakul_160419038.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.util.convertRupiah
import com.ubaya.bakul_160419038.util.loadImage
import com.ubaya.bakul_160419038.viewmodel.MenuDetailViewModel
import kotlinx.android.synthetic.main.fragment_menu_detail.*

class MenuDetailFragment : Fragment() {
    private lateinit var viewModel: MenuDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(arguments != null) {
            val menuID = MenuDetailFragmentArgs.fromBundle(requireArguments()).menuID
            viewModel = ViewModelProvider(this).get(MenuDetailViewModel::class.java)
            viewModel.fetch(menuID)

            observeViewModel()

            imageButtonAddDetail.setOnClickListener {
                txtNumberDetail.text = (txtNumberDetail.text.toString().toInt() + 1).toString()
            }

            imageButtonMinDetail.setOnClickListener {
                if(txtNumberDetail.text == "0"){
                    txtNumberDetail.text = "0"
                } else {
                    txtNumberDetail.text = (txtNumberDetail.text.toString().toInt() - 1).toString()
                }
            }
        } else {
            Toast.makeText(context, "Menu tidak ditemukan!", Toast.LENGTH_LONG).show()
        }
    }

    private fun observeViewModel() {
        viewModel.menuLiveData.observe(viewLifecycleOwner) {
            txtMenuDetailName.text = it.name
            txtMenuDetailPrice.text = it.price.convertRupiah()
            txtMenuDetailDesc.text = it.description ?: ""
            txtNumberDetail.text = it.count.toString()
            val noImageURL = "https://cdn-icons-png.flaticon.com/512/1829/1829548.png"
            imageMenuDetail.loadImage(it.photoURL ?: noImageURL, progressBarMenuImage)

            val placeID = it.place_id 
            btnConfirm.setOnClickListener {
                Toast.makeText(context, "Isi keranjang berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                val action = MenuDetailFragmentDirections.actionMenuListFromMenuDetail(placeID)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }
}