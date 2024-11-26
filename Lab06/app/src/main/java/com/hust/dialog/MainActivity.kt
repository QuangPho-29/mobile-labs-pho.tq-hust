package com.hust.dialog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.hust.dialog.adapter.StudentAdapter

class MainActivity : AppCompatActivity() {
  private lateinit var studentAdapter: StudentAdapter
  private val students = mutableListOf<StudentModel>()
  private val selectedStudents = mutableSetOf<StudentModel>()

  private var recentlyDeletedStudent: StudentModel? = null
  private var recentlyDeletedPosition: Int = -1

  companion object {
    const val REQUEST_CODE_EDIT = 1002
    const val REQUEST_CODE_ADD = 1001
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Initialize data
    initializeStudents()

    val listView: ListView = findViewById(R.id.list_view_students)
    studentAdapter = StudentAdapter(this, students, selectedStudents,
      onEditStudent = { position ->
        val student = students[position]
        val intent = Intent(this, AddEditActivity::class.java).apply {
          putExtra("STUDENT_NAME", student.studentName)
          putExtra("STUDENT_ID", student.studentId)
          putExtra("POSITION", position)
        }
        startActivityForResult(intent, REQUEST_CODE_EDIT)
      },
      onDeleteStudent = { position ->
        deleteStudent(position)
      }
    )
    listView.adapter = studentAdapter

    findViewById<Button>(R.id.btn_add_student).setOnClickListener {
      val intent = Intent(this, AddEditActivity::class.java)
      startActivityForResult(intent, REQUEST_CODE_ADD)
    }


  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_delete -> {
        deleteSelectedStudents()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Activity.RESULT_OK && data != null) {
      val name = data.getStringExtra("STUDENT_NAME")
      val id = data.getStringExtra("STUDENT_ID")
      val position = data.getIntExtra("POSITION", -1)

      if (!name.isNullOrEmpty() && !id.isNullOrEmpty()) {
        when (requestCode) {
          REQUEST_CODE_ADD -> { // Thêm mới
            students.add(StudentModel(name, id))
            studentAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Student added successfully!", Toast.LENGTH_SHORT).show()
          }
          REQUEST_CODE_EDIT -> { // Chỉnh sửa
            if (position != -1) {
              students[position] = StudentModel(name, id)
              studentAdapter.notifyDataSetChanged()
              Toast.makeText(this, "Student updated successfully!", Toast.LENGTH_SHORT).show()
            } else {
              Toast.makeText(this, "Invalid position for edit!", Toast.LENGTH_SHORT).show()
            }
          }
        }
      }
    }
  }

  private fun deleteStudent(position: Int) {
    recentlyDeletedStudent = students[position]
    recentlyDeletedPosition = position

    students.removeAt(position)
    studentAdapter.notifyDataSetChanged()

    Snackbar.make(findViewById(R.id.list_view_students), "Student removed", Snackbar.LENGTH_LONG)
      .setAction("Undo") {
        students.add(recentlyDeletedPosition, recentlyDeletedStudent!!)
        studentAdapter.notifyDataSetChanged()
      }
      .show()
  }

  private fun deleteSelectedStudents() {
    if (selectedStudents.isEmpty()) {
      Toast.makeText(this, "No students selected", Toast.LENGTH_SHORT).show()
      return
    }

    val removedStudents = ArrayList(selectedStudents)
    students.removeAll(selectedStudents)
    selectedStudents.clear()
    studentAdapter.notifyDataSetChanged()

    Snackbar.make(findViewById(R.id.list_view_students), "${removedStudents.size} students removed", Snackbar.LENGTH_LONG)
      .setAction("Undo") {
        students.addAll(removedStudents)
        studentAdapter.notifyDataSetChanged()
      }
      .show()
  }


  private fun initializeStudents() {
    students.addAll(
      listOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
      )
    )
  }
}