package br.com.vitorcesario.githubsearch.model

import com.google.gson.annotations.SerializedName

data class Usuario(
        @SerializedName("name") val nome:String,
        @SerializedName("avatar_url") val avatar_Url: String)