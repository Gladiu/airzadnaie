package com.example.iot_lab6.ui.ledPanel

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.iot_lab6.R
import com.example.iot_lab6.definitions.ledPanel.SetSingleLedPanelRequest
import com.example.iot_lab6.network.NetworkService
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LedPanelFragment : Fragment() {

    var current_color : ColorEnvelope = ColorEnvelope(-65535)
    lateinit var btn_set_color: Button
    lateinit var btn_clear_all: Button
    lateinit var btn_set_all: Button
    lateinit var switch_mode: SwitchCompat

    val tablica_ledow: MutableList<View> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_led_panel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        CoroutineScope(Dispatchers.IO).launch {
//            withContext(Dispatchers.Main) {
//                NetworkService.getInstance("http://192.168.37.18").launchRequest(
//                    SetSingleLedPanelRequest(0,0,1,2,3),
//                    NetworkService.getInstance().malinaAPI::setSingleLedPanel,
//                    requireContext()
//                )
//
//                val x = NetworkService.getInstance().launchRequest(
//                    NetworkService.getInstance().malinaAPI::getHumidity,
//                    requireContext()
//                )
//            }
//        }

        btn_set_color = view.findViewById(R.id.btn_set_color)
        btn_clear_all = view.findViewById(R.id.btn_wyczysc_wszystkie)
        btn_set_all = view.findViewById(R.id.btn_set_all)
        switch_mode = view.findViewById(R.id.switch_tryb_rysowania)


        btn_set_color.setOnClickListener {
            val builder = ColorPickerDialog.Builder(requireContext())
                .setTitle("ColorPicker Dialog")
                .setPreferenceName("MyColorPickerDialog")
                .setPositiveButton("tak",
                    ColorEnvelopeListener { envelope, fromUser -> run {
                        current_color = envelope
                        btn_set_color.setBackgroundColor(current_color.color)
                    } })
                .setNegativeButton(
                    "cancel"
                ) { dialogInterface, i -> dialogInterface.dismiss() }
                .attachAlphaSlideBar(true) // the default value is true.
                .attachBrightnessSlideBar(true) // the default value is true.
                .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
                .show()
        }

        btn_clear_all.setOnClickListener {
            tablica_ledow.forEach{it.setBackgroundColor(Color.BLACK)}
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    NetworkService.getInstance("http://192.168.37.18").launchRequest(
                        NetworkService.getInstance().malinaAPI::clearAllLedPanel,
                        requireContext()
                    )
                }
            }

        }

        btn_set_all.setOnClickListener {
            tablica_ledow.forEach{it.setBackgroundColor(current_color.color)}
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    NetworkService.getInstance("http://192.168.37.18").launchRequest(
                        NetworkService.getInstance().malinaAPI::setAllLedPanel,
                        requireContext()
                    )
                }
            }
        }

        for ( x in 0..7) {
            for (y in 0..7) {
                val led_id = resources.getIdentifier("led$x$y","id", requireActivity().packageName)
                val led = view.findViewById<View>(led_id)
                tablica_ledow.add(led)
                led.setOnClickListener {
                    Log.d("SWITCH MODE","${switch_mode.isChecked}")
                    if (switch_mode.isChecked) {
                        led.setBackgroundColor(Color.BLACK)
                    }
                    else {
                        led.setBackgroundColor(current_color.color)
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.Main) {
                            if (switch_mode.isChecked) {
                                NetworkService.getInstance("http://192.168.37.18").launchRequest(
                                    SetSingleLedPanelRequest(x,y,"000000"),
                                    NetworkService.getInstance().malinaAPI::setSingleLedPanel,
                                    requireContext()
                                )
                            }
                            else {
                                NetworkService.getInstance("http://192.168.37.18").launchRequest(
                                    SetSingleLedPanelRequest(x,y,current_color.hexCode.substring(2)),
                                    NetworkService.getInstance().malinaAPI::setSingleLedPanel,
                                    requireContext()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}