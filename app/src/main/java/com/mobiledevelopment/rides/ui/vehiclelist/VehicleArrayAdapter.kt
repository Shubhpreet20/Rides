package com.mobiledevelopment.rides.ui.vehiclelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.mobiledevelopment.rides.R
import com.mobiledevelopment.rides.databinding.ListItemVehicleListBinding
import com.mobiledevelopment.rides.model.Vehicle

class VehicleArrayAdapter(
    context: Context,
    var vehicleList: MutableList<Vehicle>

) : ArrayAdapter<Vehicle>(context,0, vehicleList) {

    fun setVehList(vehicleList: MutableList<Vehicle>) {
        this.vehicleList.clear()
        this.vehicleList.addAll(vehicleList)
        notifyDataSetChanged()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var binding: ListItemVehicleListBinding = ListItemVehicleListBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.apply {
            val vehicle = getItem(position)
            makeModelTv.text = vehicle?.makeAndModel
            vinTv.text = vehicle?.vin
        }
        return binding.root
    }

}