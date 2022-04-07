package com.ubaya.bakul_160419038.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.util.loadImage
import com.ubaya.bakul_160419038.util.usernameLoggedIn
import com.ubaya.bakul_160419038.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.fetch(usernameLoggedIn)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            txtUsername.setText(it.username)
            txtUsername.isEnabled = false
            txtPassword.setText(it.password)
            txtName.setText(it.name)
            txtPhoneNumber.setText(it.phoneNumber)
            txtEmail.setText(it.email)
            val noProfileURL = "https://cdn-icons-png.flaticon.com/512/64/64572.png"
            imageProfileUser.loadImage(it.photoURL ?: noProfileURL, progressBarProfile)
        }
    }
}