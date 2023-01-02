package com.papierflieger.presentation.ui.admin.transaction

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
import com.papierflieger.databinding.FragmentAdminTransactionBinding
import com.papierflieger.presentation.bussiness.AdminViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.ui.adapter.admin.AdminTransactionAdapter
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminTransactionFragment : Fragment() {

    private lateinit var binding: FragmentAdminTransactionBinding
    private val sessionViewModel : SessionViewModel by viewModels()
    private val adminViewModel : AdminViewModel by viewModels()

    private val adapterTransaction: AdminTransactionAdapter by lazy {
        AdminTransactionAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminTransactionBinding.inflate(layoutInflater, container, false)
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
            adminViewModel.getTransactions(token).observe(viewLifecycleOwner) {
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
                        adapterTransaction.setItem(it.payload?.transaksi)
                    }
                }
            }
        }

    }

    private fun initList() {
        binding.rvTransaction.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterTransaction
        }

        adapterTransaction.actionClick(object : AdminTransactionAdapter.OnAdminTransactionItem {
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
                    adminViewModel.deleteTransaction(id, token)
                    findNavController().navigate(R.id.adminDashboardFragment)
                }
            }
            .setNegativeButton(R.string.text_cancel) { _, _ ->

            }
            .show()
    }

    private fun bindingTitle() {
        requireActivity().setTitle(R.string.text_transaction)
    }

}