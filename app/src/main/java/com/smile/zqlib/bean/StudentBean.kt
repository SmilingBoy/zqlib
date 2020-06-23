package com.smile.zqlib.bean

import java.io.Serializable

data class StudentBean(
    val name: String,
    val sex: String,
    val age: Int
) : Serializable