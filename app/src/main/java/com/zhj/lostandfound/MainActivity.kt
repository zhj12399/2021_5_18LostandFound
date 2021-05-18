package com.zhj.lostandfound

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zhj.lostandfound.centers.CenterActivity
import com.zhj.lostandfound.loginandregister.LoginActivity
import com.zhj.lostandfound.loginandregister.MyPageAdpater
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (CheckFirstLogin()) {
            FirstStartApp()
            pager.adapter = MyPageAdpater(this)
        } else {
            if (CheckIsLogin()) {//有登录的账号
                val intent = Intent(this, CenterActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {//跳转到登录界面
                val intent = Intent(this, LoginActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                this.startActivity(intent)
            }
        }
    }

    /**
     * 第一次启动App
     * 创建一个账号密码的数据库
     */
    fun FirstStartApp() {
        val db = this.openOrCreateDatabase("account.db", MODE_PRIVATE, null)
        val db2 = this.openOrCreateDatabase("things.db", MODE_PRIVATE, null)
        db.execSQL("DROP TABLE IF EXISTS myTable")
        db2.execSQL("DROP TABLE IF EXISTS myTable")
        db.execSQL("CREATE TABLE IF NOT EXISTS myTable(id TEXT,password TEXT) ")
        db2.execSQL(
            "CREATE TABLE IF NOT EXISTS myTable(log TEXT,id TEXT,whatthing INTEGER,title TEXT," +
                    "detail TEXT,isphoto INTEGER,photouri TEXT)"
        )
        db2.execSQL("DROP TABLE IF EXISTS myTableTwo")
        db2.execSQL(
            "CREATE TABLE IF NOT EXISTS myTableTwo(log TEXT,num INTEGER,idone TEXT," +
                    "detailone TEXT,idtwo TEXT,detailtwo TEXT,idthr TEXT,detailthr TEXT)"
        )
        db.close()
    }

    /**
     * 判断是否为第一次启动App
     * True 代表第一次打开App
     */
    fun CheckFirstLogin(): Boolean {
        val PREF_FILE_NAME = "Mydata"
        val First_Login = "first_login"

        val pref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val state = pref.getBoolean(First_Login, true)
        if (state == true) {//第一次打开App
            val editor = pref.edit()
            editor.putBoolean(First_Login, false)
            editor.apply()
            return true
        } else {
            return false
        }
    }

    /**
     * 判断是否有登录的账号
     * True 代表此时已经登陆了账号
     */
    fun CheckIsLogin(): Boolean {
        val PREF_FILE_NAME = "Mydata"
        val IsLogin = "islogin"

        val pref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val state = pref.getBoolean(IsLogin, false)
        return state
    }
}