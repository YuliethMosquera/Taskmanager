package com.example.taskmanager.ui.screens.drafts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DraftsScreen(
    viewModel: DraftsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val drafts by viewModel.drafts.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Borradores Guardados") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (drafts.isEmpty()) {
                Text(
                    text = "No tienes borradores guardados",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(drafts, key = { it.id }) { draft ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = draft.title,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    if (draft.description.isNotBlank()) {
                                        Text(
                                            text = draft.description,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                                IconButton(onClick = { viewModel.deleteDraft(draft.id) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar Borrador")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}