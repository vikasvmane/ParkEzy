package com.whiterwalkers.parkezy.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.whiterwalkers.parkezy.databinding.FragmentParkInfoBottomSheetListDialogBinding
import com.whiterwalkers.parkezy.ui.activities.ScannerActivity
import com.whiterwalkers.parkezy.ui.utils.ARG_ITEM_COUNT


class ParkInfoBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentParkInfoBottomSheetListDialogBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentParkInfoBottomSheetListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivQrCodeScanner.setOnClickListener {
            startActivity(Intent(requireActivity(), ScannerActivity::class.java))
            dialog?.cancel()
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