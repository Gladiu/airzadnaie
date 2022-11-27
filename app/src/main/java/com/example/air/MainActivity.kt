package com.example.air

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    lateinit var makeRequestButton: Button
    lateinit var inputText: TextInputEditText
    lateinit var outputText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         makeRequestButton = findViewById(R.id.button)
         inputText = findViewById(R.id.inputText)
         outputText = findViewById(R.id.outputText)

        makeRequestButton.setOnClickListener {
            val volleyQueue = Volley.newRequestQueue(this)
            val url = "http://" + inputText.text

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                {
                    response ->
                    outputText.text = response.get("name").toString()

                },
                {
                    error ->
                    print("Error Occured something wrong with http request")
                }
                )
            volleyQueue.add(jsonObjectRequest)
        }
    }

}