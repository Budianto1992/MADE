package com.budianto.moviejetpackpro.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.budianto.moviejetpackpro.core.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 3, exportSchema = false)
abstract class DatabaseMovie : RoomDatabase(){

    abstract fun movieDao(): MovieDao

//    companion object{
//        @Volatile
//        private var INSTANCE: DatabaseMovie? = null
//
//        fun getInstance(context: Context): DatabaseMovie {
//            if (INSTANCE == null){
//                synchronized(DatabaseMovie::class.java){
//                    if (INSTANCE == null){
//                        INSTANCE = Room.databaseBuilder(context.applicationContext,
//                            DatabaseMovie::class.java, "Catalogue.db")
//                            .build()
//                    }
//                }
//            }
//            return INSTANCE as DatabaseMovie
//        }
//    }
}