package com.zhj.lostandfound.centers

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zhj.lostandfound.R
import com.zhj.lostandfound.ShowThingsActivity
import com.zhj.lostandfound.homepage.ListAdapter
import com.zhj.lostandfound.homepage.ListAdapter.MyClickListener
import com.zhj.lostandfound.homepage.MyDataItem


class FragmentCenter1 : Fragment(), MyClickListener {
    var dataList = emptyList<MyDataItem>()
    var currentIndex: Int = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_center1, container, false)

        val recyclerview = root.findViewById<RecyclerView>(R.id.recyclerView)
        val emptyoption = root.findViewById<TextView>(R.id.EmptyOption)
        val buttonall = root.findViewById<Button>(R.id.buttonfabu)
        val buttonshide = root.findViewById<Button>(R.id.buttonliuyan)
        val buttonyishi = root.findViewById<Button>(R.id.buttonyishi)

        currentIndex = -1
        dataList = createDataList()
        emptyoption.isVisible = dataList.isEmpty()
        recyclerview.layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
        recyclerview.adapter = ListAdapter(dataList, this)
        buttonall.setBackgroundColor(Color.MAGENTA)

        buttonall.setOnClickListener {
            dataList = createDataList()
            emptyoption.isVisible = dataList.isEmpty()
            recyclerview.layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
            recyclerview.adapter = ListAdapter(dataList, this)
            buttonall.setBackgroundColor(Color.MAGENTA)
            buttonshide.setBackgroundColor(0xFF6200EE.toInt())
            buttonyishi.setBackgroundColor(0xFF6200EE.toInt())
        }
        buttonshide.setOnClickListener {
            dataList = createShideDataList()
            emptyoption.isVisible = dataList.isEmpty()
            recyclerview.layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
            recyclerview.adapter = ListAdapter(dataList, this)
            buttonshide.setBackgroundColor(Color.MAGENTA)
            buttonall.setBackgroundColor(0xFF6200EE.toInt())
            buttonyishi.setBackgroundColor(0xFF6200EE.toInt())
        }
        buttonyishi.setOnClickListener {
            dataList = createYishiDataList()
            emptyoption.isVisible = dataList.isEmpty()
            recyclerview.layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
            recyclerview.adapter = ListAdapter(dataList, this)
            buttonyishi.setBackgroundColor(Color.MAGENTA)
            buttonshide.setBackgroundColor(0xFF6200EE.toInt())
            buttonall.setBackgroundColor(0xFF6200EE.toInt())
        }
        return root
    }

    fun createYishiDataList(): List<MyDataItem> {
        val db = activity?.openOrCreateDatabase("things.db", AppCompatActivity.MODE_PRIVATE, null)
        val sql = "select * from myTable "
        val cursor = db?.rawQuery(sql, null)
        var itemListItem = mutableListOf<MyDataItem>()
        if (cursor != null && cursor.columnCount > 0) {
            while (cursor.moveToNext()) {
                val logged = cursor.getString(0)
                var whatthing = cursor.getInt(2) == 1
                val title = cursor.getString(3)
                val isphoto = cursor.getInt(5) == 1
                if (!whatthing) {
                    val photouri = cursor.getString(6)
                    var myDataItem =
                        MyDataItem(logged, title, whatthing, isphoto, photouri, false)
                    itemListItem.add(myDataItem)
                }
            }
        }
        db?.close()
        return itemListItem
    }

    fun createShideDataList(): List<MyDataItem> {
        val db = activity?.openOrCreateDatabase("things.db", AppCompatActivity.MODE_PRIVATE, null)
        val sql = "select * from myTable "
        val cursor = db?.rawQuery(sql, null)
        var itemListItem = mutableListOf<MyDataItem>()
        if (cursor != null && cursor.columnCount > 0) {
            while (cursor.moveToNext()) {
                val logged = cursor.getString(0)
                var whatthing = cursor.getInt(2) == 1
                val title = cursor.getString(3)
                val isphoto = cursor.getInt(5) == 1
                if (whatthing) {
                    val photouri = cursor.getString(6)
                    var myDataItem =
                        MyDataItem(logged, title, whatthing, isphoto, photouri, false)
                    itemListItem.add(myDataItem)
                }
            }
        }
        db?.close()
        return itemListItem
    }

    fun createDataList(): List<MyDataItem> {
        val db = activity?.openOrCreateDatabase("things.db", AppCompatActivity.MODE_PRIVATE, null)
        val sql = "select * from myTable "
        val cursor = db?.rawQuery(sql, null)
        var itemListItem = mutableListOf<MyDataItem>()
        if (cursor != null && cursor.columnCount > 0) {
            while (cursor.moveToNext()) {
                val logged = cursor.getString(0)
                var whatthing = cursor.getInt(2) == 1
                val title = cursor.getString(3)
                val isphoto = cursor.getInt(5) == 1
                val photouri = cursor.getString(6)
                var myDataItem = MyDataItem(logged, title, whatthing, isphoto, photouri, false)
                itemListItem.add(myDataItem)
            }
        }
        db?.close()
        return itemListItem
    }

    override fun onClickItem(position: Int) {
        currentIndex = position
        val intent = Intent(activity, ShowThingsActivity::class.java)
        val bundle = Bundle()
        bundle.putString("thinglogged", dataList[currentIndex].logged)
        intent.putExtras(bundle)
        activity?.startActivity(intent)
    }
}

