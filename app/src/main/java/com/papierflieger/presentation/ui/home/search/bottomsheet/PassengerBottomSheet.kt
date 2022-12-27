package com.papierflieger.presentation.ui.home.search.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.papierflieger.databinding.BottomSheetPassengerChooseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerBottomSheet(
    private var counter : Int,
    private val listener : OnPassengerCounted
): BottomSheetDialogFragment() {

    private lateinit var binding : BottomSheetPassengerChooseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetPassengerChooseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            etPassenger.setText(counter.toString())

            btnCancel.setOnClickListener { dismiss() }
            btnSave.setOnClickListener {
                listener.passengerCounted(counter)
                dismiss()
            }

            btnMinus.setOnClickListener {
                if (counter != 1){
                    etPassenger.setText((counter - 1).toString())
                    counter -= 1
                }
            }

            btnAdd.setOnClickListener {
                etPassenger.setText((counter + 1).toString())
                counter += 1
            }
        }

    }

    interface OnPassengerCounted {
        fun passengerCounted(count : Int)
    }
}