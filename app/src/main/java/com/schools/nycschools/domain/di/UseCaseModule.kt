package com.schools.nycschools.domain.di

import com.schools.nycschools.domain.usecase.SchoolsUseCase
import com.schools.nycschools.domain.usecase.SchoolsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Use case module
 *
 * @constructor Create empty Use case module
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    internal abstract fun bindSchoolsUseCase(useCaseImpl: SchoolsUseCaseImpl): SchoolsUseCase
}
