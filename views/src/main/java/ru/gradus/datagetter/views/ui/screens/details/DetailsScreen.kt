package ru.gradus.datagetter.views.ui.screens.details

import android.os.Bundle
import android.view.InputDevice
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gradus.datagetter.utils.Decoder
import ru.gradus.datagetter.utils.getSources
import ru.gradus.datagetter.views.databinding.FragmentDetailsScreenBinding
import ru.gradus.datagetter.R

class DetailsScreen : Fragment() {
    companion object {
        const val TAG = "details_screen"
    }

    private lateinit var binding: FragmentDetailsScreenBinding
    private lateinit var decoder: Decoder
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailsScreenBinding.inflate(inflater, container, false)
        binding.backArrow.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("deviceId")?.let {
            initDeviceData(it)
        }
    }

    private fun initDeviceData(deviceId: Int) {
        decoder = Decoder.Keyboard(deviceId)
        val inputDevice = InputDevice.getDevice(deviceId)
        with(binding) {
            binding.deviceId.text = getString(R.string.device_id, "${inputDevice.id}")
            name.text = getString(R.string.device_name, inputDevice.name)
            sources.text = getString(R.string.device_sources, getSources(inputDevice.sources))
            root.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    binding.receivedData.text = decoder.handleKey(event)
                }
                true
            }
            receivedData.setOnClickListener {
                (decoder as? Decoder.Keyboard)?.clearTemp()
                binding.receivedData.text = ""
            }
            root.isFocusable = true
        }
    }
}
