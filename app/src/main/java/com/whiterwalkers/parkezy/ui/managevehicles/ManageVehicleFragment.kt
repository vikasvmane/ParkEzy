package com.whiterwalkers.parkezy.ui.managevehicles

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
import com.whiterwalkers.parkezy.databinding.FragmentManageVehicleBinding
import com.whiterwalkers.parkezy.model.pojos.Car
import com.whiterwalkers.parkezy.ui.parkinglots.manageparking.ParkingSlotCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ManageVehicleFragment : Fragment() {
    private var _binding: FragmentManageVehicleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val manageParkingViewModel =
            ViewModelProvider(this)[ManageVehicleViewModel::class.java]

        _binding = FragmentManageVehicleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerviewCar

        viewLifecycleOwner.lifecycleScope.launch {
            manageParkingViewModel.getVehicleList(1)
        }
        manageParkingViewModel.carListData.observe(viewLifecycleOwner) {
            val adapter = CarListAdapter(requireContext(), it, object :
                CarSelectionCallback{
                override fun onSelectedVehicle(car: Car) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        manageParkingViewModel.saveVehicleData(car)
                    }
                }
            })
            val layoutManager = LinearLayoutManager(requireContext())
            layoutManager.orientation = LinearLayoutManager.VERTICAL

            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        }
        binding.fabAddCar.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_nav_manage_parking_to_nav_add_car)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}