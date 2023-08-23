package com.example.tasklist

import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.provider.ContactsContract.RawContacts.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.databinding.TaskItemBinding
import kotlin.concurrent.thread

class TasksAdapter(val listener: CheckBoxClickListener): RecyclerView.Adapter<TasksAdapter.TaskHolder>() {

    var listOfTasks = ArrayList<Task>()

    inner class TaskHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = TaskItemBinding.bind(itemView)
        fun bind(task: Task, listener: CheckBoxClickListener) = with(binding){
            taskTitle.text = task.title
            taskCheckbox.isChecked = task.isCompleted
            itemView.findViewById<CheckBox>(R.id.task_checkbox).setOnClickListener {
                listener.onCheckBoxClick(task)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfTasks.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(listOfTasks[position], listener)
    }

    fun addNewTask(task: Task){
        listOfTasks.add(task)
        notifyDataSetChanged()
    }

    interface CheckBoxClickListener{
        fun onCheckBoxClick(task: Task)
    }
}