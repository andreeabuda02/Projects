package com.example.rickandmortyappcologviu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.rickandmortyappcologviu.models.data_api.AuthResponse
import com.example.rickandmortyappcologviu.rest_api.RickAndMortyAPI
import com.example.rickandmortyappcologviu.util.LocalModel
import com.example.rickandmortyappcologviu.util.UserInfoSingleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    //cerinta b
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var textViewWrongUsername: TextView
    private lateinit var textViewWrongPassword: TextView
    private lateinit var buttonLogin: Button
    private lateinit var textViewLoginFailed: TextView
    private lateinit var textViewSignInMessage: TextView

    private lateinit var imageViewAnimation: ImageView

    private val handler = Handler(Looper.getMainLooper())
    private val api = RickAndMortyAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //cerinta e: numar de accesari ale paginii
        LocalModel.loginPageCount++
        val accessCounterTextView: TextView = findViewById(R.id.accessCounterTextView)
        accessCounterTextView.text = "Page views: ${LocalModel.loginPageCount}"


        initializeViews()
        setListeners()
    }

    private fun initializeViews() {
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        textViewWrongUsername = findViewById(R.id.textViewWrongUsername)
        textViewWrongPassword = findViewById(R.id.textViewWrongPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewLoginFailed = findViewById(R.id.textViewLoginFailed)
        textViewSignInMessage = findViewById(R.id.textViewSignInMessage)

        imageViewAnimation = findViewById(R.id.imageViewAnimation)

    }

    private fun setListeners() {
        buttonLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        resetGlobalMessages()

        // cerinta b: validare credetiale si autentificare
        val validUsername = checkUsername()
        val validPassword = checkPassword()

        if (validUsername && validPassword) {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            authenticate(username, password)
        }

        val inputText = editTextUsername.text.toString().trim()
        imageViewAnimation.visibility = View.INVISIBLE

        when {
            inputText.contains("Rick") -> showGif(R.drawable.rick_sanchez)
            inputText.contains("Morty") -> showGif(R.drawable.morty_smith)
            else -> showGif(R.drawable.loading1)
        }

    }

    //cerinta c: gif-uri pentru personaje
    private fun showGif(resourceId: Int) {
        imageViewAnimation.visibility = View.VISIBLE
        Glide.with(this)
            .asGif()
            .load(resourceId)
            .into(imageViewAnimation)

        handler.postDelayed({
            imageViewAnimation.visibility = View.INVISIBLE
        }, 2000)
    }

    private fun showTemporaryMessage(textView: TextView, message: String, isError: Boolean, durata: Long, editText: EditText? = null)
    {
        textView.text = message
        textView.visibility = View.VISIBLE

        if (isError) {
            textView.setTextColor(ContextCompat.getColor(this, R.color.red))
            editText?.setBackgroundColor(ContextCompat.getColor(this, R.color.red1))
        } else {
            textView.setTextColor(ContextCompat.getColor(this, R.color.green))
        }

        handler.postDelayed({
            textView.visibility = View.INVISIBLE
            editText?.setBackgroundResource(android.R.drawable.edit_text)
        }, durata)
    }

    private fun checkUsername(): Boolean {
        val username = editTextUsername.text.toString().trim()
        return when {
            username.isEmpty() -> {
                showTemporaryMessage(
                    textViewWrongUsername,
                    getString(R.string.error_username_empty),
                    true,
                    3000,
                    editTextUsername
                )
                false
            }
            username.length < 3 -> {
                showTemporaryMessage(
                    textViewWrongUsername,
                    getString(R.string.error_username_short),
                    true,
                    3000,
                    editTextUsername
                )
                false
            }
            else -> true
        }
    }

    private fun checkPassword(): Boolean {
        val password = editTextPassword.text.toString().trim()
        return when {
            password.isEmpty() -> {
                showTemporaryMessage(
                    textViewWrongPassword,
                    getString(R.string.error_password_empty),
                    true,
                    3000,
                    editTextPassword
                )
                false
            }
            password.length < 3 -> {
                showTemporaryMessage(
                    textViewWrongPassword,
                    getString(R.string.error_password_short),
                    true,
                    3000,
                    editTextPassword
                )
                false
            }
            else -> true
        }
    }

    private fun authenticate(username: String, password: String) {
        val id = password.filter { it.isDigit() }.toIntOrNull()
        if (id == null) {
            showTemporaryMessage(
                textViewLoginFailed,
                getString(R.string.error_invalid_password_format),
                true,
                3000
            )
            return
        }

        api.getCharacterById(id).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val character = response.body()
                    if (character != null && username == character.name && password == "${character.id}${character.status}") {
                        UserInfoSingleton.username = username
                        UserInfoSingleton.password = password
                        showTemporaryMessage(
                            textViewSignInMessage,
                            getString(R.string.success_login),
                            false,
                            3000
                        )
                        handler.postDelayed({
                            val intent =
                                Intent(this@LoginActivity, CharacterListActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, 1000)
                    } else {
                        showTemporaryMessage(
                            textViewLoginFailed,
                            getString(R.string.error_invalid_credentials),
                            true,
                            3000
                        )
                    }
                } else {
                    showTemporaryMessage(
                        textViewLoginFailed,
                        getString(R.string.error_character_not_found),
                        true,
                        3000
                    )
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                showTemporaryMessage(textViewLoginFailed, "Error: ${t.message}", true, 3000)
            }
        })
    }

    private fun resetGlobalMessages() {
        textViewLoginFailed.visibility = View.INVISIBLE
        textViewSignInMessage.visibility = View.INVISIBLE
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

}
