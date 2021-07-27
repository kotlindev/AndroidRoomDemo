package cn.jkdev.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

// 将构造函数中的DAO声明为私有属性
// 传入DAO而不是传入数据库，我们只需要访问DAO
class WordRepository(private val wordDao: WordDao) {

    // Room 在单独的线程上执行所有查询。
    // 当数据发生变化时，Observed Flow 会通知观察者。
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWord()

    // 默认情况下，Room 在主线程之外运行挂起查询，
    // 因此，我们不是在主线程之外进行长时间运行的数据库工作。
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word){
        wordDao.insert(word)
    }
}