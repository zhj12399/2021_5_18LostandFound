package com.zhj.lostandfound.loginandregister

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zhj.lostandfound.centers.CenterActivity
import com.zhj.lostandfound.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ButtonLogin.setOnClickListener {
            val db = this.openOrCreateDatabase("account.db", MODE_PRIVATE, null)
            val sql = "select * from myTable where id = ?"

            val idin = EditTextName.text.toString()
            val passwordin = EditTextPassword.text.toString()
            val cursor = db.rawQuery(sql, arrayOf(idin))

            if (cursor.moveToFirst()) {
                val passwordsql = cursor.getString(1)
                if (passwordsql == passwordin) {
                    //登陆成功后先在缓存中存储一下账户信息
                    val PREF_FILE_NAME = "Mydata"
                    val LoginID = "loginid"
                    val IsLogin = "islogin"
                    val pref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                    val editor = pref.edit()
                    editor.putString(LoginID, idin)
                    editor.putBoolean(IsLogin,true)
                    editor.apply()

                    db.close()
                    val intent = Intent(this, CenterActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show()
                    EditTextPassword.setText("")
                }
            } else {
                Toast.makeText(this, "查无此账户", Toast.LENGTH_SHORT).show()
                EditTextName.setText("")
                EditTextPassword.setText("")
            }
        }

        ButtonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            this.startActivity(intent)
        }
    }
}