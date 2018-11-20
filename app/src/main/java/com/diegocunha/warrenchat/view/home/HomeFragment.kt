package com.diegocunha.warrenchat.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegocunha.warrenchat.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        val adapter = MessageAdapter()

        binding.recyclerMessages.layoutManager = LinearLayoutManager(this.requireContext())
        binding.recyclerMessages.adapter = adapter
        binding.setLifecycleOwner(this)

        viewModel.answers.observe(this, Observer {
            adapter.setItem(it)
        })

        binding.sendMessage.setOnClickListener {
            val message = binding.message.text.toString()

            if (message.isEmpty()) {
                return@setOnClickListener
            }

            binding.message.setText("")
            viewModel.sendMessage(message)
        }


        return binding.root
    }
}