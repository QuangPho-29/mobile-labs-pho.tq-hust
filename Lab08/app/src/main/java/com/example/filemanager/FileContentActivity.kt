package com.example.filemanager

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.File

class FileContentActivity : AppCompatActivity(), OnBackPressedDispatcherOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_content)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Bật nút quay lại trên Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val filePath = intent.getStringExtra("filePath")
        val textView: TextView = findViewById(R.id.fileContent)

        if (filePath != null) {
            val file = File(filePath)
            if (file.exists() && file.extension == "txt") {
                textView.text = file.readText()
            } else {
                textView.text = "Không thể đọc tệp này."
            }
        }

        // Đăng ký OnBackPressedCallback
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()  // Quay lại Activity trước đó
            }
        }

        onBackPressedDispatcher.addCallback(this, callback)
    }

    // Xử lý sự kiện khi nhấn nút Quay lại trên Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
