package com.mathieugouverneur.homeview.activity

import android.app.ProgressDialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.mathieugouverneur.homeview.R
import com.mathieugouverneur.homeview.models.Movie
import com.mathieugouverneur.homeview.adapters.MovieListAdapter
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MoviesActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        /** 1- Bouton pour revenir à l'écran d'accueil */
        actionBar?.setDisplayHomeAsUpEnabled(true)

        /** 2- Changer le titre de cette page */
        val actionBar = supportActionBar
        actionBar!!.title = "The movie list"

        /** 3- Bind view */
        listView = findViewById(R.id.movies_list)

        /** 4- URL */
        val url = "https://swapi.dev/api/films/"

        /** 4.1- call asyncTask */
        AsynckTaskMovieHandler().execute(url)

    }

    /** 5 AsyncTask for handler */
    inner class AsynckTaskMovieHandler : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(this@MoviesActivity)
            progressDialog.setMessage("Veuillez patienter s'il vous plaît !")
            progressDialog.setCancelable(false)
            progressDialog.show()
        }

        override fun doInBackground(vararg url: String?): String {
            val res : String
            val connection = URL(url[0]).openConnection() as HttpURLConnection

            try {
                connection.connect()
                res = connection.inputStream.use { it.reader().use { reader ->reader.readText() } }

            } finally {
                connection.disconnect()
            }
            return res
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (progressDialog.isShowing){
                progressDialog.dismiss()
            }
            parseJSonFilm(result!!)
        }

        private fun parseJSonFilm(json: String) {

            // 1 - Convertir en JSON
            val jsonObject = JSONObject(json)

            // 2 - Recuperer le tableau results de JSON
            val jsonArray = jsonObject.getJSONArray("results")

            // 3 - List de films
            val movieList = ArrayList<Movie>()

            for (i in 0 until jsonArray.length()){

                val movieObjectJSON = jsonArray.getJSONObject(i)

                val edited: String = movieObjectJSON.get("edited").toString()
                val director: String = movieObjectJSON.get("director").toString()
                val created: String = movieObjectJSON.get("created").toString()
                val opening_crawl: String = movieObjectJSON.get("opening_crawl").toString()
                val title: String = movieObjectJSON.get("title").toString()
                val url: String = movieObjectJSON.get("url").toString()
                val episode_id: Int = movieObjectJSON.get("episode_id") as Int
                val release_date: String = movieObjectJSON.get("release_date").toString()
                val producer: String = movieObjectJSON.get("producer").toString()

                val characters: MutableList<String>? = mutableListOf()
                val planets: MutableList<String>? = mutableListOf()
                val starships: MutableList<String>? = mutableListOf()
                val vehicles: MutableList<String>? = mutableListOf()
                val species: MutableList<String>? = mutableListOf()

                val charactersArrayJSONObject = movieObjectJSON.getJSONArray("characters")
                val planetsArrayJSONObject = movieObjectJSON.getJSONArray("planets")
                val starshipsArrayJSONObject = movieObjectJSON.getJSONArray("starships")
                val vehiclesArrayJSONObject = movieObjectJSON.getJSONArray("vehicles")
                val speciesArrayJSONObject = movieObjectJSON.getJSONArray("species")

                /** Get all characters*/
                for (c in 0 until  charactersArrayJSONObject.length()){
                    characters!!.add(charactersArrayJSONObject.get(c).toString())
                }

                /** Get all planets*/
                for (p in 0 until planetsArrayJSONObject.length()){
                    planets!!.add(planetsArrayJSONObject.get(p).toString())
                }

                /** Get all starships*/
                for (s in 0 until starshipsArrayJSONObject.length()){
                    starships!!.add(starshipsArrayJSONObject.get(s).toString())
                }

                /** Get all vehicles*/
                for (v in 0 until vehiclesArrayJSONObject.length()){
                    vehicles!!.add(vehiclesArrayJSONObject.get(v).toString())
                }

                /** Get all species*/
                for (s in 0 until speciesArrayJSONObject.length()){
                    species!!.add(speciesArrayJSONObject.get(s).toString())
                }

                val movieItem = Movie(
                    edited, director, created, vehicles,
                    opening_crawl, title, url,
                    characters, episode_id, planets,
                    release_date, starships, species, producer
                )

                movieList?.add(movieItem)
            }

            val adapter = MovieListAdapter(this@MoviesActivity, movieList)
            listView.adapter = adapter
        }
    }
}