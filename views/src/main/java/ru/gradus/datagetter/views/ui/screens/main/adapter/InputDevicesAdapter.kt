package ru.gradus.datagetter.views.ui.screens.main.adapter

import android.view.InputDevice
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.gradus.datagetter.utils.getSources
import ru.gradus.datagetter.views.databinding.ItemInputDeviceBinding
import ru.gradus.datagetter.R

class InputDevicesAdapter(private val onClickListener: (Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val items = mutableListOf<InputDevice>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemInputDeviceBinding.inflate(layoutInflater)
        return InputDeviceViewHolder(binding, onClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? InputDeviceViewHolder)?.bind(items[position])
    }
}

class InputDeviceViewHolder(private val binding: ItemInputDeviceBinding, private val onClickListener: (Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: InputDevice) {
        with(itemView.resources) {
            with(binding){
                deviceId.text = getString(R.string.device_id, "${item.id}")
                name.text = getString(R.string.device_name, item.name)
                sources.text = getString(R.string.device_sources, getSources(item.sources))
            }
        }
        itemView.setOnClickListener {
            onClickListener(item.id)
        }
    }
}
