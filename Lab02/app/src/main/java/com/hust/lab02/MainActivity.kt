package com.hust.lab02

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var display: TextView
    private var currentInput = ""
    private var previousInput = ""
    private var operator = ""
    private var expresion = ""
    private var stateReset = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.linear_layout)

        display = findViewById(R.id.display)

        // Number buttons
        val numberButtons = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9
        )

        // Assigning OnClickListener to number buttons
        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener(this)
        }

        // Operator buttons
        val operatorButtons = listOf(
            R.id.btn_add, R.id.btn_subtract, R.id.btn_multiply, R.id.btn_divide
        )

        // Assigning OnClickListener to operator buttons
        operatorButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener(this)
        }

        // Special buttons
        val specialButtons = listOf(
            R.id.btn_ce, R.id.btn_c, R.id.btn_bs, R.id.btn_equals, R.id.btn_dot, R.id.btn_plus_minus
        )

        // Assigning OnClickListener to special buttons
        specialButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            // Handle number button clicks
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9 -> {
                val button = v as Button
                if (stateReset) {
                    currentInput = ""
                    expresion = ""
                    stateReset = false
                }
                currentInput += button.text.toString()
                expresion += button.text.toString()
                updateDisplay()
            }

            // Handle operator button clicks
            R.id.btn_add -> handleOperator("+")
            R.id.btn_subtract -> handleOperator("-")
            R.id.btn_multiply -> handleOperator("*")
            R.id.btn_divide -> handleOperator("/")

            // Handle special button clicks
            R.id.btn_ce -> {
                currentInput = ""
                expresion = ""
                updateDisplay()
            }

            R.id.btn_c -> {
                currentInput = ""
                previousInput = ""
                operator = ""
                expresion = ""
                updateDisplay()
            }

            R.id.btn_bs -> {
                if (currentInput.isNotEmpty()) {
                    currentInput = currentInput.dropLast(1)
                    expresion = expresion.dropLast(1)
                    updateDisplay()
                }
            }

            R.id.btn_equals -> {
                if (previousInput.isNotEmpty() && operator.isNotEmpty()) {
                    val result = calculate(previousInput.toDouble(), currentInput.toDouble(), operator)
                    expresion = result.toString()
                    currentInput = result.toString()
                    previousInput = ""
                    operator = ""
                    stateReset = true
                    updateDisplay()
                }
            }

            R.id.btn_dot -> {
                if (!currentInput.contains(".")) {
                    currentInput += "."
                    expresion += "."
                    updateDisplay()
                }
            }

            R.id.btn_plus_minus -> {
                if (currentInput.isNotEmpty() && currentInput != "0") {
                    currentInput = if (currentInput.startsWith("-")) {
                        currentInput.substring(1)
                        expresion.substring(1)
                    } else {
                        "-$currentInput"
                        "-$expresion"
                    }
                    updateDisplay()
                }
            }
        }
    }

    private fun handleOperator(selectedOperator: String) {
        if (currentInput.isNotEmpty()) {
            if (previousInput.isEmpty()) {
                previousInput = currentInput
                operator = selectedOperator
                currentInput = ""
                expresion += selectedOperator
            } else if (operator.isNotEmpty()) {
                val result = calculate(previousInput.toDouble(), currentInput.toDouble(), operator)
                previousInput = result.toString()
                operator = selectedOperator
                currentInput = ""
                expresion += selectedOperator
            }
            updateDisplay()
        }
    }

    private fun calculate(a: Double, b: Double, operator: String): Double {
        return when (operator) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> a / b
            else -> 0.0
        }
    }

    private fun updateDisplay() {
        display.text = expresion.ifEmpty { "0" }
    }
}
