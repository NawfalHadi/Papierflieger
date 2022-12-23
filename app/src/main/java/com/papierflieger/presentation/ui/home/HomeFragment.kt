package com.papierflieger.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.papierflieger.R
import com.papierflieger.databinding.FragmentHomeBinding
import com.papierflieger.presentation.bussiness.AuthViewModel
import com.papierflieger.presentation.bussiness.DestinationViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.ui.adapter.destinations.DestinationAdapter
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val sessionViewModel : SessionViewModel by viewModels()
    private val destinationViewModel : DestinationViewModel by viewModels()

    private val favDestinationAdapter : DestinationAdapter by lazy { DestinationAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingSession()
        showsData()
        binding.btnSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchActivity)
        }
    }

    private fun showsData() {
        destinationViewModel.getDestination().observe(viewLifecycleOwner){
            favDestinationAdapter.setItem(it.destinations!!)
            initRecyclerView()
        }
    }

    private fun initRecyclerView() {
        with(binding.rvFavdestination){
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )
            adapter = favDestinationAdapter
        }
    }

    private fun bindingSession() {
        authViewModel.getNames().observe(viewLifecycleOwner){
            binding.tvFullname.text = it
        }

        authViewModel.getAvatar().observe(viewLifecycleOwner){
            binding.ivAvatar.load(it){
                placeholder(R.drawable.ic_person_circle)
            }
        }
    }

}