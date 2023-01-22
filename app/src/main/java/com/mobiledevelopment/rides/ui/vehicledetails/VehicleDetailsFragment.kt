package com.mobiledevelopment.rides.ui.vehicledetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.mobiledevelopment.rides.MainViewModel
import com.mobiledevelopment.rides.Utils
import com.mobiledevelopment.rides.databinding.FragmentVehicleDetailsBinding

class VehicleDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = VehicleDetailsFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentVehicleDetailsBinding
    private val args: VehicleDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVehicleDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val vehicle = args.vehicle
            vinTv.text = vehicle.vin
            mMTv.text = vehicle.makeAndModel
            colorTv.text = vehicle.color
            carTypeTv.text = vehicle.carType
        }

        binding.carbonEmBtn.setOnClickListener {
            val messageBottomSheetDialog = MessageBottomSheetDialog()
            val estCarbonEmission = Utils.getEstCarbonEmission(args.vehicle.kilometrage)
            val args = Bundle()
            args.putDouble(MessageBottomSheetDialog.EXTRA_CARBON_EMISSION, estCarbonEmission)
            messageBottomSheetDialog.arguments = args
            messageBottomSheetDialog.show(childFragmentManager, "MessageBottomSheetDialog")
        }
    }




}