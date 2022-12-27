package com.papierflieger.presentation.ui.admin.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.papierflieger.R
import com.papierflieger.databinding.FragmentAdminTicketBinding
import com.papierflieger.presentation.bussiness.AdminViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.bussiness.TicketViewModel
import com.papierflieger.presentation.ui.adapter.destinations.AdminTicketAdapter
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminTicketFragment : Fragment() {

    private lateinit var binding : FragmentAdminTicketBinding
    private val ticketViewModel : TicketViewModel by viewModels()
    private val sessionViewModel : SessionViewModel by viewModels()
    private val adminViewModel : AdminViewModel by viewModels()

    private val adapterTicket : AdminTicketAdapter by lazy {
        AdminTicketAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        binding = FragmentAdminTicketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingTitle()
        initList()
        observeData()
        clickListener()
    }

    private fun clickListener() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_adminTicketFragment_to_addTicketFlightFragment)
        }
    }

    private fun observeData() {
        ticketViewModel.getTickets().observe(viewLifecycleOwner) {
            when (it) {
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
                    adapterTicket.setItem(it.payload?.dataTicket)
                }
            }
        }
    }

    private fun initList() {
        binding.rvTicket.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterTicket
        }

        adapterTicket.actionClick(object : AdminTicketAdapter.OnAdminTicketItem{
            override fun itemEditClicked(id: Int) {
                findNavController().navigate(
                    R.id.action_adminTicketFragment_to_addTicketFlightFragment,
                    Bundle().apply { putInt("id", id) }
                )
            }

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
                    adminViewModel.deleteTicket(id, token).observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Empty -> {}
                            is Resource.Error -> {}
                            is Resource.Loading -> {
                                binding.apply {
                                    pbLoading.visibility = View.VISIBLE
                                    pbLoading.isVisible = true
                                }
                            }
                            is Resource.Success -> {
                                findNavController().navigate(R.id.adminTicketFragment)
                            }
                        }
                    }
                }
            }
            .setNegativeButton(R.string.text_cancel) { _, _ ->

            }
            .show()
    }

    private fun bindingTitle() {
        requireActivity().setTitle(R.string.text_ticket)
    }

}