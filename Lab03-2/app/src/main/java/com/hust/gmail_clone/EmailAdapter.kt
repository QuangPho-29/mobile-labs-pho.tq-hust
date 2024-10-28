package com.hust.gmail_clone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmailAdapter(private val emailList: List<Email>) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val emailSender: TextView = itemView.findViewById(R.id.emailSender)
        val emailSummary: TextView = itemView.findViewById(R.id.emailSummary)
        val emailTime: TextView = itemView.findViewById(R.id.emailTime)
        val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        val starIcon: ImageView = itemView.findViewById(R.id.starIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val currentItem = emailList[position]
        holder.emailSender.text = currentItem.sender
        holder.emailSummary.text = currentItem.summary
        holder.emailTime.text = currentItem.time
        // Sử dụng avatar mặc định và biểu tượng ngôi sao nếu cần
    }

    override fun getItemCount() = emailList.size
}