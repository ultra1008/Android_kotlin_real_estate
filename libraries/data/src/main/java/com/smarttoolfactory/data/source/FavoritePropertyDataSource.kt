package com.smarttoolfactory.data.source

import com.smarttoolfactory.data.db.dao.FavoritesCoroutinesDao
import com.smarttoolfactory.data.model.local.InteractivePropertyEntity
import com.smarttoolfactory.data.model.local.PropertyWithFavorites
import com.smarttoolfactory.data.model.local.UserFavoriteJunction
import javax.inject.Inject

interface FavoritePropertyDataSource {

    suspend fun insertOrUpdateFavorite(
        userId: Long,
        entity: InteractivePropertyEntity,
        viewCount: Int,
        liked: Boolean
    )

    suspend fun getStatsForAll(): List<UserFavoriteJunction>

    /**
     * Retrieves favorite and view count stats for property with [propertyId] only for the user
     * with [userId]
     */
    suspend fun getPropertyStats(
        userId: Long,
        propertyId: Int
    ): UserFavoriteJunction?

    /**
     * Retrieves favorite and view count stats for every property for the user with [userId]
     */
    suspend fun getStatsForProperties(userId: Long): List<UserFavoriteJunction>

    suspend fun getPropertiesWithFavorites(userId: Long): List<PropertyWithFavorites>

    suspend fun deleteFavoriteEntity(entity: InteractivePropertyEntity)
    suspend fun deleteFavoriteEntityForUser(userId: Long, propertyId: Int)
}

class FavoritePropertyDataSourceImpl @Inject constructor(
    private val favoritesDao: FavoritesCoroutinesDao
) : FavoritePropertyDataSource {

    override suspend fun insertOrUpdateFavorite(
        userId: Long,
        entity: InteractivePropertyEntity,
        viewCount: Int,
        liked: Boolean
    ) {
        favoritesDao.insertUserFavorite(userId, entity, viewCount, liked)
    }

    override suspend fun getStatsForAll(): List<UserFavoriteJunction> {
        return favoritesDao.getUserFavoriteJunctionForAll()
    }

    override suspend fun getPropertyStats(
        userId: Long,
        propertyId: Int
    ): UserFavoriteJunction? {
        return favoritesDao.getUserFavoriteJunction(userId, propertyId)
    }

    override suspend fun getStatsForProperties(userId: Long): List<UserFavoriteJunction> {
        return favoritesDao.getUserFavoriteJunction(userId)
    }

    override suspend fun getPropertiesWithFavorites(userId: Long): List<PropertyWithFavorites> {
        return favoritesDao.getPropertiesWithFavorites(userId = userId)
    }

    override suspend fun deleteFavoriteEntity(entity: InteractivePropertyEntity) {
        favoritesDao.delete(entity)
    }

    override suspend fun deleteFavoriteEntityForUser(userId: Long, propertyId: Int) {
        favoritesDao.deleteFavoriteEntityForUser(userId, propertyId)
    }
}
