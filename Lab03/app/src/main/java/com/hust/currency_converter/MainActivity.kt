package com.hust.currency_converter

import android.text.Editable
import android.text.TextWatcher
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var sourceAmount: EditText
    private lateinit var sourceCurrency: Spinner
    private lateinit var sourceSymbol: TextView
    private lateinit var targetAmount: EditText
    private lateinit var targetCurrency: Spinner
    private lateinit var targetSymbol: TextView

    // Biến để kiểm tra xem EditText nào là nguồn
    private var isSourceSelected = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sourceAmount = findViewById(R.id.sourceAmount)
        sourceSymbol = findViewById(R.id.sourceSymbol)
        sourceCurrency = findViewById(R.id.sourceCurrencySpinner)
        targetAmount = findViewById(R.id.targetAmount)
        targetSymbol = findViewById(R.id.targetSymbol)
        targetCurrency = findViewById(R.id.targetCurrencySpinner)

        // Danh sách tiền tệ mẫu: cặp tên và ký hiệu
        val currencyList = listOf(
            Pair("USD", "$"),
            Pair("EUR", "€"),
            Pair("JPY", "¥"),
            Pair("VND", "₫"),
            Pair("CNY", "¥"),
            Pair("KRW", "₩")
        )

        // Sử dụng ArrayAdapter với layout tùy chỉnh cho mục đã chọn và dropdown
        val adapter = object : ArrayAdapter<Pair<String, String>>(this, R.layout.spinner_item, currencyList) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
                val currencyName = view.findViewById<TextView>(R.id.currencyName)
                val currencySymbol = view.findViewById<TextView>(R.id.currencySymbol)
                currencyName.text = currencyList[position].first
                currencySymbol.text = currencyList[position].second
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_dropdown, parent, false)
                val currencyName = view.findViewById<TextView>(R.id.currencyName)
                val currencySymbol = view.findViewById<TextView>(R.id.currencySymbol)
                currencyName.text = currencyList[position].first
                currencySymbol.text = currencyList[position].second
                return view
            }
        }

        // Gán adapter cho Spinner
        sourceCurrency.adapter = adapter
        targetCurrency.adapter = adapter

        // Đăng ký sự kiện cho EditText và Spinner
        setupListeners()
    }

    private fun setupListeners() {
        // Lắng nghe sự kiện thay đổi nội dung trong sourceAmount
        sourceAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isSourceSelected) {
                    if(s.isNullOrEmpty()) {
                        targetAmount.setText("0.0")
                    } else calculateConversion()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Lắng nghe sự kiện thay đổi nội dung trong targetAmount
        targetAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!isSourceSelected) {
                    if(s.isNullOrEmpty()) {
                        sourceAmount.setText("0.0")
                    } else calculateConversion()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Thiết lập sự kiện focus cho sourceAmount
        sourceAmount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                isSourceSelected = true
                calculateConversion() // Tính toán lại khi người dùng chuyển nguồn
            }
        }

        // Thiết lập sự kiện focus cho targetAmount
        targetAmount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                isSourceSelected = false
                calculateConversion() // Tính toán lại khi người dùng chuyển nguồn
            }
        }

        // Lắng nghe sự kiện thay đổi item trong sourceCurrency Spinner
        sourceCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                calculateConversion()
                sourceSymbol.text = (sourceCurrency.selectedItem as Pair<*, *>).second.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Lắng nghe sự kiện thay đổi item trong targetCurrency Spinner
        targetCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                calculateConversion()
                targetSymbol.text = (targetCurrency.selectedItem as Pair<*, *>).second.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }


    private fun calculateConversion() {
        val amount = if (isSourceSelected) {
            sourceAmount.text.toString().toDoubleOrNull() ?: return
        } else {
            targetAmount.text.toString().toDoubleOrNull() ?: return
        }

        val sourceCurrencyCode = (sourceCurrency.selectedItem as Pair<*, *>).first.toString()
        val targetCurrencyCode = (targetCurrency.selectedItem as Pair<*, *>).first.toString()

        val source = if (isSourceSelected) sourceCurrencyCode else targetCurrencyCode
        val target = if (isSourceSelected) targetCurrencyCode else sourceCurrencyCode

        val result = CurrencyConverter.convert(amount, source, target)
        if (isSourceSelected) {
            targetAmount.setText(result.toString())
        } else {
            sourceAmount.setText(result.toString())
        }
    }
}
