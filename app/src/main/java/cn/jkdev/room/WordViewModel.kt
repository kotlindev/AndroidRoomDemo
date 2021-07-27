package cn.jkdev.room

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class WordViewModel(private val repository: WordRepository) : ViewModel() {
    // 使用 LiveData 并缓存 allWords 返回的内容有几个好处：
    // - 我们可以在数据上放置一个观察者（而不是轮询更改），并且仅在数据实际更改时才更新 UI。
    // - Repository 通过 ViewModel 与 UI 完全分离。
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    // 启动新的协程以非阻塞方式插入数据
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}