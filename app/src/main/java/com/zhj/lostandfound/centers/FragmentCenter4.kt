package com.zhj.lostandfound.centers

import android.os.Bundle
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zhj.lostandfound.R
import com.zhj.lostandfound.loginandregister.LoginActivity
import com.zhj.lostandfound.loginandregister.ModifyActivity

class FragmentCenter4 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_center4, container, false)
        val PREF_FILE_NAME = "Mydata"
        val LoginID = "loginid"
        val pref = getActivity()?.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val userid = pref?.getString(LoginID, "")
        val textuserid = root.findViewById<TextView>(R.id.TextUserID)
        textuserid.setText("您好，$userid")

        val buttonmodify = root.findViewById<Button>(R.id.ButtonModify)
        buttonmodify.setOnClickListener {
            val intent = Intent(activity, ModifyActivity::class.java)
            activity?.startActivity(intent)
        }

        val buttonlogoff = root.findViewById<Button>(R.id.ButtonLogOff)
        buttonlogoff.setOnClickListener {
            ShowLogOffAlertDialog()
        }

        val buttondelete = root.findViewById<Button>(R.id.ButtonDelete)
        buttondelete.setOnClickListener {
            ShowDeleteAlertDialog()
        }

        val buttonabout = root.findViewById<Button>(R.id.ButtonAbout)
        buttonabout.setOnClickListener {
            ShowAboutAlertDialog()
        }
        return root
    }

    private fun ShowLogOffAlertDialog() {
        MaterialAlertDialogBuilder(activity!!)
            .setTitle("确定注销账户码？")
            .setMessage("退出后您的账号仍会保留\n其它信息也不会被删除")
            .setNeutralButton("取消") { dialog, which ->

            }
            .setPositiveButton("是") { dialog, which ->
                //修改一下缓存数据
                val PREF_FILE_NAME = "Mydata"
                val LoginID = "loginid"
                val IsLogin = "islogin"
                val pref = activity?.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                val editor = pref?.edit()
                editor?.putString(LoginID, "")
                editor?.putBoolean(IsLogin, false)
                editor?.apply()

                Toast.makeText(activity, "成功下线", Toast.LENGTH_SHORT).show()
                val intent = Intent(activity, LoginActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                activity?.startActivity(intent)
            }
            .show()
    }

    /*
    * 未完
    * */
    private fun ShowDeleteAlertDialog() {
        MaterialAlertDialogBuilder(activity!!)
            .setTitle("确定删除账户码？")
            .setMessage("删除后您的账号将不会被保留\n其它信息也将会被删除")
            .setNeutralButton("取消") { dialog, which ->

            }
            .setPositiveButton("是") { dialog, which ->
                //修改一下缓存数据
                val PREF_FILE_NAME = "Mydata"
                val LoginID = "loginid"
                val IsLogin = "islogin"
                val pref = activity?.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                val editor = pref?.edit()
                editor?.putString(LoginID, "")
                editor?.putBoolean(IsLogin, false)
                editor?.apply()

                Toast.makeText(activity, "成功删除账户", Toast.LENGTH_SHORT).show()
                val intent = Intent(activity, LoginActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                activity?.startActivity(intent)
            }
            .show()
    }

    private fun ShowAboutAlertDialog() {
        MaterialAlertDialogBuilder(activity!!)
            .setTitle("关于失物招领")
            .setMessage(
                "感谢您的使用\n" +
                        "作者：张昊杰\n" +
                        "学校：BIT\n" +
                        "邮箱：zhj727534681@163.com\n"
            )
            .setPositiveButton("确定") { dialog, which ->
            }
            .show()
    }
}