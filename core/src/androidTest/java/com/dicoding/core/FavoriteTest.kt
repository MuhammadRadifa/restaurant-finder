package com.dicoding.core

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.dicoding.core.data.source.local.room.FavoriteDao
import com.dicoding.core.data.source.local.room.FavoriteEntity
import com.dicoding.core.data.source.local.room.RestaurantDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
@SmallTest
class FavoriteTest {

    private lateinit var database: RestaurantDatabase
    private lateinit var favoriteDao: FavoriteDao

    @Before
    fun setUpDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RestaurantDatabase::class.java
        ).allowMainThreadQueries().build()

        favoriteDao = database.favoriteDao()
    }

    @Test
    fun insertFavorite() = runBlocking {
        val favorite = FavoriteEntity("1", "1", "medan", "melting put", "5.0", "description")
        favoriteDao.addFavorite(favorite)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            favoriteDao.getAllFavorites().collect{
                assertThat(it.isNotEmpty(), `is`(true))
                latch.countDown()
            }
        }

        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun deleteFavorite() = runBlocking {
        val favorite = FavoriteEntity("1", "1", "medan", "melting put", "5.0", "description")
        favoriteDao.addFavorite(favorite)
        favoriteDao.deleteFavoriteByName(favorite)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            favoriteDao.getAllFavorites().collect{
                assertThat(it.isEmpty(), `is`(true))
                latch.countDown()
            }
        }

        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun checkFavoriteById() = runBlocking {
        val favorite = FavoriteEntity("1", "1", "medan", "melting put", "5.0", "description")
        favoriteDao.addFavorite(favorite)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            favoriteDao.getFavoriteById(favorite.id).collect{
                assertThat(it.id, `is`(favorite.id))
                latch.countDown()
            }
        }

        latch.await()
        job.cancelAndJoin()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

}