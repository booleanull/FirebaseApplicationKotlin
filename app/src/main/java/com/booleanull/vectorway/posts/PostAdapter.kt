package com.booleanull.vectorway.posts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.booleanull.vectorway.R

class PostAdapter(val layoutInflater: LayoutInflater, val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view : View = layoutInflater.inflate(R.layout.layout_post, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.title.text = posts[holder.adapterPosition].title
        holder.text.text = posts[holder.adapterPosition].text
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.title)
        val text : TextView = view.findViewById(R.id.text)
    }


}
