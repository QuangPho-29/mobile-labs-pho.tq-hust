package com.hust.dialog.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.BaseAdapter
import com.hust.dialog.R
import com.hust.dialog.StudentModel

class StudentAdapter(
  private val context: Context,
  private val students: MutableList<StudentModel>,
  private val selectedStudents: MutableSet<StudentModel>,
  private val onEditStudent: (position: Int) -> Unit,
  private val onDeleteStudent: (position: Int) -> Unit
) : BaseAdapter() {

  override fun getCount(): Int = students.size

  override fun getItem(position: Int): Any = students[position]

  override fun getItemId(position: Int): Long = position.toLong()

  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    val view: View
    val viewHolder: ViewHolder

    if (convertView == null) {
      view = LayoutInflater.from(context).inflate(R.layout.layout_student_item, parent, false)
      viewHolder = ViewHolder(
        view.findViewById(R.id.checkbox_select),
        view.findViewById(R.id.text_student_name),
        view.findViewById(R.id.text_student_id),
        view.findViewById(R.id.image_edit),
        view.findViewById(R.id.image_remove)
      )
      view.tag = viewHolder
    } else {
      view = convertView
      viewHolder = view.tag as ViewHolder
    }

    val student = students[position]
    viewHolder.textStudentName.text = student.studentName
    viewHolder.textStudentId.text = student.studentId

    // Handle checkbox
    viewHolder.checkboxSelect.isChecked = selectedStudents.contains(student)
    viewHolder.checkboxSelect.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        selectedStudents.add(student)
      } else {
        selectedStudents.remove(student)
      }
    }

    // Handle edit
    viewHolder.imageEdit.setOnClickListener {
      onEditStudent(position)
    }

    // Handle delete
    viewHolder.imageRemove.setOnClickListener {
      onDeleteStudent(position)
    }

    return view
  }

  private data class ViewHolder(
    val checkboxSelect: CheckBox,
    val textStudentName: TextView,
    val textStudentId: TextView,
    val imageEdit: ImageView,
    val imageRemove: ImageView
  )
}
