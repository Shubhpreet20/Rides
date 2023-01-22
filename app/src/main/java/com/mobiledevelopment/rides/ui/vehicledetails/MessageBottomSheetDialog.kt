package com.mobiledevelopment.rides.ui.vehicledetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mobiledevelopment.rides.R

class MessageBottomSheetDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_mesage_bottom_sheet,container,false)
        val messageTv = view.findViewById<TextView>(R.id.messageTv);
        val estCarbonEmission = requireArguments().getDouble(EXTRA_CARBON_EMISSION)
        messageTv.text = "Total estimated carbon emission is $estCarbonEmission"
        return view
    }

    companion object{
        const val EXTRA_CARBON_EMISSION = "extra_carbon_emission"
    }

}