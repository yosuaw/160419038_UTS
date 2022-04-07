package com.ubaya.bakul_160419038.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.util.loadImage
import com.ubaya.bakul_160419038.viewmodel.PlaceListViewModel
import com.ubaya.bakul_160419038.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*
import kotlinx.android.synthetic.main.drawer_header.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*


class MainFragment : Fragment() {
    private lateinit var placeViewModel: PlaceListViewModel
    private val placeListAdapter = PlaceListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setGreetingMessage()

        placeViewModel = ViewModelProvider(this).get(PlaceListViewModel::class.java)
        placeViewModel.refresh()

        recViewListPlace.layoutManager = LinearLayoutManager(context)
        recViewListPlace.adapter = placeListAdapter

        observeViewModel()

        refreshLayoutListPlace.setOnRefreshListener {
            setGreetingMessage()
            recViewListPlace.visibility = View.GONE
            txtErrorListPlace.visibility = View.GONE
            progressLoadListPlace.visibility = View.VISIBLE
            placeViewModel.refresh()
            refreshLayoutListPlace.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        placeViewModel.placesLiveData.observe(viewLifecycleOwner) {
            placeListAdapter.updatePlaceList(it)
        }

        placeViewModel.placesLoadErrorLiveData.observe(viewLifecycleOwner) {
            txtErrorListPlace.visibility = if(it) View.VISIBLE else View.GONE
        }

        placeViewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if(it) {
                recViewListPlace.visibility = View.GONE
                progressLoadListPlace.visibility = View.VISIBLE
            } else {
                recViewListPlace.visibility = View.VISIBLE
                progressLoadListPlace.visibility = View.GONE
            }
        }
    }

    private fun getGreetingMessage(): String {
        val c = Calendar.getInstance()
        val timeOfDay = c.get(Calendar.HOUR_OF_DAY)

        return when (timeOfDay) {
            in 0..11 -> "pagi"
            in 12..14 -> "siang"
            in 15..18 -> "sore"
            in 19..23 -> "malam"
            else -> "pagi"
        }
    }

    private fun setGreetingMessage() {
        when(getGreetingMessage()) {
            "pagi" -> {
                txtWelcome.text = "Selamat pagi \uD83C\uDF05"
                txtSubWelcome.text = "Pagi-pagi enaknya sarapan nih! Yuk coba cek beberapa tempat kuliner" +
                        "di sekitar Universitas Surabaya buat mengawali harimu\uD83D\uDE00"
            }
            "siang" -> {
                txtWelcome.text = "Selamat siang \uD83C\uDF1E"
                txtSubWelcome.text = "Siang-siang begini enaknya makan dan minum yang segar-segar nih! " +
                        "Yuk coba cek beberapa tempat kuliner di sekitar Universitas Surabaya buat " +
                        "nyegerin harimu\uD83E\uDD64"
            }
            "sore" -> {
                txtWelcome.text = "Selamat sore \uD83C\uDF07"
                txtSubWelcome.text = "Sore-sore itu enaknya nyemil snack bareng teman-teman lah! " +
                        "Yuk coba cek beberapa tempat kuliner di sekitar Universitas Surabaya, sapa " +
                        "tau ada snack favoritmu\uD83E\uDD68"
            }
            "malam" -> {
                txtWelcome.text = "Selamat malam \uD83C\uDF19"
                txtSubWelcome.text = "Malam-malam begini paling mantap kalau kerja tugas ditemanin makanan favoritmu! " +
                        "Yuk coba cek beberapa tempat kuliner di sekitar Universitas Surabaya, makanan " +
                        "yang dijual malam-malam enak-enak loh\uD83D\uDE0B"
            }
        }
    }
}