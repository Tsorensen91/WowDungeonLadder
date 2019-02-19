package com.co5225.j41564

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        layoutManager = LinearLayoutManager(this)
        rvDungeonRunList.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        rvDungeonRunList.adapter = adapter
        getQuote()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun addRun (run : DungeonRun) {
        runOnUiThread {
            adapter.addRun(run)
        }
    }

    fun addRunFromList (list : List<DungeonRun>) {
        for (i in list.indices) {
            addRun(list[i])
        }
    }

    fun getQuote() {
        fetchData("https://raider.io/api/v1/mythic-plus/runs?season=season-bfa-2&region=world&dungeon=all")
    }

    private fun processMatchJson(jsonString: String) : List<DungeonRun> {
        val jsonObject = JSONObject(jsonString)
        val rankingsArray = jsonObject.getJSONArray("rankings")
        var list = mutableListOf<DungeonRun>()
        for (j in 0..19) {
            val rankObject = rankingsArray.getJSONObject(j)
            val rankings = rankObject.get("rank").toString().toInt()
            val runObject = rankObject.getJSONObject("run")
            val dungeonObject = runObject.getJSONObject("dungeon")
            val dungeonName = dungeonObject.get("name").toString()
            val dungeonLevel = runObject.get("mythic_level").toString().toInt()
            val dungeonRun = DungeonRun(dungeonName,dungeonLevel,rankings)
            list.add(dungeonRun)
        }
        return list
    }

    fun fetchData (urlString: String){
        var list = mutableListOf<DungeonRun>()
        val thread = Thread{
            try {
                val url = URL(urlString)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection;
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
                    val text = if (scanner.hasNext()) scanner.next() else ""
                    var tempList = processMatchJson(text)
                    for( i in tempList.indices) {
                        list.add(tempList[i])
                    }
                    addRunFromList(list)


                } else {
                    var errorRun = DungeonRun(("the server has returned an error: $responseCode"),0,0)
                    var errorList = mutableListOf<DungeonRun>()
                    errorList.add(errorRun)
                    list = errorList
                }
            } catch (e: IOException) {
                var errorRun = DungeonRun(("en error occurred retrieving data"),0,0)
                var errorList = mutableListOf<DungeonRun>()
                errorList.add(errorRun)
                list = errorList
            }
        }
        thread.start()
    }










}
