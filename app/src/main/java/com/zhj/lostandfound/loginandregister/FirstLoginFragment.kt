package com.zhj.lostandfound.loginandregister

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import com.zhj.lostandfound.R

class FirstLoginFragment(val num: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_first_login, container, false)
        val tvInfo = root.findViewById<TextView>(R.id.tvInfo)
        val buttonStart=root.findViewById<Button>(R.id.ButtonStart)
        if (num == 0) {
            tvInfo.setText("欢迎来到失物招领")
            buttonStart.isVisible=false
        } else if (num == 1) {
            tvInfo.setText("可以找寻失物或发布找到的物品")
           buttonStart.isVisible=false
        } else if (num == 2) {
            tvInfo.setText("进入失物招领平台")
           buttonStart.isVisible=true
        }
        buttonStart.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity?.startActivity(intent)
        }
        return root
    }
}