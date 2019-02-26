package com.co5225.j41564

import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        layoutManager = LinearLayoutManager(this)
        rvDungeonRunList.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        rvDungeonRunList.adapter = adapter
        getDungeonRuns()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        newSearch()

        return super.onOptionsItemSelected(item)
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

    fun getDungeonRuns() {
        fetchData("https://raider.io/api/v1/mythic-plus/runs?season=season-bfa-2&region=world&dungeon=all")
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

    fun buttonOnClick(){
        newSearch()
    }

    private fun newSearch() {
        val intent = Intent(this,NewSearchActivity::class.java)
        startActivityForResult(intent,0)
    }



}
