package com.zhj.lostandfound

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_add_talk.*

class AddTalkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_talk)

        val logged = intent.getStringExtra("thinglogged")
        val PREF_FILE_NAME = "Mydata"
        val LoginID = "loginid"
        val pref = this.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val userid = pref.getString(LoginID, "")
        tvTitle.text = userid + " 请输入您的留言"

        buttonback.setOnClickListener {
            finish()
        }
        buttonadd.setOnClickListener {
            val db = this.openOrCreateDatabase("things.db", AppCompatActivity.MODE_PRIVATE, null)
            val sql = "select * from myTableTwo where log = ?"
            val cursor = db.rawQuery(sql, arrayOf(logged))
            cursor.moveToFirst()
            var detailnum = cursor.getInt(1)

            val detailin = EditTextTalk.text.toString()
            MaterialAlertDialogBuilder(this)
                .setTitle("注意")
                .setMessage(
                    "您发表的留言将不可删除并被永久保留"
                )
                .setPositiveButton("确定") { dialog, which ->
                    if (detailnum == 0) {
                        val sql2 =
                            "update myTableTwo set num = ? , idone = ? , detailone = ? where log = ?"
                        db.execSQL(sql2, arrayOf(1, userid, detailin, logged))

                    } else if (detailnum == 1) {
                        val sql2 =
                            "update myTableTwo set num = ? , idtwo = ? , detailtwo = ? where log = ?"
                        db.execSQL(sql2, arrayOf(2, userid, detailin, logged))
                    } else if (detailnum == 2) {
                        val sql2 =
                            "update myTableTwo set num = ? , idthr = ? , detailthr = ? where log = ?"
                        db.execSQL(sql2, arrayOf(3, userid, detailin, logged))
                    }
                    db.close()
                    Toast.makeText(this, "增添留言成功", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .show()

        }
    }
}