package com.littlelemon.littlelemonmenu

import android.os.AsyncTask
import androidx.compose.runtime.MutableState
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecyclerPage(dbHelper: MyDbHelper) {
    val dataListState = remember { mutableStateOf(emptyList<Book>()) }
    val isLoadingState = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        fetchDataFromDatabase(dbHelper, dataListState, isLoadingState)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Data List") },
                actions = {
                    IconButton(onClick = { fetchData(dataListState, isLoadingState, dbHelper) }) {
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
suspend fun fetchDataFromDatabase(
    dbHelper: MyDbHelper,
    dataListState: MutableState<List<Book>>,
    isLoadingState: MutableState<Boolean>
) {
    withContext(Dispatchers.IO) {
        val booksFromDB = dbHelper.getAllBooks()
        if (!booksFromDB.isNullOrEmpty()) {
            dataListState.value = booksFromDB
        } else {
            Log.d("MainActivityF", "No books found in the database")
        }
        isLoadingState.value = false
    }
}
fun fetchData(
    dataListState: MutableState<List<Book>>,
    isLoadingState: MutableState<Boolean>,
    dbHelper: MyDbHelper
) {
    isLoadingState.value = true
    FetchDataTask(dataListState, isLoadingState, dbHelper).execute()
}

class FetchDataTask(
    private val dataListState: MutableState<List<Book>>,
    private val isLoadingState: MutableState<Boolean>,
    private val dbHelper: MyDbHelper
) : AsyncTask<Void, Void, List<Book>?>() {

    override fun doInBackground(vararg params: Void?): List<Book>? {
        val api: LoginService = Retrofit.Builder()
            .baseUrl("http://192.168.1.7:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginService::class.java)

        return try {
            val response = api.getAllBooks().execute()
            if (response.isSuccessful) {
                val books = response.body()
                books?.let {
                    val bookEntities = it.map { book ->
                        Book(book.name, book.fields)
                    }
                    dbHelper.deleteAllBooks()
                    for (book in bookEntities) {
                        dbHelper.insertBook(book)
                    }
                    return dbHelper.getAllBooks()
                }
            } else {
                Log.d("MainActivityF", "Request failed: ${response.code()}")
            }
            null
        } catch (e: Exception) {
            Log.e("MainActivityF", "Error getting books", e)
            null
        }
    }

    override fun onPostExecute(result: List<Book>?) {
        result?.let {
            if (it.isNotEmpty()) {
                dataListState.value = it
            } else {
                Log.d("MainActivityF", "No books found in the database")
            }
        }
        isLoadingState.value = false
    }
}
