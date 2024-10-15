package com.wguproject.videogamebacklog

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.wguproject.videogamebacklog.data.GameDatabase
import com.wguproject.videogamebacklog.ui.screens.search.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
@ExperimentalCoroutinesApi
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val testDatabase = Room.inMemoryDatabaseBuilder(context, GameDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        Graph.database = testDatabase
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel()

    }

    @Test
    fun `onSearchTitleChanged updates searchTitle in uiState`() = runBlocking {

        val newTitle = "Test Game"

        viewModel.onSearchTitleChanged(newTitle)

        assertEquals(newTitle, viewModel.uiState.value.searchTitle)

    }

    @Test
    fun `convertUnixTimestamp returns correct formatted date`() {
        val timestamp = 1609489201L // 2021-01-01 00:00:00 UTC

        val result = viewModel.convertUnixTimestamp(timestamp)

        assertEquals("01/01/2021", result)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
        Graph.database.close()
    }
}

