package com.example.to_to.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.to_to.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDB : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDB>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            //Initiate DB
            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(
                    Task(
                        done = true,
                        priority = 2,
                        title = "Create projects",
                        description = "Create the required project which meets all the required features and more! "
                    )
                )
                dao.insert(
                    Task(
                        done = true,
                        title = "Breathe",
                        description = "Try to do a whole meditation session to reach mindfulness "
                    )
                )
                dao.insert(
                    Task(
                        title = "Take Android courses",
                        description = "Finish the Android courses you already enrolled in"
                    )
                )
                dao.insert(
                    Task(
                        title = "Check emails",
                        description = "Go through mail inbox and reply to the necessary messages"
                    )
                )
                dao.insert(
                    Task(
                        priority = 3,
                        title = "Wear a mask",
                        description = "Wear a mask and keep an extra one around"
                    )
                )
                dao.insert(
                    Task(
                        priority = 3,
                        title = "Clean my hands",
                        description = "Clean hands regularly"
                    )
                )
                dao.insert(
                    Task(
                        title = "Keep a safe distance",
                        priority = 3,
                        description = "keep a distance of at least six feet"
                    )
                )
                dao.insert(
                    Task(
                        priority = 2,
                        title = "Gather with family",
                        description = "It's the holiday. Family time"
                    )
                )
            }
        }
    }
}