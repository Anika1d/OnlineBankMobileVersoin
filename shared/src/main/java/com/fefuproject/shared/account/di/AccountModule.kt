package com.fefuproject.shared.account.di

import com.fefuproject.shared.account.data.api.AccountApi
import com.fefuproject.shared.account.data.repository.AccountRepositoryApiImpl
import com.fefuproject.shared.account.data.repository.AccountRepositoryFakeImpl
import com.fefuproject.shared.account.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AccountModule {
    @Binds
    fun bindAccountsRepository(repository: AccountRepositoryApiImpl): AccountRepository
}


@Module
@InstallIn(SingletonComponent::class)
object AccountApiModule {
    @Singleton
    @Provides
    fun provideApiModule(retrofit: Retrofit): AccountApi = retrofit.create(AccountApi::class.java)
}