package com.example.tasklist

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tasklist.databinding.FragmentAddNewTaskBinding
import com.example.tasklist.databinding.FragmentCompletedTasksBinding


class AddNewTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddNewTaskBinding
    private val dataModel: DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnAddNewTask.setOnClickListener {
            dataModel.messageForFragmentTask.value = binding.inputText.text.toString()
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
        binding.btnClose.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }


}