package com.mathieugouverneur.homeview.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mathieugouverneur.homeview.R

class MainActivity : AppCompatActivity() {

    private lateinit var movies : Button
    private lateinit var characters : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movies = findViewById(R.id.movies)
        characters = findViewById(R.id.characters)

        /** ================= GO TO MOVIE ACTIVITY ======================*/
        movies.setOnClickListener {
            val intent = Intent(this, MoviesActivity::class.java)
            startActivity(intent)
        }

        /** ================= GO TO CHARACTERS ACTIVITY =================*/
        characters.setOnClickListener {
            val intent = Intent(this, CharactersActivity::class.java)
            startActivity(intent)
        }
    }
}