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
        val difficulty = "+" + item.difficulty.toString()
        val affix1 = item.modifiers[0]
        val affix2 = item.modifiers[1]
        val affix3 = item.modifiers[2]
        when (affix1) {
            "Fortified" -> cardView.affix1.setImageResource(R.drawable.fortified)
        }
        when (affix2) {
            "Bolstering" -> cardView.affix2.setImageResource(R.drawable.bolstering)
            "Bursting" -> cardView.affix2.setImageResource(R.drawable.bursting)
            "Explosive" -> cardView.affix2.setImageResource(R.drawable.explosive)
            "Grievous" -> cardView.affix2.setImageResource(R.drawable.grievous)
            "Necrotic" -> cardView.affix2.setImageResource(R.drawable.necrotic)
            "Quaking" -> cardView.affix2.setImageResource(R.drawable.quaking)
            "Raging" -> cardView.affix2.setImageResource(R.drawable.raging)
            "Sanguine" -> cardView.affix2.setImageResource(R.drawable.sanguine)
            "Teeming" -> cardView.affix2.setImageResource(R.drawable.teeming)
            "Volcanic" -> cardView.affix2.setImageResource(R.drawable.volcanic)
        }
        when (affix3) {
            "Bolstering" -> cardView.affix2.setImageResource(R.drawable.bolstering)
            "Bursting" -> cardView.affix2.setImageResource(R.drawable.bursting)
            "Explosive" -> cardView.affix2.setImageResource(R.drawable.explosive)
            "Grievous" -> cardView.affix2.setImageResource(R.drawable.grievous)
            "Necrotic" -> cardView.affix2.setImageResource(R.drawable.necrotic)
            "Quaking" -> cardView.affix2.setImageResource(R.drawable.quaking)
            "Raging" -> cardView.affix2.setImageResource(R.drawable.raging)
            "Sanguine" -> cardView.affix2.setImageResource(R.drawable.sanguine)
            "Teeming" -> cardView.affix2.setImageResource(R.drawable.teeming)
            "Volcanic" -> cardView.affix2.setImageResource(R.drawable.volcanic)
        }
        cardView.tvRank.text = item.rank.toString()
        cardView.tvDifficulty.text = difficulty
        cardView.tvDungeonName.text = item.name

    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    }

    fun addRun (run : DungeonRun) {
            list.add(run)
            notifyItemInserted(list.lastIndex)
    }

    fun removeRuns() {
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val run = iterator.next()
            if (run != null) iterator.remove()
        }
        notifyDataSetChanged()
    }





}