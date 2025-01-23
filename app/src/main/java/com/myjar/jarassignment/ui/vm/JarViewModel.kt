package com.myjar.jarassignment.ui.vm

import androidx.collection.intSetOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myjar.jarassignment.createRetrofit
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.data.repository.JarRepository
import com.myjar.jarassignment.data.repository.JarRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JarViewModel : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    private val _listStringData = MutableStateFlow<List<ComputerItem>>(emptyList())
    val listStringData: StateFlow<List<ComputerItem>>
        get() = _listStringData

    private val repository: JarRepository = JarRepositoryImpl(createRetrofit())

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchResults()
                .collect { items ->
                    _listStringData.value = items
                }
        }
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
        updateFilteredList(newQuery)
    }

    private fun updateFilteredList(query: String) {
         if (query.isBlank())
             _listStringData.value = listStringData.value
        else
             _listStringData.value = listStringData.value.filter { it.name.contains(query, ignoreCase = true) }
    }
}