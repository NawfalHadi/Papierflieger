package com.papierflieger.presentation.ui.admin.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.papierflieger.R
import com.papierflieger.databinding.FragmentAdminAirportBinding
import com.papierflieger.databinding.FragmentAdminOrderBinding
import com.papierflieger.presentation.bussiness.AdminViewModel
import com.papierflieger.presentation.bussiness.AirportsViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.ui.adapter.admin.AdminAirportAdapter
import com.papierflieger.presentation.ui.adapter.admin.AdminOrderAdapter
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminOrderFragment : Fragment() {

    private lateinit var binding: FragmentAdminOrderBinding
    private val sessionViewModel : SessionViewModel by viewModels()
    private val adminViewModel : AdminViewModel by viewModels()

    private val adapterOrder : AdminOrderAdapter by lazy {
        AdminOrderAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminOrderBinding.inflate(layoutInflater, container, false)
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
            adminViewModel.getOrders(token).observe(viewLifecycleOwner) {
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
                        adapterOrder.setItem(it.payload?.orderList)
                    }
                }
            }
        }

    }

    private fun initList() {
        binding.rvOrder.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterOrder
        }

        adapterOrder.actionClick(object : AdminOrderAdapter.OnAdminOrderItem{
            override fun itemDeleteClicked(id: Int) {
                alertDelete(id)
            }
        })
    }

    private fun alertDelete(id: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.text_sure_delete)
            .setPositiveButton(R.string.text_delete) { _, _ ->
                sessionViewModel.getToken().observe(viewLifecycleOwner) { token ->
                    adminViewModel.deleteOrder(id, token)
                    findNavController().navigate(R.id.adminDashboardFragment)
                }
            }
            .setNegativeButton(R.string.text_cancel) { _, _ ->

            }
            .show()
    }

    private fun bindingTitle() {
        requireActivity().setTitle(R.string.text_order)
    }

}