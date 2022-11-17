package com.whiterwalkers.parkezy.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.whiterwalkers.parkezy.databinding.FragmentParkInfoBottomSheetListDialogBinding
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import com.whiterwalkers.parkezy.ui.activities.ScannerActivity
import com.whiterwalkers.parkezy.ui.utils.ARG_ITEM_COUNT


class ParkInfoBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentParkInfoBottomSheetListDialogBinding? = null

    private val binding get() = _binding!!

    private lateinit var mPartSpot: ParkingSpot

    private val TAG = ParkInfoBottomSheetFragment::class.java.simpleName
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // var safeArgs: ParkInfoBottomSheetFragmentArgs by navArgs()
        mPartSpot = arguments?.getParcelable("Park")!!
        _binding = FragmentParkInfoBottomSheetListDialogBinding.inflate(inflater, container, false)
        Log.d(TAG, "parking spot ${mPartSpot.parkingName}")
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivQrCodeScanner.setOnClickListener {
            startActivity(Intent(requireActivity(), ScannerActivity::class.java))
            dialog?.cancel()
        }
        mPartSpot.let {
            binding.tvTitle.text = it.parkingName
            binding.tvSubtitle.text = it.address
            binding.tvDescription.text = it.info
            binding.parkingRating.rating = it.ratings ?: 0.0f
        }
    }


    companion object {

        fun newInstance(itemCount: Int): ParkInfoBottomSheetFragment =
            ParkInfoBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
            }

        val TAG = ParkInfoBottomSheetFragment::class.java.simpleName

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}