package com.zhj.lostandfound.loginandregister

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zhj.lostandfound.centers.CenterActivity
import com.zhj.lostandfound.R
import kotlinx.android.synthetic.main.activity_register.ButtonRegister
import kotlinx.android.synthetic.main.activity_register.EditTextName
import kotlinx.android.synthetic.main.activity_register.EditTextPassword

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        ButtonRegister.setOnClickListener {
            val db = this.openOrCreateDatabase("account.db", MODE_PRIVATE, null)
            val sql = "select * from myTable where id = ?"

            val idin = EditTextName.text.toString()
            val passwordin = EditTextPassword.text.toString()
            val cursor = db.rawQuery(sql, arrayOf(idin))

            if (cursor.moveToFirst()) {
                Toast.makeText(this, "账户名已被注册", Toast.LENGTH_SHORT).show()
                EditTextName.setText("")
            } else {
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show()

                //先存储到数据库中
                val sql = "INSERT INTO myTable VALUES(?,?)"
                db.execSQL(sql, arrayOf(idin, passwordin))
                db.close()

                //缓存中也存储下
                val PREF_FILE_NAME = "Mydata"
                val LoginID = "loginid"
                val IsLogin = "islogin"
                val pref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString(LoginID, idin)
                editor.putBoolean(IsLogin, true)
                editor.apply()

                val intent = Intent(this, CenterActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}