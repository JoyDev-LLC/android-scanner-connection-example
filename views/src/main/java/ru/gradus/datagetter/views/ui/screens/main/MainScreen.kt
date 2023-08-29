package ru.gradus.datagetter.views.ui.screens.main

import android.os.Bundle
import android.view.InputDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.gradus.datagetter.views.R
import ru.gradus.datagetter.views.databinding.FragmentMainScreenBinding
import ru.gradus.datagetter.views.ui.screens.details.DetailsScreen
import ru.gradus.datagetter.views.ui.screens.main.adapter.InputDevicesAdapter

class MainScreen : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding

    private val adapter = InputDevicesAdapter {
        val fragment = DetailsScreen()
        fragment.arguments = bundleOf("deviceId" to it)
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(DetailsScreen.TAG)
            .replace(R.id.main_content, fragment, DetailsScreen.TAG)
            .commit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        val inputDevices = InputDevice.getDeviceIds().map { InputDevice.getDevice(it) }
        adapter.items.clear()
        adapter.items.addAll(inputDevices)
        binding.recycler.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recycler.adapter = adapter
        return binding.root
    }

}