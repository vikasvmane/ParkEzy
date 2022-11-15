package com.whiterwalkers.parkezy.ui.parkinglots.manageparking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.whiterwalkers.parkezy.R
import com.whiterwalkers.parkezy.databinding.FragmentManageParkingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ManageParkingFragment : Fragment() {
    private var _binding: FragmentManageParkingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val manageParkingViewModel =
            ViewModelProvider(this)[ManageParkingViewModel::class.java]

        _binding = FragmentManageParkingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerviewParkings

        viewLifecycleOwner.lifecycleScope.launch {
            manageParkingViewModel.getParkingSpot(1)
        }
        manageParkingViewModel.parkingSpotLiveData.observe(viewLifecycleOwner) {
            val adapter = ParkingListRecyclerViewAdapter(requireContext(), it, object :
                ParkingSlotCallback {
                override fun onParkingRateClick(pos: Int) {
                    //Open Rate management fragment
                }

                override fun onParkingScheduleClick(pos: Int) {
                    // Open schedule management fragment
                }

                override fun onParkingDeleteClick(pos: Int) {
                    // Call delete API
                }

                override fun toggleAvailability(pos: Int, isAvailable: Boolean) {
                    // Call API to toggle availability
                }
            })
            val layoutManager = LinearLayoutManager(requireContext())
            layoutManager.orientation = LinearLayoutManager.VERTICAL

            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        }
        binding.fabCreateParking.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_nav_manage_parking_to_nav_create_parking)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}