package com.app.omnipro_test_rm.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.omnipro_test_rm.data.local.dao.CharacterDao
import com.app.omnipro_test_rm.data.local.entities.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}