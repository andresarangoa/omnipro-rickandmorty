package com.app.omnipro_test_rm.data.remote

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.app.omnipro_test_rm.data.mappers.toCharacter
import com.app.omnipro_test_rm.domain.models.CharactersPage
import com.app.omnipro_test_rm.graphql.GetCharactersQuery
import com.app.omnipro_test_rm.graphql.GetCharacterQuery
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty

class GraphQLDataSource(private val apolloClient: ApolloClient) {
    
    suspend fun getCharacters(page: Int): Result<CharactersPage> {
        return try {
            val response = apolloClient.query(GetCharactersQuery(page)).execute()
            Log.d("GraphQL", "Response: ${response.data}")
            Log.d("GraphQL", "Errors: ${response.errors}")
            if (response.hasErrors()) {
                Result.failure(Exception(response.errors?.firstOrNull()?.message ?: "Unknown error"))
            } else {
                val data = response.data?.characters
                val characters = data?.results?.mapNotNull { it?.toCharacter() } ?: emptyList()
                val result = CharactersPage(
                    characterRickAndMorties = characters,
                    currentPage = page,
                    totalPages = data?.info?.pages ?: 1,
                    hasNextPage = data?.info?.next != null
                )
                Result.success(result)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getCharacter(id: String): Result<CharacterRickAndMorty> {
        return try {
            val response = apolloClient.query(GetCharacterQuery(id)).execute()
            
            if (response.hasErrors()) {
                Result.failure(Exception(response.errors?.firstOrNull()?.message ?: "Unknown error"))
            } else {
                val character = response.data?.character?.toCharacter()
                if (character != null) {
                    Result.success(character)
                } else {
                    Result.failure(Exception("Character not found"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
