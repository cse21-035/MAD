package com.example.quickutility

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

class Calculator : AppCompatActivity() {

    private lateinit var btnHome3: Button
    private lateinit var btnClear: Button
    private lateinit var btnProduct: Button
    private lateinit var btnDivision: Button
    private lateinit var btnAdd: Button
    private lateinit var btnSubtraction: Button
    private lateinit var btnEquals: Button
    private lateinit var btn0: Button
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn7: Button
    private lateinit var btn8: Button
    private lateinit var btn9: Button
    private lateinit var tvOutput: TextView

    // Variables to hold input and operators
    private var currentInput: String = ""
    private var operator: String? = null
    private var operand1: Double? = null
    private var operand2: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator)

        // Initialize views
        btnHome3 = findViewById(R.id.btnHome3)
        btnClear = findViewById(R.id.btnClear)
        btnProduct = findViewById(R.id.btnProduct)
        btnDivision = findViewById(R.id.btnDivision)
        btnAdd = findViewById(R.id.btnAdd)
        btnSubtraction = findViewById(R.id.btnSubtraction)
        btnEquals = findViewById(R.id.btnEquals)
        btn0 = findViewById(R.id.btn0)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
        tvOutput = findViewById(R.id.tvOutput)

        // Set up Home button to navigate back to MainActivity
        btnHome3.setOnClickListener {
            Toast.makeText(this, "Opening Home Page...", Toast.LENGTH_SHORT).show();
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Set up number buttons
        btn0.setOnClickListener { appendNumber("0") }
        btn1.setOnClickListener { appendNumber("1") }
        btn2.setOnClickListener { appendNumber("2") }
        btn3.setOnClickListener { appendNumber("3") }
        btn4.setOnClickListener { appendNumber("4") }
        btn5.setOnClickListener { appendNumber("5") }
        btn6.setOnClickListener { appendNumber("6") }
        btn7.setOnClickListener { appendNumber("7") }
        btn8.setOnClickListener { appendNumber("8") }
        btn9.setOnClickListener { appendNumber("9") }

        // Set up operator buttons
        btnAdd.setOnClickListener { setOperator("+") }
        btnSubtraction.setOnClickListener { setOperator("-") }
        btnProduct.setOnClickListener { setOperator("*") }
        btnDivision.setOnClickListener { setOperator("/") }

        // Set up equals button
        btnEquals.setOnClickListener { calculateResult() }

        // Set up clear button
        btnClear.setOnClickListener { clear() }
    }

    // Appends the number clicked to the current input
    private fun appendNumber(number: String) {
        currentInput += number
        tvOutput.text = currentInput
    }

    // Sets the operator and stores the first operand
    private fun setOperator(op: String) {
        if (currentInput.isNotEmpty()) {
            operand1 = currentInput.toDouble()
            currentInput = ""
            operator = op
        }
    }

    // Performs the calculation based on the operator and operands
    private fun calculateResult() {
        if (currentInput.isNotEmpty() && operand1 != null && operator != null) {
            try {
                operand2 = currentInput.toDouble()
                val result = when (operator) {
                    "+" -> operand1!! + operand2!!
                    "-" -> operand1!! - operand2!!
                    "*" -> operand1!! * operand2!!
                    "/" -> {
                        if (operand2 == 0.0) {
                            tvOutput.text = "Error"
                            return
                        }
                        operand1!! / operand2!!
                    }
                    else -> 0.0
                }
                // Display the result
                tvOutput.text = result.toString()
                // Reset for the next calculation
                operand1 = result
                currentInput = ""
                operator = null
            } catch (e: Exception) {
                tvOutput.text = "Error"
            }
        }
    }

    // Clears the current input and resets the calculator
    private fun clear() {
        currentInput = ""
        operator = null
        operand1 = null
        operand2 = null
        tvOutput.text = "0"
    }
}
