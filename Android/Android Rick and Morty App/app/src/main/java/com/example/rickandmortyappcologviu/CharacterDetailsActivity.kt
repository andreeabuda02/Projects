package com.example.rickandmortyappcologviu

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.rickandmortyappcologviu.models.Character

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var imageViewCharacter: ImageView
    private lateinit var textViewName: TextView
    private lateinit var textViewStatus: TextView
    private lateinit var textViewGender: TextView
    private lateinit var textViewSpecies: TextView
    private lateinit var textViewType: TextView
    private lateinit var textViewLocation: TextView
    private lateinit var accessCounterTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        val character = intent.getSerializableExtra("character") as Character

        initializeViews()

        accessCounterTextView.text = "Page views: ${character.detailsCount}"

        character.detailsCount++

        //cerinta i: afisarea completa a detaliilor
        Glide.with(this).load(character.image).into(imageViewCharacter)
        bindCharacterDetails(character)
    }

    private fun initializeViews() {
        imageViewCharacter = findViewById(R.id.imageViewCharacterDetails)
        textViewName = findViewById(R.id.textViewCharacterName)
        textViewStatus = findViewById(R.id.textViewCharacterStatus)
        textViewSpecies = findViewById(R.id.textViewCharacterSpecies)
        textViewGender = findViewById(R.id.textViewCharacterGender)
        textViewType = findViewById(R.id.textViewCharacterType)
        textViewLocation = findViewById(R.id.textViewCharacterLocation)
        accessCounterTextView = findViewById(R.id.accessCounterTextView)
    }

    private fun bindCharacterDetails(character: Character) {
        textViewName.text = "Name: ${character.name}"
        textViewStatus.text = "Status: ${character.status}"
        textViewGender.text = "Gender ${character.gender}"
        textViewSpecies.text = "Species: ${character.species}"
        textViewType.text = "Type: ${
            if (character.type.isNullOrBlank()) "N/A" else character.type
        }"
        textViewLocation.text = "Location: ${character.location.name}"

        Glide.with(this)
            .load(character.image)
            .into(imageViewCharacter)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}
