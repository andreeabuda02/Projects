package com.example.rickandmortyappcologviu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyappcologviu.adapters.CharacterListAdapter
import com.example.rickandmortyappcologviu.models.Characters
import com.example.rickandmortyappcologviu.rest_api.RickAndMortyAPI
import com.example.rickandmortyappcologviu.util.LocalModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacterListAdapter

    private val api = RickAndMortyAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        //cerinta e: numar de accesari ale paginii
        LocalModel.characterListPageCount++
        val accessCounterTextView: TextView = findViewById(R.id.accessCounterTextView)
        accessCounterTextView.text = "Page views: ${LocalModel.characterListPageCount}"

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //cerinta g: lista structurata pe o coloana
        recyclerView = findViewById(R.id.recyclerViewCharacters)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //cerinta i: context menu pentru detalii si background
        adapter = CharacterListAdapter(
            onViewDetails = { character -> openCharacterDetails(character) },
            onChangeBackground = { position -> changeCharacterBackground(position) }
        )

        recyclerView.adapter = adapter

        toolbar.post {
            invalidateOptionsMenu()
        }

        //cerinta h: optinerea datelor din api
        fetchCharacters(null)
    }

    override fun onResume() {
        super.onResume()
        val accessCounterTextView: TextView = findViewById(R.id.accessCounterTextView)
        accessCounterTextView.text = "Page views: ${LocalModel.characterListPageCount}"
        LocalModel.characterListPageCount++

    }

    //cerinta d: filtrarea pe categorii
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.character_filter_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val allMenuItem = menu?.findItem(R.id.filter_all)
        allMenuItem?.isChecked = true
        return super.onPrepareOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isChecked = true

        return when (item.itemId) {
            R.id.menu_sign_out -> {
                showSignOutDialog()
                true
            }

            R.id.filter_all -> {
                fetchCharacters(null)
                true
            }

            R.id.filter_human -> {
                fetchCharacters("Human")
                true
            }

            R.id.filter_alien -> {
                fetchCharacters("Alien")
                true
            }

            R.id.filter_robot -> {
                fetchCharacters("Robot")
                true
            }

            R.id.filter_animal -> {
                fetchCharacters("Animal")
                true
            }

            R.id.filter_disease -> {
                fetchCharacters("Disease")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    //cerinta f: buton de sign out
    private fun showSignOutDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.options_menu))
            .setMessage(getString(R.string.menu_sign_out_confirmation))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
            .show()
    }


    private fun fetchCharacters(species: String?) {
        api.getCharacters(species).enqueue(object : Callback<Characters> {
            override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                if (response.isSuccessful) {
                    val characterList = response.body()?.results ?: emptyList()
                    adapter.updateList(characterList)
                } else {
                    Toast.makeText(
                        this@CharacterListActivity,
                        getString(R.string.error_character_not_found),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Characters>, t: Throwable) {
                Toast.makeText(
                    this@CharacterListActivity,
                    getString(R.string.error_general, t.message),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun openCharacterDetails(character: com.example.rickandmortyappcologviu.models.Character) {
        character.detailsCount++

        val intent = Intent(this, CharacterDetailsActivity::class.java)
        intent.putExtra("character", character)
        startActivity(intent)
    }


    private fun changeCharacterBackground(position: Int) {
        val character = adapter.getCharacterAt(position)

        character?.hasCustomBackground = true

        val backgroundResource = when (character?.status) {
            "Dead" -> R.drawable.background_dead
            "Alive" -> R.drawable.background_alive
            else -> R.drawable.character_item_background
        }

        adapter.notifyItemChanged(position)

        recyclerView.findViewHolderForAdapterPosition(position)?.itemView?.setBackgroundResource(
            backgroundResource
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

}
