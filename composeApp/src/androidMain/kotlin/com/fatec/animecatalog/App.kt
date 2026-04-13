package com.fatec.animecatalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fatec.animecatalog.model.Anime
import com.fatec.animecatalog.model.Character

@Composable
fun App() {
    MaterialTheme {
        var animes by remember { mutableStateOf<List<Anime>>(emptyList()) }
        var characters by remember { mutableStateOf<List<Character>>(emptyList()) }
        var loading by remember { mutableStateOf(true) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        var searchQuery by remember { mutableStateOf("") }
        var selectedGenre by remember { mutableStateOf("Todos") }

        val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }

        LaunchedEffect(Unit) {
            try {
                errorMessage = null
                animes = AnimeApi.getAnimes()
                characters = CharacterApi.getCharacters()

                animes.forEach { anime ->
                    if (expandedStates[anime.id] == null) {
                        expandedStates[anime.id] = false
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Erro ao buscar dados."
            } finally {
                loading = false
            }
        }

        val genres = remember(animes) {
            listOf("Todos") + animes.map { it.genre }.distinct().sorted()
        }

        val filteredAnimes = animes
            .filter { anime ->
                val matchesSearch = anime.title.contains(searchQuery, ignoreCase = true)
                val matchesGenre = selectedGenre == "Todos" || anime.genre == selectedGenre
                matchesSearch && matchesGenre
            }
            .sortedBy { it.title }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .safeContentPadding()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "Anime Catalog",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Busque, filtre e explore animes com seus personagens.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.80f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = { Text("Buscar anime por nome") },
                    shape = RoundedCornerShape(18.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotBlank()) {
                            FilledTonalIconButton(
                                onClick = { searchQuery = "" }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Limpar busca"
                                )
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors()
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Filtrar por gênero",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    genres.forEach { genre ->
                        val selected = genre == selectedGenre
                        AssistChip(
                            onClick = { selectedGenre = genre },
                            label = { Text(genre) },
                            modifier = Modifier.height(34.dp),
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (selected) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                },
                                labelColor = if (selected) {
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "${filteredAnimes.size} anime(s) encontrado(s)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.62f)
                )

                Spacer(modifier = Modifier.height(18.dp))

                when {
                    loading -> {
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(22.dp),
                            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator()
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("Carregando dados...")
                            }
                        }
                    }

                    errorMessage != null -> {
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Text(
                                text = "Erro: $errorMessage",
                                modifier = Modifier.padding(18.dp),
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }

                    filteredAnimes.isEmpty() -> {
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = "Nenhum anime encontrado",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Tente ajustar a busca ou selecionar outro gênero.",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(filteredAnimes) { anime ->
                                val animeCharacters = characters.filter { it.animeId == anime.id }
                                val isExpanded = expandedStates[anime.id] ?: false

                                AnimeExpandableCard(
                                    anime = anime,
                                    characters = animeCharacters,
                                    isExpanded = isExpanded,
                                    onToggleExpand = {
                                        expandedStates[anime.id] = !isExpanded
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeExpandableCard(
    anime: Anime,
    characters: List<Character>,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)) {
            Text(
                text = anime.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            InfoLine(label = "Gênero", value = anime.genre)
            InfoLine(label = "Estúdio", value = anime.studio)
            InfoLine(label = "Episódios", value = anime.episodes.toString())
            InfoLine(label = "Ano", value = anime.releaseYear.toString())

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpand() },
                shape = RoundedCornerShape(18.dp),
                tonalElevation = 4.dp,
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.65f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Personagens (${characters.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (isExpanded) "Toque para recolher" else "Toque para expandir",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.72f)
                        )
                    }

                    Icon(
                        imageVector = if (isExpanded) {
                            Icons.Default.ExpandLess
                        } else {
                            Icons.Default.ExpandMore
                        },
                        contentDescription = if (isExpanded) "Ocultar personagens" else "Mostrar personagens",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(10.dp))

                if (characters.isEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        tonalElevation = 1.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                    ) {
                        Text(
                            text = "Nenhum personagem cadastrado para este anime.",
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                        )
                    }
                } else {
                    characters.forEachIndexed { index, character ->
                        CharacterItem(character = character)

                        if (index < characters.lastIndex) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(character: Character) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 3.dp,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.52f)
    ) {
        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Função: ${character.role}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Poder: ${character.power ?: "Não informado"}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun InfoLine(label: String, value: String) {
    Text(
        text = "$label: $value",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.88f)
    )
    Spacer(modifier = Modifier.height(4.dp))
}