package com.whiterwalkers.parkezy.ui.home.transaction

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.whiterwalkers.parkezy.R
import com.whiterwalkers.parkezy.databinding.FragmentTransactionBinding
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import com.whiterwalkers.parkezy.ui.activities.ScannerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null

    private lateinit var mPartSpot: ParkingSpot

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mPartSpot = arguments?.getParcelable("Park")!!
        viewModel =
            ViewModelProvider(this)[TransactionViewModel::class.java]

        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.loading.observe(viewLifecycleOwner) {
            //Show progress
        }
        viewModel.success.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCheckout.setOnClickListener {
            resultLauncher.launch(Intent(requireActivity(), ScannerActivity::class.java))
        }
        binding.buttonClose.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_nav_transaction_to_nav_home)
        }
        setParkingDetails()
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                if (mPartSpot.parkingId == data?.getStringExtra("QRData")?.toInt()) {
                    binding.layoutSummaryGroup.visibility = VISIBLE
                    setSummary()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "QR not valid. Please check again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    private fun setParkingDetails() {
        binding.simpleChronometer.base = SystemClock.elapsedRealtime()
        binding.simpleChronometer.start()

        binding.textParkingName.text = mPartSpot.parkingName
        binding.textParkingAddress.text = mPartSpot.address
        binding.textParkingRate.text = mPartSpot.rate?.standardRate.toString()
        binding.textCarDetails.text = "Tata Nexon MH14HQ2342"
        binding.textPaymentDetails.text = "Wallet"
    }

    private fun setSummary() {
        binding.buttonCheckout.visibility = GONE
        val elapsedMillis: Long = SystemClock.elapsedRealtime() - binding.simpleChronometer.base
        val hours = ((elapsedMillis / (1000 * 60 * 60)) % 24).toInt()
        val finalHours = if (hours == 0) 1 else hours
        binding.textTotalCharges.text =
            (finalHours * (mPartSpot.rate?.standardRate ?: 40).toInt()).toString()
        binding.simpleChronometer.stop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}