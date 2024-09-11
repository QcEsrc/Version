package com.android.activity.ui.just

data class RootFileItem(
    val name: String,          // 文件或目录的名称
    val path: String,          // 文件或目录的路径
    val isDirectory: Boolean   // 是否是目录
)