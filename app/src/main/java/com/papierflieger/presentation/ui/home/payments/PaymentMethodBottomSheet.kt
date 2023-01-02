package com.papierflieger.presentation.ui.home.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.papierflieger.databinding.BottomSheetPaymentInformationBinding

class PaymentMethodBottomSheet(
    private val listener : OnSavedInformation
) : BottomSheetDialogFragment() {

    private lateinit var binding : BottomSheetPaymentInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetPaymentInformationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    
        with(binding){
            cvPay.setOnClickListener {
                if (validForm()){
                    listener.saveData(etAccountName.text.toString(), etAccountNumber.text.toString())
                    dismiss()
                }
            }
        }
        
    }

    private fun validForm(): Boolean {
        with(binding){
            return !(etAccountName.text.isNullOrEmpty() || etAccountNumber.text.isNullOrEmpty())
        }
    }

    interface OnSavedInformation {
        fun saveData(accName: String, accNumber: String)
    }
}