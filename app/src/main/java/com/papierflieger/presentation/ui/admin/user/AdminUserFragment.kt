package com.papierflieger.presentation.ui.admin.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.papierflieger.R
import com.papierflieger.databinding.FragmentAdminUserBinding
import com.papierflieger.presentation.bussiness.AdminViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.ui.adapter.admin.AdminUserAdapter
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminUserFragment : Fragment() {

    private lateinit var binding: FragmentAdminUserBinding
    private val sessionViewModel : SessionViewModel by viewModels()
    private val adminViewModel : AdminViewModel by viewModels()

    private val adapterUser: AdminUserAdapter by lazy {
        AdminUserAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingTitle()
        initList()
        observeData()
    }

    private fun observeData() {
        sessionViewModel.getToken().observe(viewLifecycleOwner) { token ->
            adminViewModel.getUsers(token).observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Empty -> {}
                    is Resource.Error -> {}
                    is Resource.Loading -> {
                        binding.apply {
                            pbLoading.visibility = View.VISIBLE
                            pbLoading.isVisible = true
                        }
                    }
                    is Resource.Success -> {
                        binding.apply {
                            pbLoading.visibility = View.GONE
                            pbLoading.isVisible = false
                        }
                        adapterUser.setItem(it.payload?.users)
                    }
                }
            }
        }

    }

    private fun initList() {
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterUser
        }

        adapterUser.actionClick(object : AdminUserAdapter.OnAdminUserItem {
            override fun itemUpdateClicked(id: Int) {
                alertUpdate(id)
            }
        })
    }

    private fun alertUpdate(id: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.text_sure_admin)
            .setPositiveButton(R.string.text_update) { _, _ ->
                sessionViewModel.getToken().observe(viewLifecycleOwner) { token ->
                    adminViewModel.updateUser(id, token)
                    findNavController().navigate(R.id.adminDashboardFragment)
                }
            }
            .setNegativeButton(R.string.text_cancel) { _, _ ->

            }
            .show()
    }

    private fun bindingTitle() {
        requireActivity().setTitle(R.string.text_user)
    }

}