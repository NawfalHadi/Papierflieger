package com.papierflieger.presentation.ui.adapter.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.network.response.user.User
import com.papierflieger.databinding.ItemAdminUserBinding
import com.papierflieger.wrapper.convertDateFormat

class AdminUserAdapter : RecyclerView.Adapter<AdminUserAdapter.OrderViewHolder>() {

    private var diffCallback = object : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.fullName == newItem.fullName
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private lateinit var onAdminUserItem: OnAdminUserItem

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setItem(value : List<User?>?) = differ.submitList(value)

    fun actionClick(onAdminUserItem: OnAdminUserItem){
        this.onAdminUserItem = onAdminUserItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemAdminUserBinding.inflate(LayoutInflater.from(
            parent.context), parent, false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bindingItem(item, position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class OrderViewHolder(
        private val binding : ItemAdminUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindingItem(item: User?, position: Int) {
            with(binding){
                if (position < 1) vGap.visibility = View.INVISIBLE
                else vGap.visibility = View.GONE

                tvUsernameValue.text = item?.username
                tvFullNameValue.text = item?.fullName
                tvEmailValue.text = item?.email
                tvDateBirthValue.text = item?.birthdate?.let { convertDateFormat(it) }
                tvNationalityValue.text = item?.nationality

                if (item?.role != null && item.role == "Admin") {
                    cvAdmin.visibility = View.VISIBLE
                    cvCustomer.visibility = View.GONE

                    vGap1.visibility = View.GONE
                    btnEdit.visibility = View.GONE
                } else {
                    cvAdmin.visibility = View.GONE
                    cvCustomer.visibility = View.VISIBLE

                    vGap1.visibility = View.VISIBLE
                    btnEdit.visibility = View.VISIBLE

                    if (item?.id != null) {
                        btnEdit.setOnClickListener { onAdminUserItem.itemUpdateClicked(item.id) }
                    }
                }
            }
        }
    }

    interface OnAdminUserItem {
        fun itemUpdateClicked(id : Int)
    }

}