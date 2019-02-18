package com.co5225.j41564

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_layout.view.*

class RecyclerAdapter  : RecyclerView.Adapter<RecyclerAdapter.ViewHolder> (){

    var list = mutableListOf<DungeonRun>()

    init {
        
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 125
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvDifficulty.text = "+20"
        viewHolder.itemView.tvDungeonName.text = "Freehold"
        viewHolder.itemView.tvRank.text = position.toString()
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }
}