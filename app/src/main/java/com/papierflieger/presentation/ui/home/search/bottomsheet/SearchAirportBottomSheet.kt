package com.papierflieger.presentation.ui.home.search.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.papierflieger.data.local.room.entity.AirportEntity
import com.papierflieger.databinding.BottomSheetAirportSearchBinding
import com.papierflieger.presentation.bussiness.AirportsViewModel
import com.papierflieger.presentation.ui.adapter.airports.AirportsAdapter
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAirportBottomSheet(
    private val listener : OnAirportClickListner
) : BottomSheetDialogFragment() {

    private lateinit var binding : BottomSheetAirportSearchBinding

    private val airportViewModel : AirportsViewModel by viewModels()
    private val airportsAdapter : AirportsAdapter by lazy { AirportsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetAirportSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showsAirports()
    }

    private fun showsAirports() {
        airportViewModel.getSavedAirports()
        airportViewModel.listOfflineAirports.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    showsRecycler(it.payload)
                }
                is Resource.Empty -> {}
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }

    private fun showsRecycler(airports: List<AirportEntity>?) {
        with(binding.rvSearch){
            airportsAdapter.setItem(airports as ArrayList<AirportEntity>)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )

            adapter = airportsAdapter
            airportsAdapter.airportChoosed(
                object : AirportsAdapter.OnAirportItemAction{
                    override fun airportChoosed(airport: AirportEntity) {
                        listener.itemChoosed(airport)
                        dismiss()
                    }
                }
            )
        }
    }

    interface OnAirportClickListner {
        fun itemChoosed(airport: AirportEntity)
    }
}