package com.co5225.j41564

import org.json.JSONObject

class JSONUnpacker (jsonString: String){

    val jsonObject = JSONObject(jsonString)
    val rankingsArray = jsonObject.getJSONArray("rankings")
    var list = mutableListOf<DungeonRun>()
    init {
        for (j in 0..19) {
            val rankObject = rankingsArray.getJSONObject(j)
            val rankings = rankObject.get("rank").toString().toInt()
            val runObject = rankObject.getJSONObject("run")
            val dungeonObject = runObject.getJSONObject("dungeon")
            val dungeonName = dungeonObject.get("name").toString()
            val dungeonLevel = runObject.get("mythic_level").toString().toInt()


            val modifierNumber = runObject.get("num_modifiers_active").toString().toInt()
            val modifierArray = runObject.getJSONArray("weekly_modifiers")
            var modifierList = mutableListOf<String>()
            for (i in 0..modifierNumber-1){
                val modifierObject = modifierArray.getJSONObject(i)
                val modifier = modifierObject.get("name").toString()
                modifierList.add(modifier)
            }

            val rosterArray = runObject.getJSONArray("roster")
            var classList = mutableListOf<String>()
            for(i in 0..4) {
                val characterArray = rosterArray.getJSONObject(i)
                val characterObject = characterArray.getJSONObject("character")
                val classObject = characterObject.getJSONObject("class")
                val characterClass = classObject.get("name").toString()
                classList.add(characterClass)
            }

            val dungeonRun = DungeonRun(dungeonName,dungeonLevel,rankings, classList, modifierList)
            list.add(dungeonRun)
        }
    }

    fun getDungeonList() : List<DungeonRun> {
        return this.list
    }

}