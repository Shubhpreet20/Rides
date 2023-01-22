package com.mobiledevelopment.rides.ui.vehiclelist

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mobiledevelopment.rides.MainViewModel
import com.mobiledevelopment.rides.databinding.FragmentVehicleListBinding
import com.mobiledevelopment.rides.model.Response
import com.mobiledevelopment.rides.model.Vehicle

class VehicleListFragment : Fragment() {

    companion object {
        fun newInstance() = VehicleListFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentVehicleListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVehicleListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getVehiclesBtn.setOnClickListener {
            val size = binding.listSizeEt.text.toString()
            if (!TextUtils.isEmpty(size) && size.isDigitsOnly())
                viewModel.getVehiclesList(size.toInt())
            else
                Toast.makeText(requireContext(), "Enter a valid list size!", Toast.LENGTH_SHORT)
                    .show()
        }

        val vehicleArrayAdapter = VehicleArrayAdapter(requireContext(), mutableListOf())
        binding.vehiclesListView.adapter = vehicleArrayAdapter

        binding.vehiclesListView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val vehicle = vehicleArrayAdapter.getItem(position) as Vehicle
                findNavController().navigate(
                    VehicleListFragmentDirections.actionVehicleListFragmentToVehicleDetailsFragment(
                        vehicle
                    )
                )
            }

        viewModel.response.observe(requireActivity()) { response ->
            if (response is Response.Success<*>) {
                val vehicleList = (response as Response.Success<List<Vehicle>>).data as MutableList<Vehicle>
                vehicleList.sortedBy { it.vin }
                vehicleArrayAdapter.setVehList(vehicleList)
            } else {
                val errorMessage = (response as Response.Error<String>).error.message
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

}