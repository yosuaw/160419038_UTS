package com.ubaya.bakul_160419038.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.model.Review
import com.ubaya.bakul_160419038.util.loadImage
import kotlinx.android.synthetic.main.review_list_item.view.*

class ReviewListAdapter(val reviewList: ArrayList<Review>): RecyclerView
.Adapter<ReviewListAdapter.ReviewViewHolder>(){
    class ReviewViewHolder(var view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.review_list_item, parent, false)

        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]
        with(holder.view) {
            txtNameReview.text = review.personName
            txtReview.text = review.content
            val noProfileURL = "https://cdn-icons-png.flaticon.com/512/64/64572.png"
            imageProfile.loadImage(review.photoURL ?: noProfileURL, progressBarProfileReview)
        }
    }

    override fun getItemCount() = reviewList.size

    fun updateReviewList(newReviewList: ArrayList<Review>) {
        reviewList.clear()
        reviewList.addAll(newReviewList)
        notifyDataSetChanged() //Merefresh tampilan recycler view
    }
}