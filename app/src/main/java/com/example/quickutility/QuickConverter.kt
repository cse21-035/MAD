package com.example.quickutility

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class QuickConverter : AppCompatActivity() {

    private lateinit var edtValue: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerFromUnit: Spinner
    private lateinit var spinnerToUnit: Spinner
    private lateinit var tvResult: TextView
    private lateinit var btnHome4 : Button

    private var category: String = "Length" // Default category
    private var fromUnit: String = "Meter" // Default from unit
    private var toUnit: String = "Kilometer" // Default to unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quick_convetor)
        btnHome4 = findViewById(R.id.btnHome4)

        btnHome4.setOnClickListener {
            Toast.makeText(this, "Opening Home Page...", Toast.LENGTH_SHORT).show();
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Initialize views
        edtValue = findViewById(R.id.edtValue)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit)
        spinnerToUnit = findViewById(R.id.spinnerToUnit)
        tvResult = findViewById(R.id.tvResult)

        // Populate category spinner with options
        val categories = arrayOf("Length", "Weight", "Temperature")
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = categoryAdapter

        // Set spinner listeners
        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                category = categories[position]
                updateUnitsForCategory(category)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        // Set up listeners for the value input field
        edtValue.addTextChangedListener {
            convert()
        }

        // Initial unit setup based on default category (Length)
        updateUnitsForCategory(category)
    }

    private fun updateUnitsForCategory(category: String) {
        val fromUnits: Array<String>
        val toUnits: Array<String>

        when (category) {
            "Length" -> {
                fromUnits = arrayOf("Meter", "Kilometer", "Centimeter", "Millimeter")
                toUnits = arrayOf("Meter", "Kilometer", "Centimeter", "Millimeter")
            }
            "Weight" -> {
                fromUnits = arrayOf("Kilogram", "Gram", "Pound")
                toUnits = arrayOf("Kilogram", "Gram", "Pound")
            }
            "Temperature" -> {
                fromUnits = arrayOf("Celsius", "Fahrenheit", "Kelvin")
                toUnits = arrayOf("Celsius", "Fahrenheit", "Kelvin")
            }
            else -> {
                fromUnits = arrayOf()
                toUnits = arrayOf()
            }
        }

        val unitAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, fromUnits)
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFromUnit.adapter = unitAdapter

        val unitToAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, toUnits)
        unitToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerToUnit.adapter = unitToAdapter

        // Set listeners for unit spinners
        spinnerFromUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                fromUnit = fromUnits[position]
                convert()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        spinnerToUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                toUnit = toUnits[position]
                convert()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }
    }

    private fun convert() {
        val value = edtValue.text.toString().toDoubleOrNull()

        if (value == null) {
            tvResult.text = "Invalid input"
            return
        }

        var result: Double? = null

        // Conversion logic for each category
        when (category) {
            "Length" -> {
                result = when {
                    fromUnit == "Meter" && toUnit == "Kilometer" -> value / 1000
                    fromUnit == "Kilometer" && toUnit == "Meter" -> value * 1000
                    fromUnit == "Centimeter" && toUnit == "Meter" -> value / 100
                    fromUnit == "Meter" && toUnit == "Centimeter" -> value * 100
                    else -> value // Other conversions can be added here
                }
            }
            "Weight" -> {
                result = when {
                    fromUnit == "Kilogram" && toUnit == "Pound" -> value * 2.20462
                    fromUnit == "Pound" && toUnit == "Kilogram" -> value / 2.20462
                    else -> value // Other conversions can be added here
                }
            }
            "Temperature" -> {
                result = when {
                    fromUnit == "Celsius" && toUnit == "Fahrenheit" -> (value * 9/5) + 32
                    fromUnit == "Fahrenheit" && toUnit == "Celsius" -> (value - 32) * 5/9
                    else -> value // Other conversions can be added here
                }
            }
        }

        tvResult.text = "Result: $result"
    }
}
