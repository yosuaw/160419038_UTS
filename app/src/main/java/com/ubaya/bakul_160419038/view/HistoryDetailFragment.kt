package com.ubaya.bakul_160419038.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.util.convertRupiah
import com.ubaya.bakul_160419038.util.loadImage
import com.ubaya.bakul_160419038.viewmodel.HistoryDetailViewModel
import kotlinx.android.synthetic.main.fragment_history_detail.*
import kotlinx.android.synthetic.main.fragment_menu_list.*

class HistoryDetailFragment : Fragment() {
    private lateinit var viewModel: HistoryDetailViewModel
    private val historyDetailAdapter = HistoryDetailAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(arguments != null) {
            val historyID = HistoryDetailFragmentArgs.fromBundle(requireArguments()).historyID
            val totalPrice = HistoryDetailFragmentArgs.fromBundle(requireArguments()).totalPrice

            viewModel = ViewModelProvider(this).get(HistoryDetailViewModel::class.java)
            viewModel.fetch(historyID)

            recViewHistoryDetail.layoutManager = LinearLayoutManager(context)
            recViewHistoryDetail.adapter = historyDetailAdapter

            observeViewModel()

            txtTotalPriceHistoryDetail.text = totalPrice.convertRupiah()
        } else {
            Toast.makeText(context, "Riwayat pesanan tidak ditemukan!", Toast.LENGTH_LONG).show()
        }
    }

    private fun observeViewModel() {
        val parser = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm")
        val formatter = java.text.SimpleDateFormat("d MMMM yyyy  |  HH:mm")

        viewModel.historyMenuLiveData.observe(viewLifecycleOwner) {
            historyDetailAdapter.updateHistoryMenuList(it)

            val noImageURL = "https://cdn-icons-png.flaticon.com/512/1829/1829548.png"
            imagePlaceHistoryDetail.loadImage(it[0].placePhotoURL ?: noImageURL, progressBarPlaceHistoryDetail)
            txtPlaceNameHistoryDetail.text = it[0].placeName
            txtDateTimeHistoryDetail.text = formatter.format(parser.parse(it[0].timeAt))
        }
    }
}