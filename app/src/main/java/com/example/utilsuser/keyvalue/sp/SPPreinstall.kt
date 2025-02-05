package com.example.utilsuser.keyvalue.sp

import android.annotation.SuppressLint
import com.example.utilsgather.context.ApplicationGlobal

// 封装程度（由使用的方面程度 方便 -> 复杂）：SPPreinstall > SPDelegate > SPUtil

@SuppressLint("StaticFieldLeak")
object SPPreinstall {
    private val context = ApplicationGlobal.getInstance()

    var commonName by SPDelegate(
        context,
        "name",
        "kkkkkk",
        SPUtil.Model.COMMON
    )

    var personalAge by SPDelegate(
        context,
        "age",
        500,
        SPUtil.Model.PERSONAL
    )
}