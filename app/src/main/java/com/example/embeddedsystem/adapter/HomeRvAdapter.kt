package com.example.embeddedsystem.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.embeddedsystem.R
import com.example.embeddedsystem.model.Device

class HomeRvAdapter(
    private var devices : List<Device>,
    private val clickListener : (Device) -> Unit,
    private val autoClickListener : (Device) -> Unit) : RecyclerView.Adapter<HomeRvAdapter.HomeViewHolder>() {
    class HomeViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.nameTextView)
        val switch = view.findViewById<Switch>(R.id.deviceSwitch)
        val switchAuto = view.findViewById<Switch>(R.id.deviceSwitchAuto)
        val avatar = view.findViewById<ImageView>(R.id.deviceImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.name.text = devices[position].name
        if(devices[position].name == "Cửa") {
            holder.avatar.setImageResource(R.drawable.door)
        } else if(devices[position].name == "Đèn") {
            holder.avatar.setImageResource(R.drawable.bulb)
        }else if(devices[position].name == "Quạt") {
            holder.avatar.setImageResource(R.drawable.fan)
        }else if(devices[position].name == "Điều hòa") {
            holder.avatar.setImageResource(R.drawable.ac)
        }else if(devices[position].name == "Báo cháy") {
            holder.avatar.setImageResource(R.drawable.fire_alarm)
        }else if(devices[position].name == "Mái che") {
            holder.avatar.setImageResource(R.drawable.roof)
        }
        holder.switch.isChecked = devices[position].status
        holder.switchAuto.isChecked = devices[position].auto
        holder.switch.setOnClickListener {
            devices[position].status = !devices[position].status
            clickListener(devices[position])
        }
        holder.switchAuto.setOnClickListener {
            devices[position].auto = !devices[position].auto
            autoClickListener(devices[position])
        }
    }

    fun updateDevices(newDevices : List<Device>) {
        devices = newDevices
        notifyDataSetChanged()
    }
}