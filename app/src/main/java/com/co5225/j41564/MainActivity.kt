package com.co5225.j41564

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerAdapter
    var searchParameters : Array<String> = arrayOf("All", "All", "All", "All")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        layoutManager = LinearLayoutManager(this)
        rvDungeonRunList.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        rvDungeonRunList.adapter = adapter
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#144587")))
        getDungeonRuns(searchParameters)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        newSearch()
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == Activity.RESULT_OK) {
                @Suppress("UNCHECKED_CAST")
                val newSearch = data?.getSerializableExtra("search") as Array<String>
                adapter.removeRuns()
                getDungeonRuns(newSearch)
            }

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

    fun getDungeonRuns(searchParameters: Array<String>) {
        fetchData(urlCompiler(searchParameters))
    }

    fun urlCompiler(searchParameters : Array<String>): String{
        val baseURl = "https://raider.io/api/v1/mythic-plus/runs?season=season-bfa-2"
        var regionURL = ""
        var dungeonURL = ""
        var affixURL = ""
        when (searchParameters[0]) {
            "All" -> regionURL = "&region=world"
            "EU" -> regionURL = "&region=eu"
            "US" -> regionURL = "&region=us"
            "TW" -> regionURL = "&region=tw"
            "KR" -> regionURL = "&region=kr"
        }
        when (searchParameters[1]) {
            "All" -> dungeonURL = ""
            "AtalDazar" -> dungeonURL = "&dungeon=ataldazar"
            "Freehold" -> dungeonURL = "&dungeon=freehold"
            "Kings rest" -> dungeonURL = "&dungeon=kings-rest"
            "Shrine of the Storm" -> dungeonURL = "&dungeon=shrine-of-the-storm"
            "Siege of Boralus" -> dungeonURL = "&dungeon=siege-of-boralus"
            "Temple of Sethraliss" -> dungeonURL = "&dungeon=temple-of-sethraliss"
            "The Motherlode!!" -> dungeonURL = "&dungeon=the-motherlode"
            "The Underrot" -> dungeonURL = "&dungeon=the-underrot"
            "Tol Dagor" -> dungeonURL = "&dungeon=tol-dagor"
            "Waycrest Manor" -> dungeonURL = "&dungeon=waycrest-manor"
        }
        when (searchParameters[2]) {
            "All" -> affixURL = ""
            "Tyrannical" -> affixURL = "&affixes=tyrannical"
            "Fortified" -> affixURL = "&affixes=fortified"
        }
        return baseURl+regionURL+dungeonURL+affixURL
    }

    fun fetchData (urlString: String){
        val thread = Thread{
            try {
                val url = URL(urlString)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
                    val text = if (scanner.hasNext()) scanner.next() else ""
                    val jsonPacket = JSONUnpacker(text)
                    val list = jsonPacket.getDungeonList()
                    addRunFromList(list)

                } else {
                    println("the server has returned an error: $responseCode")
                }
            } catch (e: IOException) {
                println("en error occurred retrieving data")
            }
        }
        thread.start()
    }

    private fun newSearch() {
        val intent = Intent(this,NewSearchActivity::class.java)
        startActivityForResult(intent,0)
    }



}
