package com.co5225.j41564

import android.animation.ObjectAnimator
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import kotlinx.android.synthetic.main.card_layout.view.*


class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder> (){

    var list = mutableListOf<DungeonRun>()
    var redTheme = true
    var currentColor = "red"
    var themeNeedsUpdating = false

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
            //images adapted from raider.io, 2019
            "Tyrannical" -> cardView.affix1.setImageResource(R.drawable.tyrannical)
            "Fortified" -> cardView.affix1.setImageResource(R.drawable.fortified)
        }
        generalAffixImageAssigner(cardView, affix2, 2)
        generalAffixImageAssigner(cardView, affix3, 3)

        val class1 = item.classes[0]
        val class2 = item.classes[1]
        val class3 = item.classes[2]
        val class4 = item.classes[3]
        val class5 = item.classes[4]
        classImageAssigner(cardView, class1, 1)
        classImageAssigner(cardView, class2, 2)
        classImageAssigner(cardView, class3, 3)
        classImageAssigner(cardView, class4, 4)
        classImageAssigner(cardView, class5, 5)

        cardView.tvRank.text = item.rank.toString()
        cardView.tvDifficulty.text = difficulty
        cardView.tvDungeonName.text = item.name

        if (themeNeedsUpdating) {
            animation(cardView)
            if (position == 3) {
                themeNeedsUpdating = false
            }
        } else {
            if (redTheme) cardView.themeBar.progress = 100 else cardView.themeBar.progress = 0
        }
    }

    //shows class icons for classes in the dungeon
    private fun classImageAssigner(cardView: View, characterClass: String, characterNr : Int) {
        //images adapted from raider.io, 2019
        when (characterNr) {
            1 ->
            when (characterClass) {
                "Death Knight" -> cardView.class1.setImageResource(R.drawable.class_deathknight)
                "Demon Hunter" -> cardView.class1.setImageResource(R.drawable.class_demonhunter)
                "Druid" -> cardView.class1.setImageResource(R.drawable.class_druid)
                "Hunter" -> cardView.class1.setImageResource(R.drawable.class_hunter)
                "Mage" -> cardView.class1.setImageResource(R.drawable.class_mage)
                "Monk" -> cardView.class1.setImageResource(R.drawable.class_monk)
                "Paladin" -> cardView.class1.setImageResource(R.drawable.class_paladin)
                "Priest" -> cardView.class1.setImageResource(R.drawable.class_priest)
                "Rogue" -> cardView.class1.setImageResource(R.drawable.class_rogue)
                "Shaman" -> cardView.class1.setImageResource(R.drawable.class_shaman)
                "Warlock" -> cardView.class1.setImageResource(R.drawable.class_warlock)
                "Warrior" -> cardView.class1.setImageResource(R.drawable.class_warrior)
            }
            2->
            when (characterClass) {
                "Death Knight" -> cardView.class2.setImageResource(R.drawable.class_deathknight)
                "Demon Hunter" -> cardView.class2.setImageResource(R.drawable.class_demonhunter)
                "Druid" -> cardView.class2.setImageResource(R.drawable.class_druid)
                "Hunter" -> cardView.class2.setImageResource(R.drawable.class_hunter)
                "Mage" -> cardView.class2.setImageResource(R.drawable.class_mage)
                "Monk" -> cardView.class2.setImageResource(R.drawable.class_monk)
                "Paladin" -> cardView.class2.setImageResource(R.drawable.class_paladin)
                "Priest" -> cardView.class2.setImageResource(R.drawable.class_priest)
                "Rogue" -> cardView.class2.setImageResource(R.drawable.class_rogue)
                "Shaman" -> cardView.class2.setImageResource(R.drawable.class_shaman)
                "Warlock" -> cardView.class2.setImageResource(R.drawable.class_warlock)
                "Warrior" -> cardView.class2.setImageResource(R.drawable.class_warrior)
            }
            3->
            when (characterClass) {
                "Death Knight" -> cardView.class3.setImageResource(R.drawable.class_deathknight)
                "Demon Hunter" -> cardView.class3.setImageResource(R.drawable.class_demonhunter)
                "Druid" -> cardView.class3.setImageResource(R.drawable.class_druid)
                "Hunter" -> cardView.class3.setImageResource(R.drawable.class_hunter)
                "Mage" -> cardView.class3.setImageResource(R.drawable.class_mage)
                "Monk" -> cardView.class3.setImageResource(R.drawable.class_monk)
                "Paladin" -> cardView.class3.setImageResource(R.drawable.class_paladin)
                "Priest" -> cardView.class3.setImageResource(R.drawable.class_priest)
                "Rogue" -> cardView.class3.setImageResource(R.drawable.class_rogue)
                "Shaman" -> cardView.class3.setImageResource(R.drawable.class_shaman)
                "Warlock" -> cardView.class3.setImageResource(R.drawable.class_warlock)
                "Warrior" -> cardView.class3.setImageResource(R.drawable.class_warrior)
            }
            4->
            when (characterClass) {
                "Death Knight" -> cardView.class4.setImageResource(R.drawable.class_deathknight)
                "Demon Hunter" -> cardView.class4.setImageResource(R.drawable.class_demonhunter)
                "Druid" -> cardView.class4.setImageResource(R.drawable.class_druid)
                "Hunter" -> cardView.class4.setImageResource(R.drawable.class_hunter)
                "Mage" -> cardView.class4.setImageResource(R.drawable.class_mage)
                "Monk" -> cardView.class4.setImageResource(R.drawable.class_monk)
                "Paladin" -> cardView.class4.setImageResource(R.drawable.class_paladin)
                "Priest" -> cardView.class4.setImageResource(R.drawable.class_priest)
                "Rogue" -> cardView.class4.setImageResource(R.drawable.class_rogue)
                "Shaman" -> cardView.class4.setImageResource(R.drawable.class_shaman)
                "Warlock" -> cardView.class4.setImageResource(R.drawable.class_warlock)
                "Warrior" -> cardView.class4.setImageResource(R.drawable.class_warrior)
            }
            5->
            when (characterClass) {
                "Death Knight" -> cardView.class5.setImageResource(R.drawable.class_deathknight)
                "Demon Hunter" -> cardView.class5.setImageResource(R.drawable.class_demonhunter)
                "Druid" -> cardView.class5.setImageResource(R.drawable.class_druid)
                "Hunter" -> cardView.class5.setImageResource(R.drawable.class_hunter)
                "Mage" -> cardView.class5.setImageResource(R.drawable.class_mage)
                "Monk" -> cardView.class5.setImageResource(R.drawable.class_monk)
                "Paladin" -> cardView.class5.setImageResource(R.drawable.class_paladin)
                "Priest" -> cardView.class5.setImageResource(R.drawable.class_priest)
                "Rogue" -> cardView.class5.setImageResource(R.drawable.class_rogue)
                "Shaman" -> cardView.class5.setImageResource(R.drawable.class_shaman)
                "Warlock" -> cardView.class5.setImageResource(R.drawable.class_warlock)
                "Warrior" -> cardView.class5.setImageResource(R.drawable.class_warrior)
            }
        }
    }

    //shows modifier icons for modifiers in the dungeon
    private fun generalAffixImageAssigner(cardView: View, affix: String, affixNr : Int) {
        //images adapted from raider.io, 2019
        if (affixNr == 2) {
            when (affix) {
                "Bolstering" -> cardView.affix2.setImageResource(R.drawable.bolstering)
                "Bursting" -> cardView.affix2.setImageResource(R.drawable.bursting)
                "Explosive" -> cardView.affix2.setImageResource(R.drawable.explosive)
                "Grievous" -> cardView.affix2.setImageResource(R.drawable.grievous)
                "Necrotic" -> cardView.affix2.setImageResource(R.drawable.necrotic)
                "Quaking" -> cardView.affix2.setImageResource(R.drawable.quaking)
                "Raging" -> cardView.affix2.setImageResource(R.drawable.raging)
                "Sanguine" -> cardView.affix2.setImageResource(R.drawable.sanguine)
                "Skittish" -> cardView.affix2.setImageResource(R.drawable.skittish)
                "Teeming" -> cardView.affix2.setImageResource(R.drawable.teeming)
                "Volcanic" -> cardView.affix2.setImageResource(R.drawable.volcanic)
            }
        } else {
            when (affix) {
                "Bolstering" -> cardView.affix3.setImageResource(R.drawable.bolstering)
                "Bursting" -> cardView.affix3.setImageResource(R.drawable.bursting)
                "Explosive" -> cardView.affix3.setImageResource(R.drawable.explosive)
                "Grievous" -> cardView.affix3.setImageResource(R.drawable.grievous)
                "Necrotic" -> cardView.affix3.setImageResource(R.drawable.necrotic)
                "Quaking" -> cardView.affix3.setImageResource(R.drawable.quaking)
                "Raging" -> cardView.affix3.setImageResource(R.drawable.raging)
                "Sanguine" -> cardView.affix3.setImageResource(R.drawable.sanguine)
                "Skittish" -> cardView.affix3.setImageResource(R.drawable.skittish)
                "Teeming" -> cardView.affix3.setImageResource(R.drawable.teeming)
                "Volcanic" -> cardView.affix3.setImageResource(R.drawable.volcanic)
            }
        }
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    }

    //code adapted from rsicarelli, 2015
    fun animation (cardView: View) {
        if (redTheme) {
            val progressAnimator = ObjectAnimator.ofInt(cardView.themeBar, "progress", 0, 100)
            progressAnimator.duration = 2000
            progressAnimator.interpolator = LinearInterpolator()
            progressAnimator.start()
            currentColor = "red"
        } else {
            val progressAnimator = ObjectAnimator.ofInt(cardView.themeBar, "progress", 100, 0)
            progressAnimator.duration = 2000
            progressAnimator.interpolator = LinearInterpolator()
            progressAnimator.start()
            currentColor = "blue"
        }
    }
    //end of adapted code

    fun changeToRedTheme () {
        redTheme = true
        if (currentColor != "red") themeNeedsUpdating=true else themeNeedsUpdating = false
        notifyDataSetChanged()
    }
    fun changeToBlueTheme() {
        redTheme = false
        if (currentColor != "blue") themeNeedsUpdating=true else themeNeedsUpdating = false
        notifyDataSetChanged()
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