package com.example.rickandmortyappcologviu.adapters

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyappcologviu.R
import com.example.rickandmortyappcologviu.models.Character

class CharacterListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageViewCharacter: ImageView = itemView.findViewById(R.id.imageViewCharacter)
    private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
    private val textViewStatus: TextView = itemView.findViewById(R.id.textViewStatus)
    private val textViewGender: TextView = itemView.findViewById(R.id.textViewGender)
    private val optionsMenu: ImageView = itemView.findViewById(R.id.optionsMenu)

    fun bind(
        character: Character,
        position: Int,
        onViewDetails: (Character) -> Unit,
        onChangeBackground: (Int) -> Unit
    ) {
        textViewName.text = character.name
        textViewStatus.text = "Status: ${character.status}"
        textViewGender.text = "Gender: ${character.gender}"

        //cerinta g: imaginea personajului
        Glide.with(itemView.context)
            .load(character.image)
            .into(imageViewCharacter)

        //cerinta i: schimbare background
        val backgroundResource = if (character.hasCustomBackground) {
            when (character.status) {
                "Dead" -> R.drawable.background_dead
                "Alive" -> R.drawable.background_alive
                else -> R.drawable.character_item_background
            }
        } else {
            R.drawable.character_item_background
        }

        itemView.setBackgroundResource(backgroundResource)

        //cerinta i: meniu contextual
        optionsMenu.setOnClickListener {
            showPopupMenu(character, position, onViewDetails, onChangeBackground)
        }
    }

    private fun showPopupMenu(
        character: Character,
        position: Int,
        onViewDetails: (Character) -> Unit,
        onChangeBackground: (Int) -> Unit
    ) {
        val popupMenu = PopupMenu(itemView.context, optionsMenu)
        popupMenu.menuInflater.inflate(R.menu.character_context_menu, popupMenu.menu)

        try {
            val field = PopupMenu::class.java.getDeclaredField("mPopup")
            field.isAccessible = true
            val menuPopupHelper = field.get(popupMenu)
            val setForceShowIcon = menuPopupHelper.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            setForceShowIcon.invoke(menuPopupHelper, true)

            val backgroundField = menuPopupHelper.javaClass.getDeclaredField("mPopupBackground")
            val backgroundColor = ContextCompat.getColor(itemView.context, R.color.white)

            backgroundField.isAccessible = true
            backgroundField.set(menuPopupHelper, ColorDrawable(backgroundColor))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_view_details -> {
                    onViewDetails(character)
                    true
                }
                R.id.menu_change_background -> {
                    onChangeBackground(position)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}
