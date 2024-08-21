package com.emrekizil.movieapp.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.emrekizil.movieapp.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private val viewModel: ChatViewModel by viewModels()
    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.setContent {
            MaterialTheme {
                Column {
                    ChatColumn(modifier = Modifier.weight(1f))
                    SuggestionButton()
                    MessageInput()
                }

            }
        }
    }


    @Composable
    fun ChatColumn(modifier: Modifier) {
        val chatUiState by viewModel.chatUiState.collectAsState()
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(chatUiState) { item ->
                when (item.role) {
                    ChatRole.AI -> GeminiText(text = item.text)
                    ChatRole.USER -> UserText(text = item.text)
                }
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MessageInput() {
        var message by remember { mutableStateOf("") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (message.isNotBlank()) {
                    viewModel.sendMessage(message)
                    message = ""
                }
            }) {
                Text("Send")
            }
        }
    }

    @Composable
    fun SuggestionButton() {
        val isVisible = remember { mutableStateOf(true) }
        if (isVisible.value) {
            Button(onClick = {
                viewModel.generateModel()
                isVisible.value = false
            }, modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = "Suggest movies like my favorites")
            }
        }
    }

    @Composable
    fun GeminiText(text: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Red.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(text = text, style = TextStyle(color = Color.Red))
            }
        }
    }

    @Composable
    fun UserText(text: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Green.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(text = text, style = TextStyle(color = Color.Green))
            }
        }
    }
}