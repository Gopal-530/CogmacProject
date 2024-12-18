package com.swis.cogmacproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class BookViewModel(private val repository: BookRepository) : ViewModel() {

    val allBooks: Flow<List<Book>> = repository.allBooks

    fun insert(book: Book) = viewModelScope.launch { repository.insert(book) }

    fun update(book: Book) = viewModelScope.launch { repository.update(book) }

    fun delete(book: Book) = viewModelScope.launch { repository.delete(book) }

    fun searchBooks(query: String): Flow<List<Book>> = repository.searchBooks(query)
}

class BookViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return BookViewModel(repository) as T
    }
}
