package com.booleanull.vectorway.posts
data class Event (
        val title : String = "Title",
        val text : String = "Some text",
        val site : String = "",
        val image : String = "") : ViewInPost {

    override fun getViewId(): Int {
        return id_event
    }
}