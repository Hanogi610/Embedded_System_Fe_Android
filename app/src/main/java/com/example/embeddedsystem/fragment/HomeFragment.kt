package com.example.embeddedsystem.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.embeddedsystem.R
import com.example.embeddedsystem.adapter.HomeRvAdapter
import com.example.embeddedsystem.model.Device
import com.example.embeddedsystem.model.History
import com.example.embeddedsystem.service.DeviceApi
import com.example.embeddedsystem.service.DeviceApiHelper
import com.example.embeddedsystem.service.HistoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Date

class HomeFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var devices : MutableList<Device>
    lateinit var adapter : HomeRvAdapter
    var houseId = 0

    companion object {
        fun newInstance(param: Int): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putInt("param_key", param)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments?.getInt("param_key")
        if (args != null) {
            houseId = args
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        devices = mutableListOf()
        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = HomeRvAdapter(devices, ::changeSwitchStatus, ::changeSwitchAuto)
        recyclerView.adapter = adapter
        lifecycleScope.launch(Dispatchers.IO) {
            devices = DeviceApi.retrofitService.getDevicesByHouseId(houseId).toMutableList()
            launch(Dispatchers.Main) {
                adapter.updateDevices(devices)
            }
        }
//        while (true) {
//            fetchDevices()
//        }
    }

    private fun changeSwitchStatus(device: Device) {
        lifecycleScope.launch(Dispatchers.IO) {
            val updatedDevice : Response<Device> = DeviceApi.retrofitService.updateDevice(device)
            val responseHistory : Response<History> = HistoryApi.retrofitService.addHistory(
                History(
                    deviceName = device.name,
                    status = device.status,
                    time = Date().time,
                    house = device.house
                )
            )
            if(!responseHistory.isSuccessful){
                println("Add history failed")
            }
        }
    }

    private fun changeSwitchAuto(device: Device) {
        lifecycleScope.launch(Dispatchers.IO) {
            val updatedDevice : Response<Device> = DeviceApi.retrofitService.updateDevice(device)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchDevices()
    }

    private fun fetchDevices() {
        lifecycleScope.launch {
            while (true) {
                Log.d("HomeFragment", "fetchDevices")
                DeviceApiHelper.getDevices(houseId)
                    .flowOn(Dispatchers.IO)
                    .collect {
                        devices = it.toMutableList()
                        adapter.updateDevices(devices)
                    }
                delay(2500) // delay for 1 second
            }
        }
    }

}