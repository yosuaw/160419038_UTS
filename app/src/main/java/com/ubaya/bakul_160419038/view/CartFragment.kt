package com.ubaya.bakul_160419038.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.util.convertRupiah
import com.ubaya.bakul_160419038.util.usernameLoggedIn
import com.ubaya.bakul_160419038.viewmodel.CartListViewModel
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : Fragment() {
    private lateinit var viewModel: CartListViewModel
    private val cartListAdapter = CartListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(CartListViewModel::class.java)
        viewModel.refresh(usernameLoggedIn)

        recViewCart.layoutManager = LinearLayoutManager(context)
        recViewCart.adapter = cartListAdapter

        observeViewModel()

        refreshLayoutCart.setOnRefreshListener {
            txtTotalPriceCart.visibility = View.GONE
            horizontalLine1.visibility = View.GONE
            horizontalLine2.visibility = View.GONE
            txtForTotalPrice.visibility = View.GONE
            txtNote.visibility = View.GONE
            btnOrder.visibility = View.GONE
            recViewCart.visibility = View.GONE
            txtErrorCart.visibility = View.GONE
            progressLoadCart.visibility = View.VISIBLE
            viewModel.refresh(usernameLoggedIn)
            refreshLayoutCart.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.cartMenusLiveData.observe(viewLifecycleOwner) {
            if(it.size == 0) {
                txtClearCart.visibility = View.VISIBLE
                horizontalLine1.visibility = View.GONE
                horizontalLine2.visibility = View.GONE
                txtForTotalPrice.visibility = View.GONE
                txtTotalPriceCart.visibility = View.GONE
                txtNote.visibility = View.GONE
                btnOrder.visibility = View.GONE
            } else {
                txtClearCart.visibility = View.GONE
                horizontalLine1.visibility = View.VISIBLE
                horizontalLine2.visibility = View.VISIBLE
                txtForTotalPrice.visibility = View.VISIBLE
                txtTotalPriceCart.visibility = View.VISIBLE
                txtNote.visibility = View.VISIBLE
                btnOrder.visibility = View.VISIBLE
                cartListAdapter.updateCartMenuList(it)
                txtTotalPriceCart.text = it[0].totalPrice.convertRupiah()
            }
        }

        viewModel.cartMenusLoadErrorLiveData.observe(viewLifecycleOwner) {
            txtErrorCart.visibility = if(it) View.VISIBLE else View.GONE
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if(it) {
                recViewCart.visibility = View.GONE
                progressLoadCart.visibility = View.VISIBLE
            } else {
                recViewCart.visibility = View.VISIBLE
                progressLoadCart.visibility = View.GONE
            }
        }
    }
}