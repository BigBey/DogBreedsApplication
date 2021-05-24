package ru.android.dogbreedsapplication.favorite_dogs_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(_root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage::class), version = DB_VERSION)
abstract class FavoriteDogImagesDatabase : RoomDatabase(){
    abstract fun favoriteDogImageDao(): FavoriteDogImageDao

    companion object{
        @Volatile
        private var databaseInstance: FavoriteDogImagesDatabase? = null

        fun getDatabaseIstance(mContext: Context): FavoriteDogImagesDatabase =
            databaseInstance ?: synchronized(this) {
                databaseInstance ?: buildDatabaseInstance(mContext).also {
                    databaseInstance = it
                }

                Log.d(TAG,"Мы плучили Instance")
            }

        private fun buildDatabaseInstance(mContext: Context) {
            Room.databaseBuilder(mContext, FavoriteDogImagesDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
            Log.d(TAG,"Мы создали Instance")
        }
    }
}

const val TAG = "Database"
const val DB_VERSION = 1
const val DB_NAME = "FavoriteDogImages.db"