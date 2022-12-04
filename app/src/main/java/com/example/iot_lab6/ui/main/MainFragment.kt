package com.example.iot_lab6.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.iot_lab6.R
import com.example.iot_lab6.network.NetworkService

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentMainToDBMain = view.findViewById<Button>(R.id.MainFragmentToLedPanelFragment)
        fragmentMainToDBMain.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_ledPanelFragment)
        }

        val fragmentMainToPlotFragmentBtn = view.findViewById<Button>(R.id.mainFragmentToPlotPanelFragment)
        fragmentMainToPlotFragmentBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_plotPanelFragment)
        }


        val setIPBtn = view.findViewById<Button>(R.id.setIP)
        val setIPText = view.findViewById<TextView>(R.id.setIPText)

        setIPBtn.setOnClickListener {
            NetworkService.getInstance().changeBaseUrl(setIPText.text.toString())
        }
    }

}