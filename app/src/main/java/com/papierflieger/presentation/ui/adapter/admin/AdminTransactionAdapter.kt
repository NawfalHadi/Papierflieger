package com.papierflieger.presentation.ui.adapter.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.network.response.transaction.Transaksi
import com.papierflieger.databinding.ItemAdminTransactionBinding

class AdminTransactionAdapter : RecyclerView.Adapter<AdminTransactionAdapter.TransactionViewHolder>() {

    private var diffCallback = object : DiffUtil.ItemCallback<Transaksi>(){
        override fun areItemsTheSame(oldItem: Transaksi, newItem: Transaksi): Boolean {
            return oldItem.tokenTransaction == newItem.tokenTransaction
        }

        override fun areContentsTheSame(oldItem: Transaksi, newItem: Transaksi): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private lateinit var onAdminTransactionItem: OnAdminTransactionItem

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setItem(value : List<Transaksi?>?) = differ.submitList(value)

    fun actionClick(onAdminTransactionItem: OnAdminTransactionItem){
        this.onAdminTransactionItem = onAdminTransactionItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemAdminTransactionBinding.inflate(LayoutInflater.from(
            parent.context), parent, false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bindingItem(item, position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class TransactionViewHolder(
        private val binding : ItemAdminTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindingItem(item: Transaksi?, position: Int) {
            with(binding){
                if (position < 1) vGap.visibility = View.INVISIBLE
                else vGap.visibility = View.GONE

                if (item?.status == true) {
                    cvPaymentStatusPending.visibility = View.GONE
                    cvPaymentStatusSuccess.visibility = View.VISIBLE
                } else {
                    cvPaymentStatusPending.visibility = View.VISIBLE
                    cvPaymentStatusSuccess.visibility = View.GONE
                }

                tvTripValue.text = item?.trip
                tvFullNameValue.text = item?.user?.fullName
                tvEmailValue.text = item?.user?.email
                tvTotalPriceValue.text = item?.totalPrice.toString()

                if (item?.paymentId != null) {
                    tvBankName.visibility = View.VISIBLE
                    tvBankNameValue.visibility = View.VISIBLE
                    tvAccountNumber.visibility = View.VISIBLE
                    tvAccountNumberValue.visibility = View.VISIBLE
                    tvAccountName.visibility = View.VISIBLE
                    tvAccountNameValue.visibility = View.VISIBLE

                    tvBankNameValue.text = item.payment?.bankName
                    tvAccountNumberValue.text = item.payment?.accountNumber.toString()
                    tvAccountNameValue.text = item.payment?.accountName
                } else {
                    tvBankName.visibility = View.GONE
                    tvBankNameValue.visibility = View.GONE
                    tvAccountNumber.visibility = View.GONE
                    tvAccountNumberValue.visibility = View.GONE
                    tvAccountName.visibility = View.GONE
                    tvAccountNameValue.visibility = View.GONE
                }

                if (item?.id != null) {
                    btnDelete.setOnClickListener { onAdminTransactionItem.itemDeleteClicked(item.id) }
                }
            }
        }
    }

    interface OnAdminTransactionItem {
        fun itemDeleteClicked(id : Int)
    }

}