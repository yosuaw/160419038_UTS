package com.ubaya.bakul_160419038.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.util.usernameLoggedIn
import com.ubaya.bakul_160419038.viewmodel.HistoryListViewModel
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {
    private lateinit var viewModel:HistoryListViewModel
    private val historyListAdapter = HistoryListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HistoryListViewModel::class.java)
        viewModel.refresh(usernameLoggedIn)

        recViewHistory.layoutManager = LinearLayoutManager(context)
        recViewHistory.adapter = historyListAdapter

        observeViewModel()

        refreshLayoutCart.setOnRefreshListener {
            recViewHistory.visibility = View.GONE
            txtErrorHistory.visibility = View.GONE
            progressLoadHistory.visibility = View.VISIBLE
            viewModel.refresh(usernameLoggedIn)
            refreshLayoutCart.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.historiesLiveData.observe(viewLifecycleOwner) {
            if(it.size != 0) {
                txtHistoryClear.visibility = View.GONE
                historyListAdapter.updateHistoryList(it)
            } else {
                txtHistoryClear.visibility = View.VISIBLE
            }
        }

        viewModel.historiesLoadErrorLiveData.observe(viewLifecycleOwner) {
            txtErrorHistory.visibility = if(it) View.VISIBLE else View.GONE
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if(it) {
                recViewHistory.visibility = View.GONE
                progressLoadHistory.visibility = View.VISIBLE
            } else {
                recViewHistory.visibility = View.VISIBLE
                progressLoadHistory.visibility = View.GONE
            }
        }
    }
}