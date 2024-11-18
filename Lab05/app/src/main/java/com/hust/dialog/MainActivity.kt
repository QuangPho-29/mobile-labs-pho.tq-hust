package com.hust.dialog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private lateinit var studentAdapter: StudentAdapter
  private val students = mutableListOf<StudentModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Initialize data
    initializeStudents()

    // Setup RecyclerView
    studentAdapter = StudentAdapter(
      students,
      onEditStudent = { position, student -> showEditDialog(position, student) },
      onDeleteStudent = { position -> deleteStudent(position) }
    )

    findViewById<RecyclerView>(R.id.recycler_view_students).run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    // Add new student
    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showAddDialog()
    }
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

  private fun showAddDialog() {
    val dialogView = layoutInflater.inflate(R.layout.dialog_student, null)
    val dialog = AlertDialog.Builder(this)
      .setTitle("Add New Student")
      .setView(dialogView)
      .create()

    dialogView.findViewById<Button>(R.id.btn_save).setOnClickListener {
      val name = dialogView.findViewById<EditText>(R.id.et_student_name).text.toString()
      val mssv = dialogView.findViewById<EditText>(R.id.et_student_id).text.toString()

      if (name.isNotEmpty() && mssv.isNotEmpty()) {
        students.add(0, StudentModel(name, mssv))
        studentAdapter.notifyItemInserted(0)
        Toast.makeText(this, "Added $name to the list!", Toast.LENGTH_SHORT).show()
        dialog.dismiss()
      } else {
        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
      }
    }

    dialog.show()
  }

  private fun showEditDialog(position: Int, student: StudentModel) {
    val dialogView = layoutInflater.inflate(R.layout.dialog_student, null)
    val dialog = AlertDialog.Builder(this)
      .setTitle("Edit Student")
      .setView(dialogView)
      .create()

    val nameEditText = dialogView.findViewById<EditText>(R.id.et_student_name)
    val idEditText = dialogView.findViewById<EditText>(R.id.et_student_id)

    nameEditText.setText(student.studentName)
    idEditText.setText(student.studentId)

    dialogView.findViewById<Button>(R.id.btn_save).setOnClickListener {
      val newName = nameEditText.text.toString()
      val newId = idEditText.text.toString()

      if (newName.isNotEmpty() && newId.isNotEmpty()) {
        students[position] = StudentModel(newName, newId)
        studentAdapter.notifyItemChanged(position)
        Toast.makeText(this, "Updated student information!", Toast.LENGTH_SHORT).show()
        dialog.dismiss()
      } else {
        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
      }
    }

    dialog.show()
  }

  private fun deleteStudent(position: Int) {
    val removeStudent = students[position]

    AlertDialog.Builder(this)
      .setTitle("Delete Student")
      .setMessage("Are you sure you want to delete ${removeStudent.studentName}?")
      .setPositiveButton("Yes") { _, _ ->
        students.removeAt(position)
        studentAdapter.notifyItemRemoved(position)

        Snackbar.make(findViewById(R.id.main), "Deleted ${removeStudent.studentName}", Snackbar.LENGTH_LONG)
          .setAction("Undo") {
            students.add(position, removeStudent)
            studentAdapter.notifyItemInserted(position)
          }
          .show()
      }
      .setNegativeButton("No", null)
      .show()
  }
}