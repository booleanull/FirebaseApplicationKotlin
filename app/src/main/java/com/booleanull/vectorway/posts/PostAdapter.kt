package com.booleanull.vectorway.posts

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.booleanull.vectorway.R

class PostAdapter(val layoutInflater: LayoutInflater, val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    val colors = arrayOf(R.color.colorRed, R.color.colorOrange, R.color.colorGreen)
    val icons = arrayOf(R.drawable.ic_free_way, R.drawable.ic_ai_icon, R.drawable.ic_desktop_icon, R.drawable.ic_gamedev_icon,
        R.drawable.ic_android_icon, R.drawable.ic_apple_icon, R.drawable.ic_frontend_icon, R.drawable.ic_backend_icon, R.drawable.ic_lowlevel_icon)

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
        holder.date.text = posts[holder.adapterPosition].date
        holder.back.setBackgroundColor(ContextCompat.getColor(layoutInflater.context, getColorBack(posts[holder.adapterPosition].level)))
        holder.icon.setImageResource(getImageIcon(posts[holder.adapterPosition].way))
    }

    private fun getColorBack(i : Int) : Int {
        if(i in 0..2) {
            return colors[i]
        }
        return colors[0]
    }

    private fun getImageIcon(i : Int) : Int {
        if(i in 0..8) {
            return icons[i]
        }
        return icons[0]
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.title)
        val text : TextView = view.findViewById(R.id.text)
        val date : TextView = view.findViewById(R.id.date)
        val back : LinearLayout = view.findViewById(R.id.back)
        val icon : ImageView = view.findViewById(R.id.icon)
    }
}
