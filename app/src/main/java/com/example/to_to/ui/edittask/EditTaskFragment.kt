package com.example.to_to.ui.edittask

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.to_to.R
import com.example.to_to.databinding.FragmentEditTaskBinding
import com.example.to_to.utils.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EditTaskFragment : Fragment(R.layout.fragment_edit_task) {

    private val TAG = "EditTaskFragment"
    private val viewModel: EditTaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEditTaskBinding.bind(view)
        binding.apply {
            editTaskTitle.setText(viewModel.taskTitle)
            editTaskDescription.setText(viewModel.taskDescription)
            when (viewModel.taskPriority) {
                3 -> priority.check(R.id.rb_high_priority)
                2 -> priority.check(R.id.rb_medium_priority)
                else -> priority.check(R.id.rb_low_priority)
            }

            editTaskTitle.addTextChangedListener {
                viewModel.taskTitle = it.toString()
            }
            editTaskDescription.addTextChangedListener {
                viewModel.taskDescription = it.toString()
            }
            priority.setOnCheckedChangeListener { group, checkedId ->
                when {
                    rbHighPriority.isChecked -> {
                        viewModel.taskPriority = 3
                    }
                    rbMediumPriority.isChecked -> {
                        viewModel.taskPriority = 2
                    }
                    else -> {
                        viewModel.taskPriority = 1
//                        priority.check(R.id.rb_low_priority)
                    }
                }
            }
            if (editTaskTitle.text.isBlank()) {
                btnAdd.text = "Add"
            } else {
                btnAdd.text = "Edit"
            }
            btnAdd.setOnClickListener {
                viewModel.onSaveClick()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.editTaskEvent.collect { event ->
                when (event) {
                    is EditTaskViewModel.EditTaskEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.message, Snackbar.LENGTH_LONG).show()
                    }
                    is EditTaskViewModel.EditTaskEvent.NavigateBack -> {
                        //Hide the keyboard after editing
                        binding.editTaskTitle.clearFocus()
                        binding.editTaskDescription.clearFocus()
                        setFragmentResult(
                            "edit_request",
                            bundleOf("edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                }.exhaustive
            }
        }
    }
}