package com.example.to_to.ui.alltasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.to_to.ADD_TASK_RESULT_OK
import com.example.to_to.EDIT_TASK_RESULT_OK
import com.example.to_to.data.Task
import com.example.to_to.data.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskDao: TaskDao
) : ViewModel() {

    private val taskEventChannel = Channel<TaskEvent>()
    val taskEvent = taskEventChannel.receiveAsFlow()

    val tasks = taskDao.getTasks().asLiveData()

    fun onTaskSelected(task: Task) = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.NavigateToEditTaskScreen(task))
    }

    fun onTaskCheckChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        taskDao.update(task.copy(done = isChecked))
    }

    fun onTaskSwiped(task: Task) = viewModelScope.launch {
        taskDao.delete(task)
        taskEventChannel.send(TaskEvent.ShowUndoDelete(task))
    }

    fun onUndoDeleteClick(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
    }

    fun onAddNewTaskClick() = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.NavigateToAddTaskScreen)
    }

    fun onAddResult(result: Int) {
        when (result) {
            ADD_TASK_RESULT_OK -> showTaskSavedConfirmation("Task added successfully")
            EDIT_TASK_RESULT_OK -> showTaskSavedConfirmation("Task updated successfully")
        }
    }

    private fun showTaskSavedConfirmation(message: String) = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.ShowTaskSavedConfirmationMessage(message))
    }

    sealed class TaskEvent {
        object NavigateToAddTaskScreen : TaskEvent()
        data class NavigateToEditTaskScreen(val task: Task) : TaskEvent()
        data class ShowUndoDelete(val task: Task) : TaskEvent()
        data class ShowTaskSavedConfirmationMessage(val message: String) : TaskEvent()
    }
}