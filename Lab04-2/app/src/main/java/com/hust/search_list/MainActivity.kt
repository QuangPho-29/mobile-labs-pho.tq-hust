package com.hust.search_list

import android.app.Notification
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var search: EditText
    private lateinit var students: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var notification: TextView
    private lateinit var studentList: MutableList<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        search = findViewById(R.id.search)
        students = findViewById(R.id.listStudents)
        notification = findViewById(R.id.notification)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        studentList = mutableListOf(
            Student("Nguyễn Văn A", "20190001"),
            Student("Nguyễn Văn B", "20190002"),
            Student("Nguyễn Văn C", "20190003"),
            Student("Nguyễn Văn D", "20190004"),
            Student("Nguyễn Văn E", "20190005"),
            Student("Nguyễn Văn F", "20190006"),
            Student("Nguyễn Văn G", "20190007"),
            Student("Nguyễn Văn H", "20190008"),
            Student("Nguyễn Văn I", "20190009"),
            Student("Nguyễn Văn K", "20190010")
        )

        // Set up RecyclerView
        studentAdapter = StudentAdapter(studentList)
        students.adapter = studentAdapter
        students.layoutManager = LinearLayoutManager(this)

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                val filteredList = if (query.length > 2) {
                    studentList.filter {
                        it.name.contains(query, ignoreCase = true) ||
                                it.mssv.contains(query, ignoreCase = true)
                    }
                } else {
                    studentList
                }
                notification.text = studentAdapter.updateList(filteredList)
                notification.visibility = View.VISIBLE
            }
        })

    }
}