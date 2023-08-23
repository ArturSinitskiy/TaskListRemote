package com.example.tasklist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasklist.databinding.FragmentTasksBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TasksFragment : Fragment(), TasksAdapter.CheckBoxClickListener {

    private lateinit var binding: FragmentTasksBinding
    private val dataModel: DataModel by activityViewModels()
    private val adapter = TasksAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val tasksJson = sharedPreferences.getString("taskList", null)

        if (tasksJson != null) {
            val gson = Gson()
            val taskListType = object : TypeToken<ArrayList<Task>>() {}.type
            val tasks = gson.fromJson<ArrayList<Task>>(tasksJson, taskListType)
            adapter.listOfTasks = tasks
            adapter.notifyDataSetChanged()
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity?.baseContext)

        dataModel.messageForFragmentTask.observe(activity as LifecycleOwner){
            adapter.addNewTask(Task(false, it))
        }

        binding.btnAdd.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.add_task_place, AddNewTaskFragment())?.commit()
        }
        binding.btnDelete.setOnClickListener{
            val listOfCompletedTasks = ArrayList<Task>()
            for (task in adapter.listOfTasks){
                if (task.isCompleted) {
                    listOfCompletedTasks.add(task)
                }
            }
            dataModel.messageForCompletedTasks.value = listOfCompletedTasks
            adapter.listOfTasks.removeIf{
                it.isCompleted
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

        editor.putString("taskList", tasksJson)
        editor.apply()
    }

}