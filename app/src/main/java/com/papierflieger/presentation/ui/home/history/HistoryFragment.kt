package com.papierflieger.presentation.ui.home.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.papierflieger.R
import com.papierflieger.data.network.response.transaction.Order
import com.papierflieger.data.network.response.transaction.Ticket
import com.papierflieger.data.network.response.transaction.Transaction
import com.papierflieger.databinding.FragmentHistoryBinding
import com.papierflieger.presentation.bussiness.NotificationViewModel
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.ui.adapter.HistoryAdapter
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val sessionViewModel : SessionViewModel by viewModels()
    private val notificationViewModel : NotificationViewModel by viewModels()

    private val historyAdapter : HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingNotification()
        observeData()
    }

    private fun observeData() {
        sessionViewModel.getToken().observe(viewLifecycleOwner) {token ->
            sessionViewModel.getHistoriesUser(token).observe(viewLifecycleOwner){
                when(it){
                    is Resource.Success -> {
                        if (it.payload?.ticket.isNullOrEmpty().not()) {
                            binding.apply {
                                ivNotFound.visibility = View.GONE
                                tvNotFound.visibility = View.GONE
                            }
                            it.payload?.let { ticket -> historyAdapter.setItem(ticket) }
                            initList()
                        } else {
                            binding.apply {
                                ivNotFound.visibility = View.VISIBLE
                                tvNotFound.visibility = View.VISIBLE
                                rvHistoryTransaction.visibility = View.GONE
                            }
                        }
                    }
                    is Resource.Empty -> {}
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun initList() {
        binding.ivNotFound.visibility = View.INVISIBLE
        with(binding.rvHistoryTransaction){
            visibility = View.VISIBLE
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
            adapter = historyAdapter
        }

        historyAdapter.listener(object : HistoryAdapter.OnHistoryCardAction{
            override fun cardAction(ticket: Ticket) {
                val mBundle = Bundle()
                mBundle.putParcelable(DetailHistoryActivity.TICKET_LIST_KEY, ticket)

                val mIntent = Intent(activity, DetailHistoryActivity::class.java)
                mIntent.putExtras(mBundle)
                startActivity(mIntent)
            }
        })
    }

    private fun bindingNotification() {
        sessionViewModel.getToken().observe(viewLifecycleOwner) { token ->
            notificationViewModel.getNotifications(token).observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Empty -> {}
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val notification = it.payload?.notifikasi?.count { notification -> !notification.read }
                        if (notification != null && notification > 0) {
                            updateNotification(token)
                        }
                    }
                }
            }
        }
    }

    private fun updateNotification(token: String) {
        notificationViewModel.updateNotification(token).observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Empty -> {}
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val bottomNavigation = requireActivity().findViewById<BottomNavigationView>(
                        R.id.bottomNavigation)
                    bottomNavigation.removeBadge(R.id.historyFragment)
                }
            }
        }
    }

}