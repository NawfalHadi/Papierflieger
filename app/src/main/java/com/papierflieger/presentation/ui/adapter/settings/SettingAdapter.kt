package com.papierflieger.presentation.ui.adapter.settings

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.R
import com.papierflieger.data.local.model.SettingModel
import com.papierflieger.databinding.ItemProfileAccountBinding

class SettingAdapter : RecyclerView.Adapter<SettingAdapter.SettingViewHolder>() {

    private lateinit var onMenuActionCallback: OnMenuActionCallback
    private var list : ArrayList<SettingModel> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(menus : ArrayList<SettingModel>){
        list.clear()
        list.addAll(menus)
        notifyDataSetChanged()
    }

    fun actionMenu(onMenuActionCallback: OnMenuActionCallback){
        this.onMenuActionCallback = onMenuActionCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val binding = ItemProfileAccountBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SettingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        val item = list[position]
        holder.bindingView(item)
    }

    override fun getItemCount(): Int = list.size

    inner class SettingViewHolder(
        private val binding : ItemProfileAccountBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bindingView(item: SettingModel) {
            with(binding){
                tvAccount.text = item.title
                ivAccount.setImageResource(item.icon!!)

                btnAction.setOnClickListener { onMenuActionCallback.menuClicked(item.action) }
                cardAction.setOnClickListener { onMenuActionCallback.menuClicked(item.action) }
            }
        }
    }

    interface OnMenuActionCallback {
        fun menuClicked(id : Int?)
    }
}