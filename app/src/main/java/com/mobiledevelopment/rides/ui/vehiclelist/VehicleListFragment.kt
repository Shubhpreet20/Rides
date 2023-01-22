package com.mobiledevelopment.rides.ui.vehiclelist

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mobiledevelopment.rides.MainViewModel
import com.mobiledevelopment.rides.Utils
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
            val result = Utils.isValidInput(size)
            val isValidInput = result.first
            val errorMessage = result.second
            if (isValidInput) {
                viewModel.getVehiclesList(size.toInt())
                viewModel.setLoading(true)
            } else {
                AlertDialog.Builder(requireContext())
                    .setMessage(errorMessage)
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.cancel()
                    }.create().show()
            }
        }

        val vehicleArrayAdapter = VehicleArrayAdapter(requireContext(), mutableListOf())
        binding.list.adapter = vehicleArrayAdapter

        binding.list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val vehicle = vehicleArrayAdapter.getItem(position) as Vehicle
            findNavController().navigate(
                VehicleListFragmentDirections.actionVehicleListFragmentToVehicleDetailsFragment(
                    vehicle
                )
            )
        }

        viewModel.response.observe(requireActivity()) { response ->
            if (response is Response.Success<*>) {
                val vehicleList =
                    (response as Response.Success<List<Vehicle>>).data as MutableList<Vehicle>
                vehicleList.sortedBy { it.vin }
                vehicleArrayAdapter.setVehList(vehicleList)
                Log.d("TAG", vehicleList.toString())
            } else {
                val errorMessage = (response as Response.Error<String>).error.message
                Log.d("TAG", errorMessage.toString())
            }
            binding.listSwipe.isRefreshing = false
            viewModel.setLoading(false)
        }


        //Pull down to refresh
        binding.listSwipe.setOnRefreshListener {
            viewModel.getVehiclesList(viewModel.sizeData.value)
        }

        //Show or Hide progress bar
        viewModel.loading.observe(requireActivity()) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }


    }

}