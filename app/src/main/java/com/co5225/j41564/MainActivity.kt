package com.co5225.j41564

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun getQuote(view: View) {
        fetchData("https://raider.io/api/v1/mythic-plus/runs?season=season-bfa-2&region=world&dungeon=all")
    }

    private fun processMatchJson(jsonString: String) : String {
        val jsonObject = JSONObject(jsonString)
        val rankingsArray = jsonObject.getJSONArray("rankings")
        val rankObject = rankingsArray.getJSONObject(0)
        val rankings = rankObject.get("rank").toString()
        var rank = ""
        for (i in 0..rankings.length-1) {
            if(rankings[i].isLetterOrDigit()){
                rank += rankings[i]
            } else if (rankings[i] == ','){
                break
            } else if (rankings[i] == ':'){
                rank += rankings[i] + " "
            }
        }
        return rankings
    }

    private fun fetchData (urlString: String) {
        val thread = Thread{

            try {
                val url = URL(urlString)
                val connection:HttpURLConnection = url.openConnection() as HttpURLConnection;
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
                    val text = if (scanner.hasNext()) scanner.next() else ""
                    val quote = processMatchJson(text)
                    updateTextView(quote)
                } else {
                    updateTextView("the server has returned an error: $responseCode")
                }
            } catch (e: IOException) {
                updateTextView("en error occurred retrieving data")
            }

        }
        thread.start()
    }

    private fun updateTextView(text: String) {
        runOnUiThread{
            textView.text = text
        }
    }
}
