package com.mathieugouverneur.homeview.activity

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.mathieugouverneur.homeview.R
import com.mathieugouverneur.homeview.adapters.CharacterListAdapter
import com.mathieugouverneur.homeview.adapters.MovieListAdapter
import com.mathieugouverneur.homeview.models.Characters
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class CharactersActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

        /** 1- Bouton pour revenir à l'écran d'accueil */
        actionBar?.setDisplayHomeAsUpEnabled(true)

        /** 2- Changer le titre de cette page */
        val actionBar = supportActionBar
        actionBar!!.title = "The character list"

        /** 3- Bind view */
        listView = findViewById(R.id.characters_list)

        /** 4- URL */
        val url = "https://swapi.dev/api/people/"

        /** 4.1- call asyncTask */
        AsynckTaskCharacterHandler().execute(url)
    }

    /** 5 AsyncTask for handler */
    inner class AsynckTaskCharacterHandler : AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(this@CharactersActivity)
            progressDialog.setMessage("Veuillez patienter s'il vous plaît !")
            progressDialog.setCancelable(false)
            progressDialog.show()
        }

        override fun doInBackground(vararg url: String?): String {
            val res: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection

            try {
                connection.connect()
                res = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }

            } finally {
                connection.disconnect()
            }
            return res
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }
            parseJSonCharacter(result!!)
        }

        private fun parseJSonCharacter(json: String) {

            val jsonObject = JSONObject(json)
            val jsonArray = jsonObject.getJSONArray("results")

            val characters = ArrayList<Characters>()


            for (i in 0 until jsonArray.length()) {

                val charactersJson = jsonArray.getJSONObject(i)

                val homeworld: String = charactersJson.get("homeworld").toString()
                val gender: String = charactersJson.get("gender").toString()
                val skin_color: String = charactersJson.get("skin_color").toString()
                val edited: String = charactersJson.get("edited").toString()
                val created: String = charactersJson.get("created").toString()
                val mass: String = charactersJson.get("mass").toString()

                val url: String = charactersJson.get("url").toString()
                val hair_color: String = charactersJson.get("hair_color").toString()
                val birth_year: String = charactersJson.get("birth_year").toString()
                val eye_color: String = charactersJson.get("eye_color").toString()

                val name: String = charactersJson.get("name").toString()
                val height: String = charactersJson.get("height").toString()

                val vehicles: MutableList<String>? = mutableListOf()
                val species: MutableList<String>? = mutableListOf()
                val starships: MutableList<String>? = mutableListOf()
                val films: MutableList<String>? = mutableListOf()

                val vehiclesArrayJSONObject = charactersJson.getJSONArray("vehicles")
                val speciesArrayJSONObject = charactersJson.getJSONArray("species")
                val starshipsArrayJSONObject = charactersJson.getJSONArray("starships")
                val filmsArrayJSONObject = charactersJson.getJSONArray("films")

                for (c in 0 until vehiclesArrayJSONObject.length()) {
                    vehicles!!.add(vehiclesArrayJSONObject.get(c).toString())
                }

                for (c in 0 until speciesArrayJSONObject.length()) {
                    species!!.add(speciesArrayJSONObject.get(c).toString())
                }

                for (c in 0 until starshipsArrayJSONObject.length()) {
                    starships!!.add(starshipsArrayJSONObject.get(c).toString())
                }

                for (c in 0 until filmsArrayJSONObject.length()) {
                    films!!.add(filmsArrayJSONObject.get(c).toString())
                }

                val charactersItem = Characters(
                    films, homeworld, gender, skin_color, edited,
                    created, mass, vehicles, species, url, hair_color, birth_year,
                    eye_color, starships, name, height
                )
                characters?.add(charactersItem)
            }

            val adapter = CharacterListAdapter(this@CharactersActivity, characters)
            listView.adapter = adapter

        }
    }
}