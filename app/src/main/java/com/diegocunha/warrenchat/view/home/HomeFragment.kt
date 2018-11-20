package com.diegocunha.warrenchat.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegocunha.warrenchat.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), View.OnLayoutChangeListener {

    val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding
    private val adapter = MessageAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.recyclerMessages.layoutManager = LinearLayoutManager(this.requireContext())
        binding.recyclerMessages.adapter = adapter
        binding.recyclerMessages.itemAnimator = DefaultItemAnimator()
        binding.recyclerMessages.addOnLayoutChangeListener(this)


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

    override fun onLayoutChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int, p6: Int, p7: Int, p8: Int) {
        binding.recyclerMessages.smoothScrollToPosition(adapter.itemCount)
    }
}