package com.example.quickutility

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.quickutility.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    private var currentInput: String = "" // To store current input
    private var operator: String? = null // To store current operator
    private var firstOperand: Double? = null // Store first operand for calculations

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using ViewBinding
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assign the TextView from the binding
        val tvOutput: TextView = binding.tvOutput

        // Set up button click listeners
        setUpButtonListeners(tvOutput)
    }

    private fun setUpButtonListeners(tvOutput: TextView) {
        // Number buttons
        binding.btn1.setOnClickListener { appendNumber("1", tvOutput) }
        binding.btn2.setOnClickListener { appendNumber("2", tvOutput) }
        binding.btn3.setOnClickListener { appendNumber("3", tvOutput) }
        binding.btn4.setOnClickListener { appendNumber("4", tvOutput) }
        binding.btn5.setOnClickListener { appendNumber("5", tvOutput) }
        binding.btn6.setOnClickListener { appendNumber("6", tvOutput) }
        binding.btn7.setOnClickListener { appendNumber("7", tvOutput) }
        binding.btn8.setOnClickListener { appendNumber("8", tvOutput) }
        binding.btn9.setOnClickListener { appendNumber("9", tvOutput) }
        binding.btn0.setOnClickListener { appendNumber("0", tvOutput) }

        // Operator buttons
        binding.btnAdd.setOnClickListener { setOperator("+", tvOutput) }
        binding.btnSubtraction.setOnClickListener { setOperator("-", tvOutput) }
        binding.btnProduct.setOnClickListener { setOperator("*", tvOutput) }
        binding.btnDivision.setOnClickListener { setOperator("/", tvOutput) }

        // Equal button
        binding.btnEquals.setOnClickListener { calculateResult(tvOutput) }

        // Clear button
        binding.btnClear.setOnClickListener { clear(tvOutput) }

        // Home button
        binding.btnHome3.setOnClickListener {
            // Navigate to Home (handle as required)
            requireActivity().onBackPressed()
        }
    }

    private fun appendNumber(number: String, tvOutput: TextView) {
        currentInput += number
        tvOutput.text = currentInput
    }

    private fun setOperator(op: String, tvOutput: TextView) {
        if (firstOperand == null && currentInput.isNotEmpty()) {
            firstOperand = currentInput.toDouble()
        }
        operator = op
        currentInput = ""  // Clear current input to start with second operand
    }

    private fun calculateResult(tvOutput: TextView) {
        if (firstOperand != null && operator != null && currentInput.isNotEmpty()) {
            val secondOperand = currentInput.toDouble()
            val result = when (operator) {
                "+" -> firstOperand!! + secondOperand
                "-" -> firstOperand!! - secondOperand
                "*" -> firstOperand!! * secondOperand
                "/" -> if (secondOperand != 0.0) firstOperand!! / secondOperand else "Error"
                else -> 0.0
            }
            tvOutput.text = result.toString()
            // Reset for next operation
            firstOperand = null
            operator = null
            currentInput = result.toString()
        }
    }

    private fun clear(tvOutput: TextView) {
        firstOperand = null
        operator = null
        currentInput = ""
        tvOutput.text = "0"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
