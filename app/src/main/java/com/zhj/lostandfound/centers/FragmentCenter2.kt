package com.zhj.lostandfound.centers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.zhj.lostandfound.NewThingActivity
import com.zhj.lostandfound.R

class FragmentCenter2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_center2, container, false)

        val buttonjian = root.findViewById<Button>(R.id.ButtonJian)
        val buttondiu = root.findViewById<Button>(R.id.ButtonDiu)

        buttonjian.setOnClickListener {
            val intent = Intent(activity, NewThingActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("whatthing", 1)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        buttondiu.setOnClickListener {
            val intent = Intent(activity, NewThingActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("whatthing",0)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        return root
    }
}