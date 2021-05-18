package com.zhj.lostandfound

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zhj.lostandfound.centers.CenterActivity
import kotlinx.android.synthetic.main.activity_show_things.*

class ShowThingsActivity : AppCompatActivity() {
    var logged: String? = ""
    var detailnum: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_things)

        val PREF_FILE_NAME = "Mydata"
        val LoginID = "loginid"
        val pref = this.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val userid = pref.getString(LoginID, "")

        logged = intent.getStringExtra("thinglogged")
        val db = this.openOrCreateDatabase("things.db", AppCompatActivity.MODE_PRIVATE, null)
        val sql = "select * from myTable where log = ?"
        val cursor = db.rawQuery(sql, arrayOf(logged))
        cursor.moveToFirst()
        val logged = cursor.getString(0)
        val peopleid = cursor.getString(1)
        val whatthing = cursor.getInt(2) == 1
        val title = cursor.getString(3)
        val detail = cursor.getString(4)
        val isphoto = cursor.getInt(5) == 1
        var photouri = cursor.getString(6)

        if (whatthing) {
            tvTitle.text = "是不是你丢的呀？"
            tvdetali1.text = peopleid + " 捡到了 " + title
        } else {
            tvTitle.text = "你有没有捡到呀？"
            tvdetali1.text = peopleid + " 丢失了 " + title
        }

        tvdetali2.text = " 详情： " + detail
        if (isphoto) {
            imageView.setImageURI(Uri.parse(photouri))
            nopic.isVisible = false
        } else {
            imageView.isVisible = false
            nopic.isVisible = true
        }

        //只有自己才能删除自己发布的
        buttondelete.isVisible = userid == peopleid
        buttondelete.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("确定删除这条信息码？")
                .setMessage("删除后将不可恢复")
                .setNeutralButton("取消") { dialog, which ->

                }
                .setPositiveButton("是") { dialog, which ->
                    db.execSQL("delete from mytable where log = ?", arrayOf(logged))
                    db.execSQL("delete from mytableTwo where log = ?", arrayOf(logged))
                    db.close()
                    finish()
                    val intent = Intent(this, CenterActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .show()
        }
        buttonback.setOnClickListener {
            finish()
        }

        buttonadd.setOnClickListener {
            if (detailnum==3){
                MaterialAlertDialogBuilder(this)
                    .setTitle("注意")
                    .setMessage(
                        "留言区已满\n无法在增添留言"
                    )
                    .setPositiveButton("确定") { dialog, which ->
                    }
                    .show()
            }
            else{
                val intent = Intent(this, AddTalkActivity::class.java)
                val bundle = Bundle()
                bundle.putString("thinglogged", logged)
                intent.putExtras(bundle)
                this.startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val db2 = this.openOrCreateDatabase("things.db", AppCompatActivity.MODE_PRIVATE, null)
        val sql2 = "select * from myTableTwo where log = ?"
        val cursor2 = db2.rawQuery(sql2, arrayOf(logged))
        cursor2.moveToFirst()
        detailnum = cursor2.getInt(1)
        if (detailnum == 0) {
            details3.text = "暂时无留言啊"
        } else {
            var detailtext: String = ""
            for (i in 1..detailnum) {
                detailtext += cursor2.getString(detailnum * 2) + " 留言： " + cursor2.getString(
                    detailnum * 2 + 1
                ) + "\n"
            }
            details3.text = detailtext
        }

    }
}