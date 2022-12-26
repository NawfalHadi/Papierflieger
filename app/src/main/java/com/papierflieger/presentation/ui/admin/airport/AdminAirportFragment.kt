package com.papierflieger.presentation.ui.admin.airport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.papierflieger.R
import com.papierflieger.databinding.FragmentAdminAirportBinding
import com.papierflieger.presentation.bussiness.AdminViewModel
import com.papierflieger.presentation.bussiness.AirportsViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.ui.adapter.destinations.AdminAirportAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminAirportFragment : Fragment() {

    private lateinit var binding : FragmentAdminAirportBinding
    private val airportViewModel : AirportsViewModel by viewModels()
    private val sessionViewModel : SessionViewModel by viewModels()
    private val adminViewModel : AdminViewModel by viewModels()

    private val adapterAirport : AdminAirportAdapter by lazy {
        AdminAirportAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminAirportBinding.inflate(layoutInflater, container, false)
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
            findNavController().navigate(R.id.action_adminAirportFragment_to_addAirportFragment)
        }
    }

    private fun observeData() {
        airportViewModel.getAirports().observe(viewLifecycleOwner) {
            adapterAirport.setItem(it.payload?.airports)
        }
    }

    private fun initList() {
        binding.rvAirport.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterAirport
        }

        adapterAirport.actionClick(object : AdminAirportAdapter.OnAdminAirportItem{
            override fun itemEditClicked(id: Int) {
                findNavController().navigate(
                    R.id.action_adminAirportFragment_to_addAirportFragment,
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
                    adminViewModel.deleteAirport(id, token)
                    findNavController().navigate(R.id.adminDashboardFragment)
                }
            }
            .setNegativeButton(R.string.text_cancel) { _, _ ->

            }
            .show()
    }

    private fun bindingTitle() {
        requireActivity().setTitle(R.string.text_airport)
    }

}