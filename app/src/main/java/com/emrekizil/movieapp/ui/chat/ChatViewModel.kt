package com.emrekizil.movieapp.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emrekizil.movieapp.domain.GetFavoriteMovieUseCase
import com.google.ai.client.generativeai.GenerativeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
    private val generativeModel: GenerativeModel
) : ViewModel() {

    private val _chatUiState = MutableStateFlow<List<ChatUiState>>(emptyList())
    val chatUiState = _chatUiState.asStateFlow()


    fun generateModel() {
        viewModelScope.launch {
            _chatUiState.update {
                it + ChatUiState("Suggest movies like my favorites", ChatRole.USER)
            }
            val movies = getFavoriteMovies()
            val moviesString = movies.joinToString(", ")
            val prompt =
                "Give me suggestions about movie title  like $moviesString in a list. Just contain movie title"
            val response = generativeModel.generateContent(prompt)
            _chatUiState.update {
                it + ChatUiState(response.text.toString(), ChatRole.AI)
            }
        }
    }


    fun sendMessage(message: String) {
        viewModelScope.launch {
            _chatUiState.update {
                it + ChatUiState(message, ChatRole.USER)
            }
            val response = generativeModel.generateContent(message)
            _chatUiState.update {
                it + ChatUiState(response.text.toString(), ChatRole.AI)
            }
        }
    }

    suspend fun getFavoriteMovies(): List<String> {
        return getFavoriteMovieUseCase().first().map {
            it.title
        }
    }


}

data class ChatUiState(
    val text: String,
    val role: ChatRole
)

enum class ChatRole {
    AI,
    USER
}