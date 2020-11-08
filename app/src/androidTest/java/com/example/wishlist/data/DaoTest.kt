package com.example.wishlist.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.wishlist.entity.Wish
import org.junit.runner.RunWith
import kotlinx.coroutines.runBlocking
import org.junit.*

@RunWith(AndroidJUnit4::class)
class DaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var appDatabase: AppDatabase
    private lateinit var dao: Dao

    @Before
    fun initDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        ).build()
        dao = appDatabase.dao()
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun getAllWishesReturnsEmptyList() {
        runBlocking {
            val list = dao.getAllWishes()
            Assert.assertEquals(list, emptyList<Wish>())
        }
    }
}