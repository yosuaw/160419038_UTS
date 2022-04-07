package com.ubaya.bakul_160419038.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.util.convertRupiah
import com.ubaya.bakul_160419038.viewmodel.CartListViewModel
import com.ubaya.bakul_160419038.viewmodel.FAQViewModel
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_faq.*

class FAQFragment : Fragment() {
    private lateinit var viewModel: FAQViewModel
    // Untuk expandableListView FAQ
    var header: MutableList<String> = ArrayList()
    var body: MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(FAQViewModel::class.java)
        viewModel.refresh()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.faqLiveData.observe(viewLifecycleOwner) {
            header.clear()
            body.clear()
            for(item in it) {
                header.add(item.question)
                body.add(item.answer)
            }
            expandableListView.setAdapter(ExpandableListAdapter(context, header, body))
        }
    }
}