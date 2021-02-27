package com.example.androiddevchallenge.data.model

import androidx.annotation.DrawableRes

data class Puppy(
    val name: String,
    val breed: String,
    val origin: String,
    val age: String,
    val gender: String,
    val height: Int,
    val weight: Float,
    val ownerName: String,
    val location: String,
    val details: String,
    @DrawableRes val photo: Int,
)