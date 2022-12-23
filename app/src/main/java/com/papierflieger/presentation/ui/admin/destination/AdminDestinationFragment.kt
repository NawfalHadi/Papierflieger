package com.papierflieger.presentation.ui.admin.destination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.papierflieger.R
import com.papierflieger.databinding.FragmentAdminDestinationBinding
import com.papierflieger.presentation.bussiness.DestinationViewModel
import com.papierflieger.presentation.ui.adapter.destinations.AdminDestinationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminDestinationFragment : Fragment() {

    private lateinit var binding : FragmentAdminDestinationBinding
    private val destinationViewModel : DestinationViewModel by viewModels()

    private val adapterDestination : AdminDestinationAdapter by lazy {
        AdminDestinationAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        binding = FragmentAdminDestinationBinding.inflate(layoutInflater, container, false)
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
            findNavController().navigate(R.id.action_adminDestinationFragment_to_addDestinationFragment)
        }
    }

    private fun observeData() {
        destinationViewModel.getDestination().observe(viewLifecycleOwner) {
            adapterDestination.setItem(it.destinations!!)
        }
    }

    private fun initList() {
        binding.rvDestination.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterDestination
        }

        adapterDestination.actionClick(object : AdminDestinationAdapter.OnAdminDestinationItem{
            override fun itemClicked(id: Int) {
                findNavController().navigate(
                    R.id.action_adminDestinationFragment_to_addDestinationFragment,
                    Bundle().apply { putInt("id", id) }
                )
            }
        })
    }

    private fun bindingTitle() {
        requireActivity().setTitle(R.string.text_destination)
    }

}