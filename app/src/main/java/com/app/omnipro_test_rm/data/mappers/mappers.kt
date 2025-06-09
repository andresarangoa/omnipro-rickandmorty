package com.app.omnipro_test_rm.data.mappers

import com.app.omnipro_test_rm.data.local.entities.CharacterEntity
import com.app.omnipro_test_rm.graphql.GetCharacterQuery
import com.app.omnipro_test_rm.graphql.GetCharactersQuery
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty
import com.app.omnipro_test_rm.domain.models.CharacterStatus
import com.app.omnipro_test_rm.domain.models.Episode
import com.app.omnipro_test_rm.domain.models.Location

fun GetCharactersQuery.Result.toCharacter(): CharacterRickAndMorty {
    return CharacterRickAndMorty(
        id = id ?: "",
        name = name ?: "",
        status = CharacterStatus.fromString(status ?: ""),
        species = species ?: "",
        type = type ?: "",
        gender = gender ?: "",
        origin = origin?.let {
            Location(
                id = it.id,
                name = it.name ?: "",
                type = it.type,
                dimension = it.dimension
            )
        },
        location = location?.let {
            Location(
                id = it.id,
                name = it.name ?: "",
                type = it.type,
                dimension = it.dimension
            )
        },
        image = image ?: "",
        episodes = episode.mapNotNull { ep ->
            ep?.let {
                Episode(
                    id = it.id ?: "",
                    name = it.name ?: "",
                    episode = it.episode ?: ""
                )
            }
        } ?: emptyList(),
        created = created ?: "",
        episodeCount = episode.size
    )
}

fun GetCharacterQuery.Character.toCharacter(): CharacterRickAndMorty {
    return CharacterRickAndMorty(
        id = id ?: "",
        name = name ?: "",
        status = CharacterStatus.fromString(status ?: ""),
        species = species ?: "",
        type = type ?: "",
        gender = gender ?: "",
        origin = origin?.let {
            Location(
                id = it.id,
                name = it.name ?: "",
                type = it.type,
                dimension = it.dimension
            )
        },
        location = location?.let {
            Location(
                id = it.id,
                name = it.name ?: "",
                type = it.type,
                dimension = it.dimension
            )
        },
        image = image ?: "",
        episodes = episode.mapNotNull { ep ->
            ep?.let {
                Episode(
                    id = it.id ?: "",
                    name = it.name ?: "",
                    episode = it.episode ?: "",
                    airDate = it.air_date
                )
            }
        },
        created = created ?: "",
        episodeCount = episode.size
    )
}

fun CharacterEntity.toCharacter(): CharacterRickAndMorty {
    return CharacterRickAndMorty(
        id = id,
        name = name,
        status = CharacterStatus.fromString(status),
        species = species,
        type = type,
        gender = gender,
        origin = if (originName != null) {
            Location(originId, originName, originType, originDimension)
        } else null,
        location = if (locationName != null) {
            Location(locationId, locationName, locationType, locationDimension)
        } else null,
        image = image,
        episodeCount = episodeCount,
        episodes = emptyList(), // Episodes loaded separately for detail view
        created = created,
        isFavorite = isFavorite
    )
}

fun CharacterRickAndMorty.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status.name,
        species = species,
        type = type,
        gender = gender,
        originId = origin?.id,
        originName = origin?.name,
        originType = origin?.type,
        originDimension = origin?.dimension,
        locationId = location?.id,
        locationName = location?.name,
        locationType = location?.type,
        locationDimension = location?.dimension,
        image = image,
        created = created,
        episodeCount = episodes.size,
        isFavorite = isFavorite
    )
}