package com.booleanull.vectorway.posts

import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.booleanull.vectorway.R
import java.lang.reflect.Array


class PostAdapter(val layoutInflater: LayoutInflater, private val items: MutableList<ViewInPost>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Colors for TypeImage in Post
    private val colors = arrayOf(
            R.color.colorRed,
            R.color.colorOrange,
            R.color.colorGreen)

    // Icons for TypeImage in Post
    private val icons = arrayOf(
            R.drawable.ic_free_way,
            R.drawable.ic_ai_icon,
            R.drawable.ic_desktop_icon,
            R.drawable.ic_gamedev_icon,
            R.drawable.ic_android_icon,
            R.drawable.ic_apple_icon,
            R.drawable.ic_frontend_icon,
            R.drawable.ic_backend_icon,
            R.drawable.ic_lowlevel_icon
    )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getViewId()
    }

    override fun onCreateViewHolder(p0: ViewGroup, type: Int): RecyclerView.ViewHolder {
        return when(type) {
            // Event
            1 -> {
                val view: View = layoutInflater.inflate(R.layout.layout_event, p0, false)
                EventHolder(view)
            }
            // Note
            else -> {
                val view: View = layoutInflater.inflate(R.layout.layout_post, p0, false)
                PostHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {
        when(holder.itemViewType) {
            // Event
            1 -> {
                val event : Event = items[holder.adapterPosition] as Event
                val eventHolder = holder as EventHolder

                eventHolder.title.text = event.title
                eventHolder.text.text = event.text
            }
            // Post
            else -> {
                val post : Post = items[holder.adapterPosition] as Post
                val postHolder = holder as PostHolder

                postHolder.title.text = post.title
                postHolder.text.text = post.text
                postHolder.date.text = post.date

                postHolder.back.setBackgroundColor(
                    ContextCompat.getColor(
                        layoutInflater.context,
                        getSaveData(post.level, colors)
                    )
                )
                postHolder.icon.setImageResource(getSaveData(post.way, icons))

                if (post.site.equals(""))
                    postHolder.link.visibility = View.GONE
                else
                    postHolder.link.visibility = View.VISIBLE

                postHolder.card.setOnClickListener { v ->
                    if (!post.site.equals("")) {
                        val uriUrl = Uri.parse(post.site)
                        val intent = Intent(Intent.ACTION_VIEW,
                            uriUrl)
                        layoutInflater.context.startActivity(intent)
                    } else
                        Snackbar
                            .make(v!!, layoutInflater.context.getText(R.string.error_empty_site), Snackbar.LENGTH_SHORT)
                            .show()
                }
            }
        }
    }

    private fun getSaveData(i: Int, array : kotlin.Array<Int>): Int {
        if (i in 0..(array.size - 1)) {
            return array[i]
        }
        return array[0]
    }

    class EventHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val text: TextView = view.findViewById(R.id.text)
    }

    class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: CardView = view.findViewById(R.id.card)
        val title: TextView = view.findViewById(R.id.title)
        val text: TextView = view.findViewById(R.id.text)
        val date: TextView = view.findViewById(R.id.date)
        val back: LinearLayout = view.findViewById(R.id.back)
        val icon: ImageView = view.findViewById(R.id.icon)
        val link: ImageView = view.findViewById(R.id.link)
    }
}
