package com.hust.gmail_clone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Thiết lập RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Danh sách email mẫu
        val emailList = listOf(
            Email("Nguyễn Văn A", "Tôi lên là Nguyễn Văn A, tôi bao nhiêu tuổi?", "08:45 AM"),
            Email("Trần Thị B", "Tôi cần gì ở bạn?", "09:30 AM"),
            Email("Lê Văn C", "Hãy giúp tôi với công việc này.", "11:00 AM"),
            Email("Phạm Thị D", "Bạn có thể gửi tài liệu cho tôi không?", "01:15 PM"),
            Email("Hoàng Văn E", "Cuộc họp đã được lên lịch vào ngày mai.", "02:45 PM"),
            Email("Nguyễn Văn A", "Tôi lên là Nguyễn Văn A, tôi bao nhiêu tuổi?", "08:45 AM"),
            Email("Trần Thị B", "Tôi cần gì ở bạn?", "09:30 AM"),
            Email("Lê Văn C", "Hãy giúp tôi với công việc này.", "11:00 AM"),
            Email("Phạm Thị D", "Bạn có thể gửi tài liệu cho tôi không?", "01:15 PM"),
            Email("Hoàng Văn E", "Cuộc họp đã được lên lịch vào ngày mai.", "02:45 PM")
        )

        // Thiết lập adapter cho RecyclerView
        val adapter = EmailAdapter(emailList)
        recyclerView.adapter = adapter
    }
}