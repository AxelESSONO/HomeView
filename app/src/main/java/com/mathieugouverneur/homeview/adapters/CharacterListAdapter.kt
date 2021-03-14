package com.mathieugouverneur.homeview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mathieugouverneur.homeview.R
import com.mathieugouverneur.homeview.models.Characters

class CharacterListAdapter(val context: Context, val listCharacter: ArrayList<Characters>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return listCharacter.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.character_row, parent, false)

        /** Bind View */
        val name: TextView = view.findViewById(R.id.name)
        val heightCharacter: TextView = view.findViewById(R.id.height_character)
        val mass: TextView = view.findViewById(R.id.mass)
        val hairColor: TextView = view.findViewById(R.id.hair_color)
        val birthYear: TextView = view.findViewById(R.id.birth_year)
        val gender: TextView = view.findViewById(R.id.gender)
        val numberFilms: TextView = view.findViewById(R.id.number_of_films)
        val numberSpecies: TextView = view.findViewById(R.id.number_of_species)
        val numberStarships: TextView = view.findViewById(R.id.number_of_starships)

        name.text = listCharacter[position].name
        heightCharacter.text = listCharacter[position].height
        mass.text = listCharacter[position].mass
        hairColor.text = listCharacter[position].hair_color
        birthYear.text = listCharacter[position].birth_year
        gender.text = listCharacter[position].gender
        numberFilms.text = "${listCharacter[position].films?.size}"
        numberSpecies.text = "${listCharacter[position].species?.size}"
        numberStarships.text = "${listCharacter[position].starships?.size}"

        return view

    }
}