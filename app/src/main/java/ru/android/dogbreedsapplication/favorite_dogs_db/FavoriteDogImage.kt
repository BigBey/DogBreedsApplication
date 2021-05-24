package ru.android.dogbreedsapplication.favorite_dogs_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteDogImage(
    @PrimaryKey val dogImageLink: String,
    @ColumnInfo(name = "breed") val breed: String?
)