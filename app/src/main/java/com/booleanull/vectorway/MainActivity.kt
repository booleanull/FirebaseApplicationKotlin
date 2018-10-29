package com.booleanull.vectorway

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.booleanull.vectorway.personal.PersonalFragment
import com.booleanull.vectorway.posts.NewsFragment
import com.booleanull.vectorway.users.UsersFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val newsFragment = NewsFragment()
    val projectFragment = UsersFragment()
    val personalFragment = PersonalFragment()

    lateinit var fragmentManager: FragmentManager
    var position: Int = 0

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.getItemId()) {
                R.id.navigation_news -> {
                    if (position != 0) {
                        setFragment(R.string.post, newsFragment, 0)
                        position = 0
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_project -> {
                    if (position != 1) {
                        setFragment(R.string.users, projectFragment, 1)
                        position = 1
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_personal -> {
                    if (position != 2) {
                        setFragment(R.string.personal, personalFragment, 2)
                        position = 2
                    }
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager
        setFragment(R.string.post, newsFragment, 0)

        val navigation = navigation
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun setFragment(id: Int, fragment: Fragment, idfrag: Int) {
        supportActionBar!!.setTitle(getString(id))
        fragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        position = idfrag
    }
}
