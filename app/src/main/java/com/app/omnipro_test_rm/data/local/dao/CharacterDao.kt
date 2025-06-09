package com.app.omnipro_test_rm.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.app.omnipro_test_rm.data.local.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters ORDER BY name ASC")
    fun pagingSource(): PagingSource<Int, CharacterEntity>
    
    @Query("SELECT * FROM characters WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoriteCharacters(): Flow<List<CharacterEntity>>
    
    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: String): CharacterEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)
    
    @Update
    suspend fun updateCharacter(character: CharacterEntity)
    
    @Query("DELETE FROM characters")
    suspend fun clearAll()
    
    @Query("UPDATE characters SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean)
}