package com.example.rickandmortyappcologviu.rest_api

import com.example.rickandmortyappcologviu.models.Characters
import com.example.rickandmortyappcologviu.models.data_api.AuthResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyAPI {
    @GET("character/{id}")
    fun getCharacterById(@Path("id") id: Int): Call<AuthResponse>

    @GET("character")
    fun getCharacters(@Query("species") species: String? = null): Call<Characters>

    companion object {
        fun create(): RickAndMortyAPI {
            val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .build()
            return retrofit.create(RickAndMortyAPI::class.java)
        }
    }
}
