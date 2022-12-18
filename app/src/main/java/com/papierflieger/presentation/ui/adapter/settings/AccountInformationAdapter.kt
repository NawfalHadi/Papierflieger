package com.papierflieger.presentation.ui.adapter.settings

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.local.model.TitleValueModel
import com.papierflieger.databinding.ItemProfileAccountInformationBinding

class AccountInformationAdapter : RecyclerView.Adapter<AccountInformationAdapter.AccountInformationViewHolder>(){

    private var list : ArrayList<TitleValueModel> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(data : ArrayList<TitleValueModel>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun clearItem(){
        this.list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccountInformationViewHolder {
        val binding = ItemProfileAccountInformationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AccountInformationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountInformationViewHolder, position: Int) {
        val item = list[position]
        holder.bindingView(item)
    }

    override fun getItemCount(): Int = list.size

    class AccountInformationViewHolder(
        private val binding : ItemProfileAccountInformationBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bindingView(item: TitleValueModel) {
            with(binding){
                tvInformation.text = item.title
                tvInformationDetail.text = item.value
            }
        }

    }
}