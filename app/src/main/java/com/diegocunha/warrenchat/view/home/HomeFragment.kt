package com.diegocunha.warrenchat.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.diegocunha.warrenchat.databinding.FragmentHomeBinding
import iwsbrazil.io.artmuseum.extensions.observeOnce
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.initialMessage.observe(this, Observer {
            Log.e("Message", it.messages[0].message)
        })

        binding.sendMessage.setOnClickListener {
            val message = binding.message.text.toString()

            if (message.isEmpty()) {
                return@setOnClickListener
            }

            sendMessage(message)
        }


        return binding.root
    }

    private fun sendMessage(message: String) {
        viewModel.sendMessage(message).observeOnce(this, Observer {
            Log.e("Message", it.messages[0].message)
        })
    }
}