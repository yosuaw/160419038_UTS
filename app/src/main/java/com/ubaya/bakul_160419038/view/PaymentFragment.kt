package com.ubaya.bakul_160419038.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ubaya.bakul_160419038.R
import com.ubaya.bakul_160419038.util.convertRupiah
import com.ubaya.bakul_160419038.util.usernameLoggedIn
import com.ubaya.bakul_160419038.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_payment.*

class PaymentFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.fetch(usernameLoggedIn)

        observeViewModel()

        refreshLayoutPayment.setOnRefreshListener {
            txtBalance.text = "Rp -"
            txtErrorPayment.visibility = View.GONE
            progressLoadPayment.visibility = View.VISIBLE
            viewModel.fetch(usernameLoggedIn)
            refreshLayoutPayment.isRefreshing = false
        }

        txtTopUp.text =
            "1. Pengisian saldo dapat dilakukan di 2 lokasi kampus Universitas Surabaya:\n" +
            "\t- Politeknik UBAYA: Jl. Ngagel Jaya Selatan No.169, Baratajaya, Kec. Gubeng, Kota SBY, Jawa Timur 60284\n" +
            "\t- UBAYA Tenggilis: Jl. Raya Kalirungkut, Kali Rungkut, Kec. Rungkut, Kota SBY, Jawa Timur 60293\n" +
            "2. Pergi ke bagian administrasi dan ajukan pengisian saldo BaKul ke administrator\n" +
            "3. Sebutkan sername dan nomor telepon akun BaKul yang ingin diisi saldonya kepada administrator\n" +
            "4. Ikuti penjelasan administrator sampai proses pengisian saldo selesai\n" +
            "5. Setelah itu, tunggu beberapa saat sampai saldo BaKul terisi\n" +
            "6. Saldo BaKul berhasil diisi"
    }

    private fun observeViewModel() {
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            txtBalance.text = it.balance.convertRupiah()
        }

        viewModel.userLoadErrorLiveData.observe(viewLifecycleOwner) {
            txtErrorPayment.visibility = if(it) View.VISIBLE else View.GONE
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if(it) {
                progressLoadPayment.visibility = View.VISIBLE
            } else {
                progressLoadPayment.visibility = View.GONE
            }
        }
    }
}