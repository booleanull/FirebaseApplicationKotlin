package com.booleanull.vectorway.posts

interface ViewInPost {
    val id_post : Int
        get() = 0
    val id_event : Int
        get() = 1

    fun getViewId() : Int
}