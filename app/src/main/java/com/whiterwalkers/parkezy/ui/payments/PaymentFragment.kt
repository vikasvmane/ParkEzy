package com.whiterwalkers.parkezy.ui.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.whiterwalkers.parkezy.databinding.FragmentPaymentBinding
import com.whiterwalkers.parkezy.model.pojos.Payment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: PaymentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[PaymentsViewModel::class.java]

        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.loading.observe(viewLifecycleOwner) {
            //Show progress
        }
        viewModel.success.observe(viewLifecycleOwner) {

        }
        viewModel.transaction.observe(viewLifecycleOwner) {
            val layoutManager = LinearLayoutManager(requireContext())
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            binding.recyclerviewPayment.layoutManager = layoutManager
            binding.recyclerviewPayment.adapter =
                PaymentRecyclerViewAdapter(requireContext(), it, object : PaymentSelectionCallback {
                    override fun onSelectedPaymentOption(payment: Payment) {
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.saveSelectedPayment(payment)
                        }
                    }
                })
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPaymentList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}