package com.hust.dialog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        val editName = findViewById<EditText>(R.id.et_student_name)
        val editId = findViewById<EditText>(R.id.et_student_id)
        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnCancel = findViewById<Button>(R.id.btn_cancel)

        // Nhận dữ liệu chỉnh sửa nếu có
        val studentName = intent.getStringExtra("STUDENT_NAME") ?: ""
        val studentId = intent.getStringExtra("STUDENT_ID") ?: ""
        val position = intent.getIntExtra("POSITION", -1)

        // Hiển thị dữ liệu chỉnh sửa trong EditText
        editName.setText(studentName)
        editId.setText(studentId)

        // Lưu thay đổi
        btnSave.setOnClickListener {
            val name = editName.text.toString()
            val id = editId.text.toString()
            if (name.isNotEmpty() && id.isNotEmpty()) {
                val resultIntent = Intent().apply {
                    putExtra("STUDENT_NAME", name)
                    putExtra("STUDENT_ID", id)
                    putExtra("POSITION", position) // Trả lại vị trí cho MainActivity
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                // Hiển thị lỗi nếu người dùng chưa nhập đủ thông tin
                if (name.isEmpty()) editName.error = "Name is required"
                if (id.isEmpty()) editId.error = "ID is required"
            }
        }

        // Hủy bỏ chỉnh sửa
        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}