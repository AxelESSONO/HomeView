package com.mathieugouverneur.homeview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mathieugouverneur.homeview.models.Movie
import com.mathieugouverneur.homeview.R

class MovieListAdapter(val context: Context, val listMovie: ArrayList<Movie>): BaseAdapter()
{

    private val MAX_LINES_COLLAPSED = 3
    private val INITIAL_IS_COLLAPSED = true
    private var isCollapsed = INITIAL_IS_COLLAPSED

    override fun getCount(): Int {
        return listMovie.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.movie_row, parent, false)

        /** Bind View */
        val title : TextView = view.findViewById(R.id.title)
        val episodeId : TextView = view.findViewById(R.id.episode_id)
        val director : TextView = view.findViewById(R.id.director)
        val producer : TextView = view.findViewById(R.id.producer)
        val release_date : TextView = view.findViewById(R.id.release_date)
        val numberCharacters : TextView = view.findViewById(R.id.number_of_characters)
        val openingCrawl : TextView = view.findViewById(R.id.opening_crawl)

        title.text = listMovie[position].title
        episodeId.text = listMovie[position].episode_id.toString()
        director.text = listMovie[position].director
        producer.text = listMovie[position].producer
        release_date.text = listMovie[position].release_date
        numberCharacters.text = listMovie[position].characters?.size.toString()
        openingCrawl.text = listMovie[position].opening_crawl

        openingCrawl.setOnClickListener {
            if (isCollapsed) {
                openingCrawl.setMaxLines(Integer.MAX_VALUE)
            } else {
                openingCrawl.setMaxLines(MAX_LINES_COLLAPSED)
            }
            isCollapsed = !isCollapsed;
        }

        return view

    }
}