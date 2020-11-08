package com.example.wishlist.di

import android.content.Context
import androidx.room.Room
import com.example.wishlist.data.AppDatabase
import com.example.wishlist.data.Dao
import com.example.wishlist.data.Repository
import com.example.wishlist.data.RepositoryImpl
import com.example.wishlist.view.list.WishesViewModel
import com.example.wishlist.view.editor.EditorViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { WishesViewModel(get()) }
    viewModel { EditorViewModel(get()) }

    single { provideRepository(get()) }
    single { provideDao(get()) }
    single { provideAppDatabase(get()) }
}

private fun provideRepository(dao: Dao): Repository {
    return RepositoryImpl(dao)
}

private fun provideDao(db: AppDatabase): Dao {
    return db.dao()
}

private fun provideAppDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "app-db").build()
}