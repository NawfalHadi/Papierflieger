package com.papierflieger.presentation.ui.home.profiles.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.R
import com.papierflieger.data.local.model.TitleValueModel
import com.papierflieger.data.network.response.user.User
import com.papierflieger.databinding.FragmentAccountInformationBinding
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.ui.adapter.settings.AccountInformationAdapter
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountInformationFragment : Fragment() {

    private lateinit var binding : FragmentAccountInformationBinding
    private val sessionViewModel : SessionViewModel by viewModels()

    private var personalInformation : ArrayList<TitleValueModel> = arrayListOf()
    private val personalAdapter : AccountInformationAdapter by lazy { AccountInformationAdapter() }

    private var addressInformation : ArrayList<TitleValueModel> = arrayListOf()
    private val addressAdapter : AccountInformationAdapter by lazy { AccountInformationAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountInformationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionViewModel.getToken().observe(viewLifecycleOwner){ token ->
            sessionViewModel.getProfileUser(token).observe(viewLifecycleOwner){
                when(it) {
                    is Resource.Success -> {
                        val users = it.payload!!
                        setAccountInformation(users)
                        showsRecycler()
                    }
                    is Resource.Empty -> Log.e("User Information", "Empty")
                    is Resource.Error -> Log.e("User Information", it.message.toString())
                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun setAccountInformation(user: User) {
        binding.textPersonalChange.setOnClickListener {
            val mBundle = Bundle()
            mBundle.putParcelable(AccountInformationActivity.PERSONAL_INFORMATION, user)
            findNavController().navigate(R.id.action_accountInformationFragment_to_personalInformationFragment, mBundle)
        }

        binding.textAddressChange.setOnClickListener {
            val mBundle = Bundle()
            mBundle.putParcelable(AccountInformationActivity.ADDRESS_INFORMATION, user)
            findNavController().navigate(R.id.action_accountInformationFragment_to_addressInformationFragment, mBundle)
        }

        personalInformation.clear()
        personalInformation.addAll(arrayListOf(
            TitleValueModel("Title", user.title),
            TitleValueModel("Full Name", user.fullName),
            TitleValueModel("Username", user.username),
            TitleValueModel("Date Of Birth", user.birthdate),
            TitleValueModel("Nationality", user.nationality)
        ))

        addressInformation.clear()
        addressInformation.add(TitleValueModel("Country", user.country))
        addressInformation.add(TitleValueModel("Province", user.province))
        addressInformation.add(TitleValueModel("Regency", user.regency))
    }

    private fun showsRecycler() {
        with(binding){
            with(rvPersonalInformation){
                personalAdapter.setItem(personalInformation)
                setRecyclerData(this, personalAdapter)
            }

            with(rvAddress){
                addressAdapter.setItem(addressInformation)
                setRecyclerData(this, addressAdapter)
            }
        }
    }

    private fun setRecyclerData(recyclerView: RecyclerView, _adapter: AccountInformationAdapter) {
        with(recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
            adapter = _adapter
        }
    }

}