package com.hust.input_form

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.floor
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private lateinit var input: EditText
    private lateinit var evenOption: RadioButton
    private lateinit var oddOption: RadioButton
    private lateinit var squareNumberOption: RadioButton
    private lateinit var listView: ListView
    private lateinit var warning: TextView
    private lateinit var showButton: Button

    private var isValid = false
    private var number: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.input)
        evenOption = findViewById(R.id.evenOption)
        oddOption = findViewById(R.id.oddOption)
        squareNumberOption = findViewById(R.id.squareNumberOption)
        listView = findViewById(R.id.listView)
        warning = findViewById(R.id.warning)
        showButton = findViewById(R.id.showButton)

        //Sự kiện ấn Button
        showButton.setOnClickListener(View.OnClickListener {
            if (checkInput()) {
                val selectedOption = when {
                    evenOption.isChecked -> 1
                    oddOption.isChecked -> 2
                    squareNumberOption.isChecked -> 3
                    else -> 0
                }
                val numbers = listNumbers(selectedOption)
                displayNumbers(numbers)
                warning.visibility = View.GONE
            } else {
                warning.visibility = View.VISIBLE
                listView.visibility = View.GONE
            }
        })
    }

    private fun checkInput(): Boolean {
        val inputNumber = input.text.toString().toIntOrNull()
        return if (inputNumber != null && inputNumber > 0) {
            number = inputNumber
            isValid = true
            true
        } else {
            isValid = false
            false
        }
    }

    private fun listNumbers(option: Int): MutableList<Int> {
        val numbers = mutableListOf<Int>()
        when (option) {
            1 -> {
                for (i in 1..number) {
                    if (i % 2 == 0) {
                        numbers.add(i)
                    }
                }
            }
            2 -> {
                for (i in 1..number) {
                    if (i % 2 != 0) {
                        numbers.add(i)
                    }
                }
            }
            3 -> {
                val maxIterator: Int = floor(sqrt(number.toDouble())).toInt()
                for (i in 1..maxIterator) {
                    numbers.add(i*i)
                }
            }
        }
        return numbers
    }

    private fun displayNumbers(numbers: MutableList<Int>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbers)
        listView.adapter = adapter
        listView.visibility = View.VISIBLE
    }
}