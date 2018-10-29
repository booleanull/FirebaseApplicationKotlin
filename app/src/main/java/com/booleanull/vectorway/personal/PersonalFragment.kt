package com.booleanull.vectorway.personal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.booleanull.vectorway.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.view.*
import android.support.v4.view.MenuItemCompat
import android.R.menu
import android.support.v7.widget.SearchView
import android.view.*


class PersonalFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragment_personal, container, false)
        activity!!.title = inflater.context.resources.getString(R.string.personal)
        return v
    }
}
