package com.app.omnipro_test_rm.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.app.omnipro_test_rm.data.local.dao.CharacterDao
import com.app.omnipro_test_rm.data.local.entities.CharacterEntity
import com.app.omnipro_test_rm.data.mappers.toEntity
import com.app.omnipro_test_rm.data.remote.GraphQLDataSource

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val remoteDataSource: GraphQLDataSource,
    private val localDataSource: CharacterDao
) : RemoteMediator<Int, CharacterEntity>() {
    
    private var currentPage = 1
    
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> currentPage + 1
            }
            
            val result = remoteDataSource.getCharacters(page)
            
            result.fold(
                onSuccess = { charactersPage ->
                    if (loadType == LoadType.REFRESH) {
                        localDataSource.clearAll()
                    }
                    
                    val entities = charactersPage.characterRickAndMorties.map { it.toEntity() }
                    localDataSource.insertCharacters(entities)
                    currentPage = page
                    
                    MediatorResult.Success(
                        endOfPaginationReached = !charactersPage.hasNextPage
                    )
                },
                onFailure = { exception ->
                    MediatorResult.Error(exception)
                }
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}