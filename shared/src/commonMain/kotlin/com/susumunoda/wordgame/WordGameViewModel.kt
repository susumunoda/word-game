package com.susumunoda.wordgame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.susumunoda.wordgame.data.PlayerData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

@OptIn(ExperimentalResourceApi::class)
class WordGameViewModel(
    coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) {
    companion object {
        private const val TILES_PER_USER = 7
        private const val WORD_LIST_FILENAME = "word_list.txt"
    }

    private val _uiState = MutableStateFlow(WordGameState())
    val uiState = _uiState.asStateFlow()
    var wordListState by mutableStateOf<Set<String>?>(null)

    init {
        // See https://developer.android.com/kotlin/coroutines/coroutines-adv#main-safety for
        // recommendations on which dispatcher to use for different kinds of operations
        coroutineScope.launch {
            val byteArray = withContext(Dispatchers.IO) {
                resource(WORD_LIST_FILENAME).readBytes()
            }
            val words = withContext(Dispatchers.Default) {
                parseByteArray(byteArray)
            }
            wordListState = words
        }
    }

    private fun parseByteArray(byteArray: ByteArray): Set<String> {
        val words = mutableSetOf<String>()
        var wordStringBuilder = StringBuilder()
        var index = 0
        while (index < byteArray.size) {
            val char = byteArray[index++].toInt().toChar()
            if (char == '\n' || index == byteArray.size) {
                if (wordStringBuilder.isNotEmpty()) {
                    words.add(wordStringBuilder.toString())
                    wordStringBuilder = StringBuilder()
                }
            } else {
                wordStringBuilder.append(char)
            }
        }
        return words
    }

    fun startNewGame(playerOneName: String, playerTwoName: String, startPlayer: String) {
        val initialTiles = getInitialTiles()

        _uiState.value = WordGameState(
            playerOneData = PlayerData(
                name = playerOneName,
                tiles = getUserTiles(initialTiles)
            ),
            playerTwoData = PlayerData(
                name = playerTwoName,
                tiles = getUserTiles(initialTiles)
            ),
            currentTurnPlayer = startPlayer,
            gameStatus = GameStatus.STARTED,
            remainingTiles = initialTiles
        )
    }

    fun setShowUserTiles(showUserTiles: Boolean) {
        _uiState.update { it.copy(showUserTiles = showUserTiles) }
    }

    private fun getUserTiles(remainingTiles: MutableList<Tile>): List<Tile> {
        val userTiles = mutableListOf<Tile>()
        while (userTiles.size < TILES_PER_USER && remainingTiles.isNotEmpty()) {
            val randomTile = remainingTiles.random()
            remainingTiles.remove(randomTile)
            userTiles.add(randomTile)
        }
        return userTiles
    }

    private fun getInitialTiles() =
        Tile.values().flatMap { tile -> List(tile.distribution) { tile } }.toMutableList()
}
