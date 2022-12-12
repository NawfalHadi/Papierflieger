package com.papierflieger.presentation.ui.home.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.papierflieger.R
import com.papierflieger.databinding.FragmentDetailHistoryBinding

class DetailHistoryFragment : Fragment() {

    private lateinit var binding : FragmentDetailHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailHistoryBinding.inflate(layoutInflater, container, false)
        return inflater.inflate(R.layout.fragment_detail_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.icKeyboardArrow.setOnClickListener {
            pricingDetailsShow()
        }
    }

    private fun pricingDetailsShow() {
        val arrowState = binding.icKeyboardArrow.rotation
        with(binding){
            if (arrowState == 180f){
                // Hide Details
                icKeyboardArrow.rotation = 0f
                // guideline, rv, payment hide
                guidelineOne.visibility = View.GONE
                rvPricingdetail.visibility = View.GONE
            } else {
                // Show Detail
                icKeyboardArrow.rotation = 180f
                // guideline, rv, payment show
                guidelineOne.visibility = View.VISIBLE
                rvPricingdetail.visibility = View.VISIBLE
            }
        }
    }
}