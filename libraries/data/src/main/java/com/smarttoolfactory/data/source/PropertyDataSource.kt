package com.smarttoolfactory.data.source

import com.smarttoolfactory.data.constant.ORDER_BY_NONE
import com.smarttoolfactory.data.model.local.PagedPropertyEntity
import com.smarttoolfactory.data.model.local.PropertyEntity
import com.smarttoolfactory.data.model.remote.PropertyDTO
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface PropertyDataSource

/*
    Coroutines
 */
interface RemotePropertyDataSource : PropertyDataSource {
    suspend fun getPropertyDTOs(orderBy: String = ORDER_BY_NONE): List<PropertyDTO>
    suspend fun getPropertyDTOsWithPagination(
        page: Int,
        orderBy: String = ORDER_BY_NONE
    ): List<PropertyDTO>
}

interface LocalPropertyDataSource : PropertyDataSource {
    suspend fun getPropertyEntities(): List<PropertyEntity>
    suspend fun saveEntities(properties: List<PropertyEntity>): List<Long>
    suspend fun deletePropertyEntities()
    suspend fun saveOrderKey(orderBy: String)
    suspend fun getOrderKey(): String
}

/*
    Pagination + Coroutines
 */
interface LocalPagedPropertyDataSource : PropertyDataSource {
    suspend fun getPropertyEntities(): List<PagedPropertyEntity>
    suspend fun saveEntities(properties: List<PagedPropertyEntity>): List<Long>
    suspend fun deletePropertyEntities()
    suspend fun getPropertyCount(): Int
    suspend fun saveOrderKey(orderBy: String)
    suspend fun getOrderKey(): String
}

/*
    RxJava3
 */
interface RemotePropertyDataSourceRxJava3 : PropertyDataSource {
    fun getPropertyDTOs(orderBy: String = ORDER_BY_NONE): Single<List<PropertyDTO>>
    fun getPropertyDTOsWithPagination(
        page: Int,
        orderBy: String = ORDER_BY_NONE
    ): Single<List<PropertyDTO>>
}

interface LocalPropertyDataSourceRxJava3 : PropertyDataSource {
    fun getPropertyEntities(): Single<List<PropertyEntity>>
    fun saveEntities(properties: List<PropertyEntity>): Completable
    fun deletePropertyEntities(): Completable
    fun saveOrderKey(orderBy: String): Completable
    fun getOrderKey(): Single<String>
}
