package com.zhj.lostandfound.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zhj.lostandfound.R

class mypageAdapter(
    private val itemList: List<MyDataItemTwo>,
    private val listener: MyClickListener?   //外部数据监听器
) : RecyclerView.Adapter<mypagerViewHolder>() {
    //当前选中的行索引
    var selectedIndex = -1

    //外部事件响应监听器接口
    interface MyClickListener {
        fun onClickItem(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : mypagerViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        val holder =mypagerViewHolder(root)
        root.setOnClickListener {
            listener?.onClickItem(holder.adapterPosition)
            setSelected(holder.adapterPosition)
        }
        return holder
    }

    //确定显示行数
    override fun getItemCount(): Int {
        return itemList.size
    }

    //显示一个的内容
    override fun onBindViewHolder(
        holder: mypagerViewHolder,
        position: Int
    ) {
        holder.bind(itemList[position])
    }

    //供外界调用的“选中行”方法
    fun setSelected(position: Int) {
        if (position != selectedIndex && selectedIndex != -1) {
            itemList[selectedIndex].isSelected = false
            notifyItemChanged(selectedIndex)
        }
        itemList[position].isSelected = true
        notifyItemChanged(position)
        selectedIndex = position
    }
}