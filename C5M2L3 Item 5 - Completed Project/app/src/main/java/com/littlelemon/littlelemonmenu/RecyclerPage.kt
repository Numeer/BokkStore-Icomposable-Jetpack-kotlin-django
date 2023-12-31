package com.littlelemon.littlelemonmenu

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecyclerPage(appDatabase: AppDatabase) {
    val dataListState = remember { mutableStateOf(emptyList<Book>()) }
    val isLoadingState = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Data List") },
                actions = {
                    IconButton(onClick = { fetchData(dataListState, isLoadingState, appDatabase) }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }
            )
        }
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            if (isLoadingState.value) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn {
                    Log.d("Valueee", dataListState.value.size.toString())
                    items(dataListState.value.size) { index ->
                        val book = dataListState.value[index]
                        ListItem(text = { Text(text = book.toString()) })
                    }
                }
            }
        }
    }
}

fun fetchData(
    dataListState: MutableState<List<Book>>,
    isLoadingState: MutableState<Boolean>,
    database: AppDatabase
) {
    isLoadingState.value = true
    val api: LoginService = Retrofit.Builder()
        .baseUrl("http://192.168.1.7:8000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LoginService::class.java)

    api.getAllBooks().enqueue(object : Callback<List<Book>> {
        override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
            if (response.isSuccessful) {
                val books = response.body()
                if (books != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val bookEntities = books.map { book ->
                            Book(book.name, book.fields, )
                        }
                        database.bookDao().insertBooks(bookEntities)
                        val booksFromDB = database.bookDao().getAllBooks()
                        if (!booksFromDB.isNullOrEmpty()) {
                            dataListState.value = booksFromDB
                            isLoadingState.value = false
                        } else {
                            Log.d("MainActivityF", "No books found in the database")
                            isLoadingState.value = false
                        }
                    }
//                    dataListState.value = books
                }
                else {
                    Log.d("MainActivityF", "No books found")
                }
            } else {
                Log.d("MainActivityF", "Request failed: ${response.code()}")
            }
            isLoadingState.value = false // Set isLoading to false after fetching the data
        }

        override fun onFailure(call: Call<List<Book>>, t: Throwable) {
            Log.e("MainActivityF", "Error getting books", t)
            isLoadingState.value = false // Set isLoading to false in case of failure
        }
    })
}





