package com.schools.nycschools.di

import com.schools.nycschools.data.repository.SchoolsRepositoryImpl
import com.schools.nycschools.domain.repository.SchoolsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Repository module
 *
 * @constructor Create empty Repository module
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    internal abstract fun bindSchoolsRepository(repository: SchoolsRepositoryImpl): SchoolsRepository
}
