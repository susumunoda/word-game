package com.susumunoda.wordgame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

@OptIn(ExperimentalResourceApi::class)
class WordListProvider(coroutineScope: CoroutineScope) {
    companion object {
        private const val WORD_LIST_FILENAME = "word_list.txt"
    }

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
}
