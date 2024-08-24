package com.example.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.todoapp.databinding.FragmentAddTodoPopupBinding
import com.example.todoapp.utils.ToDoData
import com.google.android.material.textfield.TextInputEditText

class AddTodoPopupFragment : DialogFragment() {

    private lateinit var binding: FragmentAddTodoPopupBinding
    private lateinit var listener: DialogNextButtonClickListener
    private var toDoData: ToDoData? = null

    fun setListener(listener: DialogNextButtonClickListener)
    {
        this.listener = listener
    }

    companion object{
        const val TAG = "AddTodoPopupFragment"

        @JvmStatic
        fun newInstance(taskId: String, task: String) = AddTodoPopupFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddTodoPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments != null)
        {
            toDoData = ToDoData(arguments?.getString("taskId").toString(), arguments?.getString("task").toString())
            binding.todoInputEditText.setText(toDoData?.task)
        }
        registerEvents()
    }

    private fun registerEvents()
    {
        binding.todoNextButton.setOnClickListener(){
            val todoTask = binding.todoInputEditText.text.toString()
            if(todoTask.isNotEmpty())
            {
                if(toDoData == null)
                {
                    listener.onSaveTask(todoTask, binding.todoInputEditText)
                }
                else
                {
                    toDoData?.task = todoTask
                    listener.onUpdateTask(toDoData!!, binding.todoInputEditText)
                }
            }
            else
            {
                Toast.makeText(context, "Please type some task", Toast.LENGTH_SHORT).show()
            }
        }

        binding.todoCloseButton.setOnClickListener()
        {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    interface DialogNextButtonClickListener{
        fun onSaveTask(todo: String, todoEditText: TextInputEditText)
        fun onUpdateTask(toDoData: ToDoData, todoEditText: TextInputEditText)
    }
}