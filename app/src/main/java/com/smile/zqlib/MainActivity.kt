package com.smile.zqlib

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smile.zqlib.bean.StudentBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tv_sm.setOnClickListener {
            tv_sm.isSelected = !tv_sm.isSelected
        }

        tv_sm_to_second.setOnClickListener {

            startActivity(Intent(this, SecondActivity::class.java).also {
                it.putExtra("name", "小王")
                it.putExtra("age", 30)
//                it.putExtra("sex", "女")
                it.putExtra("xingbie", "女3")
                it.putExtra("student", StudentBean("小瓜", "男", 22))
            })

        }
    }
}
