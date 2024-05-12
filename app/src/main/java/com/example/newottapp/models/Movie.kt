package com.example.newottapp.models

import com.google.gson.annotations.SerializedName

class Movie(@SerializedName("name")val name:String,@SerializedName("poster-image") val posterimage: String)