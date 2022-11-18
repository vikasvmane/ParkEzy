package com.whiterwalkers.parkezy.ui.managevehicles.addcar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.whiterwalkers.parkezy.databinding.FramgentAddCarBinding
import com.whiterwalkers.parkezy.model.pojos.Car
import com.whiterwalkers.parkezy.model.pojos.Size
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.random.Random


@AndroidEntryPoint
class AddCarFragment : Fragment() {

    private var _binding: FramgentAddCarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: AddCarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[AddCarViewModel::class.java]

        _binding = FramgentAddCarBinding.inflate(inflater, container, false)
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
        binding.btnSaveCar.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.addCar(
                    Car(
                        registrationNumber = getRandomId(),
                        nickName = binding.edCarName.text.toString(),
                        make = binding.edCarMake.text.toString(),
                        model = binding.edCarModel.text.toString(),
                        type = getSizeFromUserSelection(),
                        isPrimary = false
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

    private fun getRandomId() = Random.nextInt(100, 10000).toLong()
}