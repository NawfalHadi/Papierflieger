package com.papierflieger.presentation.ui.home.profiles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.papierflieger.R
import com.papierflieger.data.local.model.SettingModel
import com.papierflieger.databinding.FragmentProfileUserBinding
import com.papierflieger.presentation.bussiness.AuthViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.ui.adapter.settings.SettingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileUserFragment : Fragment() {

    private lateinit var binding : FragmentProfileUserBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val sessionViewModel: SessionViewModel by viewModels()

    private val accountAndSecurity : ArrayList<SettingModel> = arrayListOf(
        SettingModel(R.drawable.ic_person, "Account Information", "", R.id.action_profileUserFragment_to_accountInformationActivity),
        SettingModel(R.drawable.ic_verified,"Password and Security", "", R.id.action_profileUserFragment_to_passwordSecurityActivity)
    )

//    private val preference : ArrayList<SettingModel> = arrayListOf(
//        SettingModel("Country", "Indonesia", R.id.action_profileUserFragment2_to_accountInformationFragment),
//        SettingModel("Currency", "Rupiah", R.id.action_profileUserFragment2_to_accountInformationFragment),
//        SettingModel("Language", "Indonesia", R.id.action_profileUserFragment2_to_accountInformationFragment)
//    )

    private val application : ArrayList<SettingModel> = arrayListOf(
        SettingModel(R.drawable.ic_logout, "Sign Out", "", 0)
    )

    private val accountAdapter : SettingAdapter by lazy { SettingAdapter() }
    private val applicationAdapter : SettingAdapter by lazy { SettingAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingProfile()
        initList()
    }

    private fun bindingProfile() {
        authViewModel.getAvatar().observe(viewLifecycleOwner) {
            binding.ivProfile.load(it) {
                placeholder(R.color.background_gray)
                transformations(CircleCropTransformation())
            }
        }
        authViewModel.getNames().observe(viewLifecycleOwner) {
            binding.tvFullName.text = it
        }
        authViewModel.getEmail().observe(viewLifecycleOwner) {
            binding.tvEmail.text = it
        }
    }

    private fun initList() {
        with(binding) {
            with(rvAccountSecurity) {
                accountAdapter.setItem(accountAndSecurity)
                setRecyclerData(this, accountAdapter)
            }

            with(rvApplication){
                applicationAdapter.setItem(application)
                setRecyclerData(this, applicationAdapter)
            }
        }
    }

    private fun setRecyclerData(recyclerView: RecyclerView, _adapter : SettingAdapter) {
        with(recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
            adapter = _adapter
        }

        _adapter.actionMenu(object : SettingAdapter.OnMenuActionCallback{
            override fun menuClicked(id: Int?) {
                if (id == 0) {
                    sessionViewModel.logout()
                    findNavController().navigate(R.id.action_profileUserFragment_to_authActivity)
                    activity?.finish()
                } else {
                    findNavController().navigate(id!!)
                }
            }
        })
    }

}