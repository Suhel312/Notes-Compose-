package com.suhel.noteapp.di

import android.content.Context
import com.suhel.noteapp.data.local.NoteDao
import com.suhel.noteapp.data.local.NoteDatabase
import com.suhel.noteapp.data.repo.NoteRepositoryImpl
import com.suhel.noteapp.domain.repo.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase{
        return NoteDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao = noteDatabase.noteDao()

    @Provides
    @Singleton
    fun provideNoteRepository(impl: NoteRepositoryImpl) : NoteRepository = impl
}