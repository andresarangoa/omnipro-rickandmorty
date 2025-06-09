package com.app.omnipro_test_rm.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.app.omnipro_test_rm.data.local.dao.CharacterDao
import com.app.omnipro_test_rm.data.mappers.toCharacter
import com.app.omnipro_test_rm.data.mappers.toEntity
import com.app.omnipro_test_rm.data.paging.CharacterRemoteMediator
import com.app.omnipro_test_rm.data.remote.GraphQLDataSource
import com.app.omnipro_test_rm.domain.repository.ICharacterRepository
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepository(
    private val remoteDataSource: GraphQLDataSource,
    private val localDataSource: CharacterDao
) : ICharacterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharactersPaged(): Flow<PagingData<CharacterRickAndMorty>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = CharacterRemoteMediator(remoteDataSource, localDataSource),
            pagingSourceFactory = { localDataSource.pagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toCharacter() }
        }
    }

    override suspend fun getCharacterById(id: String): Result<CharacterRickAndMorty> {
        val localCharacter = localDataSource.getCharacterById(id)
        return if (localCharacter != null) {
            Result.success(localCharacter.toCharacter())
        } else {
            remoteDataSource.getCharacter(id).fold(
                onSuccess = { networkCharacter ->
                    val entity = networkCharacter.toEntity()
                    localDataSource.insertCharacter(entity)
                    Result.success(networkCharacter)
                },
                onFailure = { exception ->
                    Result.failure(exception)
                }
            )
        }
    }

    override suspend fun toggleFavorite(id: String) {
        val character = localDataSource.getCharacterById(id)
        character?.let {
            localDataSource.updateFavoriteStatus(id, !it.isFavorite)
        }
    }

    override fun getFavoriteCharacters(): Flow<List<CharacterRickAndMorty>> {
        return localDataSource.getFavoriteCharacters().map { entities ->
            entities.map { it.toCharacter() }
        }
    }
}