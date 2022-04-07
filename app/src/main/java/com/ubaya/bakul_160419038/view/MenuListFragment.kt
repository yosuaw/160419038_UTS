package com.ubaya.bakul_160419038.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.util.loadImage
import com.ubaya.bakul_160419038.viewmodel.MenuListViewModel
import kotlinx.android.synthetic.main.fragment_menu_list.*
import kotlinx.android.synthetic.main.menu_list_item.view.*
import kotlinx.android.synthetic.main.place_list_item.view.*

class MenuListFragment : Fragment() {
    private lateinit var placeViewModel: MenuListViewModel
    private lateinit var menuViewModel: MenuListViewModel
    private val menuListAdapter = MenuListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(arguments != null) {
            val placeID = MenuListFragmentArgs.fromBundle(requireArguments()).placeID

            // For place
            placeViewModel = ViewModelProvider(this).get(MenuListViewModel::class.java)
            placeViewModel.fetch(placeID)

            btnReset.setOnClickListener {
                imageButtonStar1.setImageResource(R.drawable.ic_baseline_star_border_24)
                imageButtonStar2.setImageResource(R.drawable.ic_baseline_star_border_24)
                imageButtonStar3.setImageResource(R.drawable.ic_baseline_star_border_24)
                imageButtonStar4.setImageResource(R.drawable.ic_baseline_star_border_24)
                imageButtonStar5.setImageResource(R.drawable.ic_baseline_star_border_24)
            }


            // For menus
            menuViewModel = ViewModelProvider(this).get(MenuListViewModel::class.java)
            menuViewModel.refresh(placeID)

            recViewMenuList.layoutManager = LinearLayoutManager(context)
            recViewMenuList.adapter = menuListAdapter

            observeViewModel()

            refreshLayoutListMenu.setOnRefreshListener {
                recViewMenuList.visibility = View.GONE
                txtErrorMenuList.visibility = View.GONE
                progressLoadMenuList.visibility = View.VISIBLE
                menuViewModel.refresh(placeID)
                refreshLayoutListMenu.isRefreshing = false
            }
        } else {
            Toast.makeText(context, "Tempat kuliner tidak ditemukan!", Toast.LENGTH_LONG).show()
        }
    }

    private fun observeViewModel() {
        // For place
        placeViewModel.placeLiveData.observe(viewLifecycleOwner) {
            txtNamePlaceDetail.text = it.name
            txtAddressPlaceDetail.text = it.address
            txtRatingPlaceDetail.text = it.rating.toString()
            txtOpenClosedPlaceDetail.text = "${it.open} - ${it.closed}"
            txtReviewsPlaceDetail.text = "${it.reviews} Ulasan"
            val noImageURL = "https://cdn-icons-png.flaticon.com/512/1829/1829548.png"
            imagePlaceDetail.loadImage(it.photoURL ?: noImageURL, progressBarPlaceDetail)

            val placeID = it.id
            val placeName = it.name
            btnReviews.setOnClickListener {
                val action = MenuListFragmentDirections.actionReview(placeID, placeName)
                Navigation.findNavController(it).navigate(action)
            }
        }

        // For menus
        menuViewModel.menusLiveData.observe(viewLifecycleOwner) {
            menuListAdapter.updateMenuList(it)
        }

        menuViewModel.menusLoadErrorLiveData.observe(viewLifecycleOwner) {
            txtErrorMenuList.visibility = if(it) View.VISIBLE else View.GONE
        }

        menuViewModel.menusLoadingLiveData.observe(viewLifecycleOwner) {
            if(it) {
                recViewMenuList.visibility = View.GONE
                progressLoadMenuList.visibility = View.VISIBLE
            } else {
                recViewMenuList.visibility = View.VISIBLE
                progressLoadMenuList.visibility = View.GONE
            }
        }
    }
}