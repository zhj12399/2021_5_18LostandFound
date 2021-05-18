package com.zhj.lostandfound.centers

import android.content.Context
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
import com.zhj.lostandfound.loginandregister.MyPageAdpater
import com.zhj.lostandfound.mypage.MyDataItemTwo
import com.zhj.lostandfound.mypage.mypageAdapter

class FragmentCenter3 : Fragment(), mypageAdapter.MyClickListener {
    var dataList = emptyList<MyDataItemTwo>()
    var currentIndex: Int = -1
    var userid: String? = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_center3, container, false)
        val recyclerview = root.findViewById<RecyclerView>(R.id.recyclerView)
        val emptyoption = root.findViewById<TextView>(R.id.EmptyOption)
        val PREF_FILE_NAME = "Mydata"
        val LoginID = "loginid"
        val pref = getActivity()?.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val buttonfabu = root.findViewById<Button>(R.id.buttonfabu)
        val buttonliuyan = root.findViewById<Button>(R.id.buttonliuyan)

        userid = pref?.getString(LoginID, "")
        currentIndex = -1
        dataList = createDataListTalked()
        emptyoption.isVisible = dataList.isEmpty()
        recyclerview.layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
        recyclerview.adapter = mypageAdapter(dataList, this)
        buttonliuyan.setBackgroundColor(Color.MAGENTA)

        buttonfabu.setOnClickListener {
            dataList = createDataListMy()
            emptyoption.isVisible = dataList.isEmpty()
            recyclerview.layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
            recyclerview.adapter = mypageAdapter(dataList, this)
            buttonfabu.setBackgroundColor(Color.MAGENTA)
            buttonliuyan.setBackgroundColor(0xFF6200EE.toInt())
        }
        buttonliuyan.setOnClickListener {
            dataList = createDataListTalked()
            emptyoption.isVisible = dataList.isEmpty()
            recyclerview.layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
            recyclerview.adapter = mypageAdapter(dataList, this)
            buttonliuyan.setBackgroundColor(Color.MAGENTA)
            buttonfabu.setBackgroundColor(0xFF6200EE.toInt())
        }
        return root
    }

    fun createDataListTalked(): List<MyDataItemTwo> {
        val db = activity?.openOrCreateDatabase("things.db", AppCompatActivity.MODE_PRIVATE, null)
        val sql = "select * from myTableTwo where idone = ? or idtwo = ? or idthr = ?"
        val cursor = db?.rawQuery(sql, arrayOf(userid, userid, userid))
        val loggedList = mutableListOf<String>()
        if (cursor != null && cursor.columnCount > 0) {
            while (cursor.moveToNext()) {
                loggedList.add(cursor.getString(0))
            }
        }

        var itemListItem = mutableListOf<MyDataItemTwo>()
        for (logged in loggedList) {
            val sql2 = "select * from myTable where log = ?"
            val cursor2 = db?.rawQuery(sql2, arrayOf(logged))
            cursor2?.moveToFirst()
            val whatthing = cursor2?.getInt(2) == 1
            val title = cursor2?.getString(3).toString()
            val isphoto = cursor2?.getInt(5) == 1
            val photouri = cursor2?.getString(6).toString()
            val myDataItem =
                MyDataItemTwo(logged, title, whatthing, isphoto, photouri, false)
            itemListItem.add(myDataItem)
        }
        db?.close()
        return itemListItem
    }

    fun createDataListMy(): List<MyDataItemTwo> {
        var itemListItem = mutableListOf<MyDataItemTwo>()
        val db = activity?.openOrCreateDatabase("things.db", AppCompatActivity.MODE_PRIVATE, null)
        val sql = "select * from myTable where id = ?"
        var cursor = db?.rawQuery(sql, arrayOf(userid))
        if (cursor != null && cursor.columnCount > 0) {
            while (cursor.moveToNext()) {
                val logged = cursor?.getString(0)
                val whatthing = cursor?.getInt(2) == 1
                val title = cursor?.getString(3).toString()
                val isphoto = cursor?.getInt(5) == 1
                val photouri = cursor?.getString(6).toString()
                val myDataItem =
                    MyDataItemTwo(logged, title, whatthing, isphoto, photouri, false)
                itemListItem.add(myDataItem)
            }
        }
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