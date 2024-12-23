package com.example.filemanager

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fileAdapter: FileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val rootDir = Environment.getExternalStorageDirectory()
        if (rootDir.exists()) {
            displayFiles(rootDir)
        } else {
            Toast.makeText(this, "Không thể truy cập bộ nhớ ngoài", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayFiles(directory: File) {
        val files = directory.listFiles()?.toList() ?: emptyList()
        fileAdapter = FileAdapter(files) { file ->
            if (file.isDirectory) {
                displayFiles(file) // Hiển thị nội dung thư mục
            } else {
                // Mở FileContentActivity để hiển thị nội dung tệp
                val intent = Intent(this, FileContentActivity::class.java)
                intent.putExtra("filePath", file.absolutePath)
                startActivity(intent)
            }
        }
        recyclerView.adapter = fileAdapter
    }
}
