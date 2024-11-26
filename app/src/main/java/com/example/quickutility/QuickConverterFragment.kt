package com.example.quickutility

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.widget.addTextChangedListener

class QuickConverterFragment : Fragment() {

    private lateinit var edtValue: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerFromUnit: Spinner
    private lateinit var spinnerToUnit: Spinner
    private lateinit var tvResult: TextView
    private lateinit var btnHome4: Button

    private var category: String = "Length" // Default category
    private var fromUnit: String = "Meter" // Default from unit
    private var toUnit: String = "Kilometer" // Default to unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quick_convetor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        edtValue = view.findViewById(R.id.edtValue)
        spinnerCategory = view.findViewById(R.id.spinnerCategory)
        spinnerFromUnit = view.findViewById(R.id.spinnerFromUnit)
        spinnerToUnit = view.findViewById(R.id.spinnerToUnit)
        tvResult = view.findViewById(R.id.tvResult)
        btnHome4 = view.findViewById(R.id.btnHome4)

        // Handle the Home button click
        btnHome4.setOnClickListener {
            Toast.makeText(activity, "Opening Home Page...", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        // Populate category spinner with options
        val categories = arrayOf("Length", "Weight", "Temperature")
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
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

        // Set up the "from unit" spinner
        val unitAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, fromUnits)
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFromUnit.adapter = unitAdapter

        // Set up the "to unit" spinner
        val unitToAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, toUnits)
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
                    fromUnit == "Millimeter" && toUnit == "Meter" -> value / 1000
                    fromUnit == "Meter" && toUnit == "Millimeter" -> value * 1000
                    fromUnit == "Kilometer" && toUnit == "Centimeter" -> value * 100000
                    fromUnit == "Centimeter" && toUnit == "Kilometer" -> value / 100000
                    fromUnit == "Kilometer" && toUnit == "Millimeter" -> value * 1000000
                    fromUnit == "Millimeter" && toUnit == "Kilometer" -> value / 1000000
                    else -> value // Other conversions can be added here
                }
            }
            "Weight" -> {
                result = when {
                    fromUnit == "Kilogram" && toUnit == "Pound" -> value * 2.20462
                    fromUnit == "Pound" && toUnit == "Kilogram" -> value / 2.20462
                    fromUnit == "Kilogram" && toUnit == "Gram" -> value * 1000
                    fromUnit == "Gram" && toUnit == "Kilogram" -> value / 1000
                    fromUnit == "Pound" && toUnit == "Gram" -> value * 453.592
                    fromUnit == "Gram" && toUnit == "Pound" -> value / 453.592
                    else -> value // Other conversions can be added here
                }
            }
            "Temperature" -> {
                result = when {
                    fromUnit == "Celsius" && toUnit == "Fahrenheit" -> (value * 9/5) + 32
                    fromUnit == "Fahrenheit" && toUnit == "Celsius" -> (value - 32) * 5/9
                    fromUnit == "Celsius" && toUnit == "Kelvin" -> value + 273.15
                    fromUnit == "Kelvin" && toUnit == "Celsius" -> value - 273.15
                    fromUnit == "Fahrenheit" && toUnit == "Kelvin" -> (value - 32) * 5/9 + 273.15
                    fromUnit == "Kelvin" && toUnit == "Fahrenheit" -> (value - 273.15) * 9/5 + 32
                    else -> value // Other conversions can be added here
                }
            }
        }

        tvResult.text = "Result: $result"
    }
}
