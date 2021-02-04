package com.example.to_to.ui.edittask

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_to.ADD_TASK_RESULT_OK
import com.example.to_to.EDIT_TASK_RESULT_OK
import com.example.to_to.data.Task
import com.example.to_to.data.TaskDao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditTaskViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    @Assisted val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val task = savedStateHandle.get<Task>("task")
    var taskTitle = savedStateHandle.get<String>("taskTitle") ?: task?.title ?: ""
        set(value) {
            field = value
            savedStateHandle.set("taskTitle", value)
        }
    var taskDescription = savedStateHandle.get<String>("taskDescription") ?: task?.description ?: ""
        set(value) {
            field = value
            savedStateHandle.set("taskDescription", value)
        }
    var taskPriority = savedStateHandle.get<Int>("taskPriority") ?: task?.priority ?: 1
        set(value) {
            field = value
            savedStateHandle.set("taskPriority", value)
        }

    private val editTaskEventChannel = Channel<EditTaskEvent>()
    val editTaskEvent = editTaskEventChannel.receiveAsFlow()

    fun onSaveClick() {
        if (taskTitle.isBlank()) {
            showInvalidInputMessage("Fields cannot be empty")
            return
        }
        if (task != null) {
            val updateTask =
                task.copy(title = taskTitle, description = taskDescription, priority = taskPriority)
            updateTask(updateTask)
        } else {
            val newTask =
                Task(title = taskTitle, description = taskDescription, priority = taskPriority)
            createTask(newTask)
        }
    }

    private fun updateTask(task: Task) = viewModelScope.launch {
        taskDao.update(task)
        editTaskEventChannel.send(EditTaskEvent.NavigateBack(EDIT_TASK_RESULT_OK))
    }

    private fun createTask(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
        editTaskEventChannel.send(EditTaskEvent.NavigateBack(ADD_TASK_RESULT_OK))
    }

    private fun showInvalidInputMessage(message: String) = viewModelScope.launch {
        editTaskEventChannel.send(EditTaskEvent.ShowInvalidInputMessage(message))
    }

    sealed class EditTaskEvent {
        data class ShowInvalidInputMessage(val message: String) : EditTaskEvent()
        data class NavigateBack(val result: Int) : EditTaskEvent()
    }
}