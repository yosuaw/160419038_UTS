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
import com.ubaya.bakul_160419038.viewmodel.ReviewListViewModel
import kotlinx.android.synthetic.main.fragment_review.*

class ReviewFragment : Fragment() {
    private lateinit var viewModel: ReviewListViewModel
    private val reviewListAdapter = ReviewListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(arguments != null) {
            val placeID = ReviewFragmentArgs.fromBundle(requireArguments()).placeID
            val placeName = ReviewFragmentArgs.fromBundle(requireArguments()).placeName

            txtTitleReview.text = "Ulasan di $placeName"

            viewModel = ViewModelProvider(this).get(ReviewListViewModel::class.java)
            viewModel.refresh(placeID)

            recViewReview.layoutManager = LinearLayoutManager(context)
            recViewReview.adapter = reviewListAdapter

            observeViewModel()

            refreshLayoutReview.setOnRefreshListener {
                recViewReview.visibility = View.GONE
                txtErrorReview.visibility = View.GONE
                progressLoadReview.visibility = View.VISIBLE
                viewModel.refresh(placeID)
                refreshLayoutReview.isRefreshing = false
            }
         } else {
            Toast.makeText(context, "Ulasan tidak ditemukan!", Toast.LENGTH_LONG).show()
        }
    }

    private fun observeViewModel() {
        viewModel.reviewsLiveData.observe(viewLifecycleOwner) {
            if(it.size == 0) {
                txtTitleReview.visibility = View.GONE
                txtReviewClear.visibility = View.VISIBLE
            } else {
                txtTitleReview.visibility = View.VISIBLE
                txtReviewClear.visibility = View.GONE
                reviewListAdapter.updateReviewList(it)
            }
        }

        viewModel.reviewsLoadErrorLiveData.observe(viewLifecycleOwner) {
            txtErrorReview.visibility = if(it) View.VISIBLE else View.GONE
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if(it) {
                recViewReview.visibility = View.GONE
                progressLoadReview.visibility = View.VISIBLE
            } else {
                recViewReview.visibility = View.VISIBLE
                progressLoadReview.visibility = View.GONE
            }
        }
    }
}