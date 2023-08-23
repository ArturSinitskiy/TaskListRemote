package com.example.tasklist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasklist.databinding.FragmentCompletedTasksBinding
import com.example.tasklist.databinding.FragmentTasksBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CompletedTasksFragment : Fragment(), TasksAdapter.CheckBoxClickListener {

    private lateinit var binding: FragmentCompletedTasksBinding
    private val dataModel: DataModel by activityViewModels()
    private val adapter = TasksAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompletedTasksBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val tasksJson = sharedPreferences.getString("completed_tasks", null)

        if(tasksJson != null){
            val gson = Gson()
            val taskListType = object : TypeToken<ArrayList<Task>>() {}.type
            val tasks = gson.fromJson<ArrayList<Task>>(tasksJson, taskListType)
            adapter.listOfTasks = tasks
            adapter.notifyDataSetChanged()
        }

        binding.recyclerViewCompleted.adapter = adapter
        binding.recyclerViewCompleted.layoutManager = LinearLayoutManager(activity?.baseContext)

        dataModel.messageForCompletedTasks.observe(activity as LifecycleOwner){
            adapter.listOfTasks.addAll(it)
            adapter.notifyDataSetChanged()
        }

        binding.btnClean.setOnClickListener{
            adapter.listOfTasks.removeAll(adapter.listOfTasks)
            adapter.notifyDataSetChanged()
        }

        binding.btnUpdate.setOnClickListener {
            for(task in adapter.listOfTasks){
                if(!task.isCompleted){
                    dataModel.messageForFragmentTask.value = task.title
                }
            }
            adapter.listOfTasks.removeIf {
                !(it.isCompleted)
            }
            adapter.notifyDataSetChanged()
        }

    }

    override fun onCheckBoxClick(task: Task) {
        task.isCompleted = !task.isCompleted
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()
        val tasksJson = gson.toJson(adapter.listOfTasks)

        editor.putString("completed_tasks", tasksJson)
        editor.apply()
    }
}