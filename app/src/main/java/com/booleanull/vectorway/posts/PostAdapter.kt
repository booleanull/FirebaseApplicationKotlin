package com.booleanull.vectorway.posts

import android.content.Intent
import android.net.Uri
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.booleanull.vectorway.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_event.view.*
import kotlinx.android.synthetic.main.layout_post.view.*

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

                if (event.image != "") {
                    Picasso.get().load(Uri.parse(event.image)).into(eventHolder.photo);
                    holder.card.visibility = View.VISIBLE
                } else
                    holder.card.visibility = View.GONE

                eventHolder.event.setOnClickListener(onClickLink(event.site))
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
                        getData(post.level, colors)
                    )
                )
                postHolder.icon.setImageResource(getData(post.way, icons))

                if (post.site.equals(""))
                    postHolder.link.visibility = View.GONE
                else
                    postHolder.link.visibility = View.VISIBLE

                postHolder.card.setOnClickListener(onClickLink(post.site))
            }
        }
    }

    private fun getData(i: Int, array: kotlin.Array<Int>): Int {
        if (i in 0..(array.size - 1)) {
            return array[i]
        }
        return array[0]
    }

    private fun onClickLink(site: String): OnClickListener {
        return OnClickListener {
            if (site != "") {
                val uriUrl = Uri.parse(site)
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    uriUrl
                )
                layoutInflater.context.startActivity(intent)
            } /*else {
                Snackbar.make(it, layoutInflater.context.getText(R.string.error_empty_site), Snackbar.LENGTH_SHORT).show()
            }*/
        }
    }

    class EventHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.titleEvent
        val text = view.textEvent
        val photo = view.photoEvent
        val card = view.cardEvent
        val event = view.event
    }

    class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card = view.cardPost
        val title = view.titlePost
        val text = view.textPost
        val date = view.date
        val back = view.back
        val icon = view.icon
        val link = view.link
    }
}
