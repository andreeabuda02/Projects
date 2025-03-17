package com.example.rickandmortyappcologviu.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyappcologviu.R
import com.example.rickandmortyappcologviu.models.Character

class CharacterListAdapter(
    private var characters: List<Character> = emptyList(),
    private val onViewDetails: (Character) -> Unit,
    private val onChangeBackground: (Int) -> Unit
) : RecyclerView.Adapter<CharacterListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_list_item, parent, false)
        return CharacterListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character, position, onViewDetails, onChangeBackground)
    }

    fun updateList(newCharacters: List<Character>) {
        characters = newCharacters
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = characters.size

    fun getCharacterAt(position: Int): Character? {
        return if (position in characters.indices) characters[position] else null
    }



}
