package com.papierflieger.presentation.ui.home.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.papierflieger.databinding.FragmentDetailHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding : FragmentDetailHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        

        bindingInformation()
        binding.icKeyboardArrow.setOnClickListener {
            pricingDetailsShow()
        }

    }

    private fun bindingInformation() {
        with(binding){

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