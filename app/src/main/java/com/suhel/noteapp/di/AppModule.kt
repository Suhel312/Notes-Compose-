package com.suhel.noteapp.di

import android.content.Context
import androidx.core.util.Consumer
import com.suhel.noteapp.data.local.NoteDatabase
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
    fun provideNoteDao(noteDatabase: NoteDatabase) = noteDatabase.noteDao()
}