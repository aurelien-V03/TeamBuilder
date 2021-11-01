package fr.miage.teambuilder.models.dao

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "database-name"
        ).build()
    }

    @Provides
    fun provideSportifDao(database: AppDatabase): SportifDao {
        return  database.sportifDao()
    }
    @Provides
    fun provideEquipeDao(database: AppDatabase): EquipeDao {
        return  database.equipeDao()
    }
    @Provides
    fun provideClubDao(database: AppDatabase): ClubDao {
        return  database.clubDao()
    }
    @Provides
    fun provideMatchDao(database: AppDatabase): MatchDao {
        return  database.matchDao()
    }



}