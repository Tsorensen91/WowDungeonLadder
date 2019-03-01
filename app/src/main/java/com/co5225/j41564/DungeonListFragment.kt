package com.co5225.j41564



import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_dungeon_list.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class DungeonListFragment : Fragment() {

    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dungeon_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        layoutManager = LinearLayoutManager(activity)
        rvDungeonRunList.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        rvDungeonRunList.adapter = adapter

    }





}
