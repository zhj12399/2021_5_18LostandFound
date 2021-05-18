package com.zhj.lostandfound.homepage

import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.zhj.lostandfound.R

class MyViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val tvtitle = itemView.findViewById<TextView>(R.id.tvTitle)
    private val imageView = itemView.findViewById<ImageView>(R.id.imageView)

    fun bind(dataItem: MyDataItem) {
        tvtitle.text = dataItem.title
        if (dataItem.Isphoto) {
            imageView.setImageURI(Uri.parse(dataItem.photouri))
            imageView.isVisible = true
        } else {
            imageView.isVisible = false
            imageView.layoutParams.height = 0
            imageView.layoutParams.width = 0
        }

        if (dataItem.isSelected) {
            itemView.setBackgroundColor(Color.CYAN)
        } else {
            itemView.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}