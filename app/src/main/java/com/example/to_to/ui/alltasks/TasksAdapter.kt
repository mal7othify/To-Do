package com.example.to_to.ui.alltasks

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.to_to.R
import com.example.to_to.data.Task
import com.example.to_to.databinding.ItemTaskBinding

class TasksAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Task, TasksAdapter.TasksViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class TasksViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //Change the style on done tasks
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onItemClick(task)
                    }
                }
                cbDone.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onCheckboxClick(task, cbDone.isChecked)
                    }
                }
            }
        }

        fun bind(task: Task) {
            binding.apply {
                cbDone.isChecked = task.done
                tvTask.text = task.title
                tvTask.paint.isStrikeThruText = task.done
                when (task.priority) {
                    1 -> {
                        ivTaskPriority.setImageResource(R.drawable.ic_baseline_arrow_circle_down_24)
                        ivTaskPriority.setColorFilter(Color.parseColor("#4CAF50"))
                        ivTaskPriority.contentDescription = "Low priority"
                    }
                    2 -> {
                        ivTaskPriority.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24)
                        ivTaskPriority.setColorFilter(Color.parseColor("#CAA947"))
                        ivTaskPriority.contentDescription = "Medium priority"
                    }
                    3 -> {
                        ivTaskPriority.setImageResource(R.drawable.ic_baseline_arrow_circle_up_24)
                        ivTaskPriority.setColorFilter(Color.parseColor("#FFFF0000"))
                        ivTaskPriority.contentDescription = "High priority"
                    }
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(task: Task)
        fun onCheckboxClick(task: Task, isChecked: Boolean)
    }

    //Compare the difference between task items
    class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }
}