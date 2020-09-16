package com.smarttoolfactory.data.di

import android.app.Application
import androidx.room.Room
import com.smarttoolfactory.data.constant.DATABASE_NAME
import com.smarttoolfactory.data.db.MIGRATION_1_2
import com.smarttoolfactory.data.db.MIGRATION_2_3
import com.smarttoolfactory.data.db.MIGRATION_3_4
import com.smarttoolfactory.data.db.PropertyDatabase
import com.smarttoolfactory.data.db.dao.FavoritesDao
import com.smarttoolfactory.data.db.dao.PagedPropertyDao
import com.smarttoolfactory.data.db.dao.PropertyDaoCoroutines
import com.smarttoolfactory.data.db.dao.PropertyDaoRxJava3
import com.smarttoolfactory.data.db.dao.SortOrderDaoCoroutines
import com.smarttoolfactory.data.db.dao.SortOrderDaoRxJava3
import com.smarttoolfactory.data.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): PropertyDatabase {
        return Room.databaseBuilder(
            application,
            PropertyDatabase::class.java,
            DATABASE_NAME
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
            .build()
    }

    @Singleton
    @Provides
    fun providePropertyDaoCoroutines(appDatabase: PropertyDatabase): PropertyDaoCoroutines =
        appDatabase.propertyDaoCoroutines()

    @Singleton
    @Provides
    fun providePropertyDaoRxJava3(appDatabase: PropertyDatabase): PropertyDaoRxJava3 =
        appDatabase.propertyDaoRxJava()

    @Singleton
    @Provides
    fun providePropertySortDaoCoroutines(appDatabase: PropertyDatabase): SortOrderDaoCoroutines =
        appDatabase.propertySortDaoCoroutines()

    @Singleton
    @Provides
    fun provideSortOrderDaoRxJava3(appDatabase: PropertyDatabase): SortOrderDaoRxJava3 =
        appDatabase.propertySortDaoRxJava()

    @Singleton
    @Provides
    fun providePagedPropertyDao(appDatabase: PropertyDatabase): PagedPropertyDao =
        appDatabase.pagedPropertyDao()

    @Provides
    fun provideUserDao(appDatabase: PropertyDatabase): UserDao =
        appDatabase.userDao()

    @Provides
    fun provideFavoritesDao(appDatabase: PropertyDatabase): FavoritesDao =
        appDatabase.favoritesDao()
}
