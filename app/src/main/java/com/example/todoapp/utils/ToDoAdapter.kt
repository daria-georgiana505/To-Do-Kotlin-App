package com.example.todoapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.EachTodoItemBinding

class ToDoAdapter(private val list: MutableList<ToDoData>): RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
    private var listener: ToDoAdapterClicksInterface? = null

    fun setListener(listener: ToDoAdapterClicksInterface)
    {
        this.listener = listener
    }

    inner class ToDoViewHolder(val binding: EachTodoItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = EachTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        with(holder){
            with(list[position]){
                binding.todoTask.text = this.task

                binding.deleteTaskButton.setOnClickListener()
                {
                    listener?.onDeleteTaskButtonClicked(this)
                }

                binding.editTaskButton.setOnClickListener()
                {
                    listener?.onEditTaskButtonClicked(this)
                }
            }
        }
    }

    interface ToDoAdapterClicksInterface{
        fun onDeleteTaskButtonClicked(toDoData: ToDoData)
        fun onEditTaskButtonClicked(toDoData: ToDoData)
    }
}