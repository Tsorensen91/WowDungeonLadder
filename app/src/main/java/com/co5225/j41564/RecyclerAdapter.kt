package com.co5225.j41564

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_layout.view.*

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder> (){

    var list = mutableListOf<DungeonRun>()


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val cardView = viewHolder.itemView
        val item = list[position]
        cardView.tvRank.text = item.rank.toString()
        cardView.tvDifficulty.text = item.difficulty.toString()
        cardView.tvDungeonName.text = item.name
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    }

    fun addRun (run : DungeonRun) {
            list.add(run)
            notifyItemInserted(list.lastIndex)

    }





}