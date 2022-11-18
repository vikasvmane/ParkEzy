package com.whiterwalkers.parkezy.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.whiterwalkers.parkezy.R
import com.whiterwalkers.parkezy.databinding.FragmentParkInfoBottomSheetListDialogBinding
import com.whiterwalkers.parkezy.model.pojos.Car
import com.whiterwalkers.parkezy.model.pojos.Location
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import com.whiterwalkers.parkezy.model.pojos.Payment
import com.whiterwalkers.parkezy.ui.activities.ScannerActivity
import com.whiterwalkers.parkezy.ui.utils.DataStore
import com.whiterwalkers.parkezy.ui.utils.USER_SELECTED_CAR
import com.whiterwalkers.parkezy.ui.utils.USER_SELECTED_PAYMENT
import kotlinx.coroutines.flow.map


class ParkInfoBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentParkInfoBottomSheetListDialogBinding? = null

    private val binding get() = _binding!!

    private lateinit var mPartSpot: ParkingSpot

    private val TAG = ParkInfoBottomSheetFragment::class.java.simpleName
    private lateinit var location: Location
    private var selectedCar: Car? = null
    private var selectedPayment: Payment? = null
    private val gson = Gson()
    //private val Context.dataStore by preferencesDataStore("user_preferences")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mPartSpot = arguments?.getParcelable("Park")!!
        location = arguments?.getParcelable("Location")!!
        _binding = FragmentParkInfoBottomSheetListDialogBinding.inflate(inflater, container, false)
        //val dataStore = requireContext().dataStore
//        dataStore.data.map {
//            selectedCar =
//                gson.fromJson(it[stringPreferencesKey(USER_SELECTED_CAR)], Car::class.java)
//            selectedPayment =
//                gson.fromJson(it[stringPreferencesKey(USER_SELECTED_PAYMENT)], Payment::class.java)
//        }
        selectedCar = DataStore.getSelectedCar()
        selectedPayment = DataStore.getSelectedPayment()
        Log.d(TAG, "parking spot ${mPartSpot.parkingName}")
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivQrCodeScanner.setOnClickListener {
            if (selectedCar == null)
                Toast.makeText(
                    requireContext(),
                    "Please select car from manage car section",
                    Toast.LENGTH_SHORT
                ).show()
            else if (selectedPayment == null)
                Toast.makeText(
                    requireContext(),
                    "Please select payment option from payment section",
                    Toast.LENGTH_SHORT
                ).show()
            else
                resultLauncher.launch(Intent(requireActivity(), ScannerActivity::class.java))
        }
        binding.ivDirection.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=${location.lat},${location.lng}&daddr=${mPartSpot.location?.lat},${mPartSpot.location?.lng}")
            )
            intent.setClassName(
                "com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity"
            )
            startActivity(intent)
        }
        mPartSpot.let {
            binding.tvTitle.text = it.parkingName
            binding.tvSubtitle.text = it.address
            binding.tvDescription.text = it.info
            binding.parkingRating.rating = it.ratings ?: 0.0f
        }
        if (selectedCar != null)
            binding.textSelectedCar.text = "${selectedCar!!.make} ${selectedCar!!.model}"
        else
            binding.textSelectedCar.text = "Select Car"
        if (selectedPayment != null)
            binding.textSelectedPayment.text = "${selectedPayment!!.paymentName}"
        else
            binding.textSelectedPayment.text = "Select Payment"
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                if (mPartSpot.parkingId == data?.getStringExtra("QRData")?.toInt()) {
                    dialog?.cancel()
                    val bundle = Bundle().apply {
                        putParcelable("Park", mPartSpot)
                    }
                    val navHostFragment =
                        activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController
                        .navigate(R.id.action_nav_bottom_sheet_to_nav_transaction, bundle)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "QR not valid. Please check again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}