package com.swis.cogmacproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swis.cogmacproject.ui.theme.CogmacProjectTheme
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = BookDatabase.getInstance(applicationContext)
        val repository = BookRepository(database.bookDao())
        val factory = BookViewModelFactory(repository)

        setContent {
            val bookViewModel: BookViewModel = viewModel(factory = factory)
            BookApp(bookViewModel)
        }
    }
}

@Composable
fun BookApp(bookViewModel: BookViewModel) {
    val books by bookViewModel.allBooks.collectAsState(initial = emptyList())

    Scaffold(
       // topBar = { TopAppBar(title = { Text("Book Manager") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Handle Add */ }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            BookList(books = books, onDelete = { bookViewModel.delete(it) })
        }
    }
}

@Composable
fun BookList(books: List<Book>, onDelete: (Book) -> Unit) {
    LazyColumn {
        items(books) { book ->
            BookItem(book = book, onDelete = onDelete)
        }
    }
}

@Composable
fun BookItem(book: Book, onDelete: (Book) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Title: ${book.title}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Author: ${book.author}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Published: ${book.publicationDate}", style = MaterialTheme.typography.bodySmall)
            Row {
                Button(onClick = { onDelete(book) }, modifier = Modifier.padding(top = 8.dp)) {
                    Text("Delete")
                }
            }
        }
    }
}