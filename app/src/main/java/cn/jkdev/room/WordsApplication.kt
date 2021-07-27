package cn.jkdev.room

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    // 惰性加载，因此数据库和存储库仅在需要时创建，而不是在应用程序启动时创建
    val database by lazy { WordRoomDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
}