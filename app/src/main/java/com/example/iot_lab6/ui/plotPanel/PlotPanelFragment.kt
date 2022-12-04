package com.example.iot_lab6.ui.plotPanel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYPlot
import com.androidplot.xy.XYSeries
import com.example.iot_lab6.R
import com.example.iot_lab6.network.NetworkService
import kotlinx.coroutines.*


class PlotPanelFragment : Fragment() {


    lateinit var updateButton: Button
    lateinit var setCyclicText: EditText
    lateinit var plot: XYPlot
    var measurementOne = mutableListOf<Double>(2.0,1.0,3.0,7.0,0.0,0.0,0.0,0.0,0.0,0.0)

    lateinit var myJob: Job
    var cyclicInMs = 100L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View =  inflater.inflate(R.layout.fragment_plot_panel, container, false)
        // Inflate the layout for this fragment
        updateButton = view.findViewById(R.id.cyclicUpdateButton)
        setCyclicText = view.findViewById(R.id.setCyclicText)
        plot = view.findViewById(R.id.plot)


        myJob = startRepeatingJob(cyclicInMs)
        updateButton.setOnClickListener {

            myJob.cancel()
            cyclicInMs = setCyclicText.text.toString().toLong()
            myJob = startRepeatingJob(cyclicInMs)
        }
        return view
    }

    private fun startRepeatingJob(timeInterval: Long): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (NonCancellable.isActive) {
                // add your task here
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        val data = NetworkService.getInstance().launchRequest(
                            NetworkService.getInstance().malinaAPI::getWykresyData,
                            requireContext()
                        )
                        if (data != null) {
                            for (element in data!!) {
                                if(element.name.equals("random"))
                                        measurementOne[0] = element.value
                            }
                        }
                    }
                }

                val series1: XYSeries = SimpleXYSeries(
                    measurementOne, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1"
                )
                val series1Format =
                    LineAndPointFormatter(activity, R.xml.line_point_formatter_with_labels)
                // add a new series' to the xyplot:
                plot.addSeries(series1, series1Format);
                delay(timeInterval)
            }
        }
    }


}