package com.smile.zqlib

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.smile.autofilldata.AutoFillData
import com.smile.autofilldata.SmAutoFillData
import com.smile.zqlib.bean.StudentBean
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    @AutoFillData
    private lateinit var name: String

    @AutoFillData
    private var age = 0

    @AutoFillData(key = "xingbie")
    private var sex = "男"

    @AutoFillData
    private lateinit var student: StudentBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        initAnnotation()


        Log.e("value", "name:$name, age:$age, sex:$sex")
        Log.e("student", student.toString())


        tv_content.text = "name:$name, age:$age, sex:$sex \n $student"
    }

    private fun initAnnotation() {

        SmAutoFillData.getInstance().inject(this)

    }
}