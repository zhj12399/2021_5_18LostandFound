package com.zhj.lostandfound.loginandregister

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zhj.lostandfound.centers.CenterActivity
import com.zhj.lostandfound.R
import kotlinx.android.synthetic.main.activity_login.EditTextPassword
import kotlinx.android.synthetic.main.activity_modify.*
import kotlinx.android.synthetic.main.fragment_center4.ButtonModify

class ModifyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        val PREF_FILE_NAME = "Mydata"
        val LoginID = "loginid"
        val pref = this.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val userid = pref.getString(LoginID, "")
        tvID.text = userid

        val db = this.openOrCreateDatabase("account.db", MODE_PRIVATE, null)
        val sql = "select * from myTable where id = ?"
        val cursor = db.rawQuery(sql, arrayOf(userid))
        cursor.moveToFirst()
        val oldpassword = cursor.getString(1)
        var hint = ""
        for (i in 1..oldpassword.length) {
            hint = hint + "*"
        }
        EditTextPassword.hint = hint
        db.close()
        ButtonModify.setOnClickListener {
            val passwordin = EditTextPassword.text.toString()
            if (passwordin == oldpassword || passwordin.isEmpty()) {//未修改任何信息
                Toast.makeText(this, "未修改任何信息", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CenterActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {//更新一下密码
                val db = this.openOrCreateDatabase("account.db", MODE_PRIVATE, null)
                val sql = "update myTable set password = ? where id = ?"
                db.execSQL(sql, arrayOf(passwordin, userid))
                db.close()
                Toast.makeText(this, "密码更新成功", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CenterActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}