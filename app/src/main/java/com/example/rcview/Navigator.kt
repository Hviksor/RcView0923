package com.example.rcview

interface Navigator {
    fun back()
    fun userDetail(userId: Long)
    fun toast(message: String)

}