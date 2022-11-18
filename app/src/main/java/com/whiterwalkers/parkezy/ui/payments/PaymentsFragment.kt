package com.whiterwalkers.parkezy.ui.payments

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import com.whiterwalkers.parkezy.databinding.FragmentCreateParkingBinding
import com.whiterwalkers.parkezy.model.pojos.Location
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import com.whiterwalkers.parkezy.model.pojos.Rate
import com.whiterwalkers.parkezy.model.pojos.Size
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.random.Random


@AndroidEntryPoint
class PaymentsFragment : Fragment() {

    private var _binding: FragmentCreateParkingBinding? = null

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

        _binding = FragmentCreateParkingBinding.inflate(inflater, container, false)
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
        binding.buttonSubmit.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.createParking(
                    ParkingSpot(
                        parkingId = getRandomId(),
                        parkingName = binding.editTextName.text.toString(),
                        address = binding.editTextAddress.text.toString(),
                        info = binding.editTextInfo.text.toString(),
                        rate = Rate(standardRate = binding.editTextRate.text.toString().toDouble()),
                        isAvailable = binding.switchAvailability.isChecked,
                        isEvEnabled = binding.switchEv.isChecked,
                        size = getSizeFromUserSelection().name,
                        location = getLocationFromAddress(binding.editTextAddress.text.toString()),
                        spotSchedule = null
                    )
                )
            }
        }
    }

    private fun getSizeFromUserSelection(): Size {
        if (binding.radioHatchback.isChecked)
            return Size.HATCHBACK
        if (binding.radioSedan.isChecked)
            return Size.SEDAN
        if (binding.radioSuv.isChecked)
            return Size.SUV
        //fallback
        return Size.HATCHBACK
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getLocationFromAddress(strAddress: String?): Location? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng? = null
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            p1 = LatLng(location.latitude, location.longitude)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return Location(p1?.latitude!!, p1.longitude!!)
    }

    private fun getRandomId() = Random.nextInt(100, 10000)
}