package com.papierflieger.presentation.ui.admin.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.papierflieger.R
import com.papierflieger.databinding.FragmentAdminTicketBinding

class AdminTicketFragment : Fragment() {

    private lateinit var binding : FragmentAdminTicketBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminTicketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingTitle()
    }

    private fun bindingTitle() {
        requireActivity().setTitle(R.string.text_ticket)
    }

}