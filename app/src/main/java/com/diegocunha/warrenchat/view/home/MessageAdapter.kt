package com.diegocunha.warrenchat.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.diegocunha.warrenchat.databinding.ChatMessageOtherBinding
import com.diegocunha.warrenchat.databinding.ChatMessageSelfBinding
import com.diegocunha.warrenchat.model.data.Message
import com.diegocunha.warrenchat.view.databinding.ReactiveAdapter

class MessageAdapter : ReactiveAdapter<Message, ViewDataBinding>() {

    companion object {
        val USER = 0
        val BOT = 1
    }

    override fun getBinding(context: Context, parent: ViewGroup, viewType: Int): ViewDataBinding {
        return if (viewType == USER) {
            ChatMessageSelfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            ChatMessageOtherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
    }

    override fun bind(binding: ViewDataBinding, item: Message) {
        val viewModel = MessageViewModel(item)

        if (binding is ChatMessageSelfBinding) {
            binding.viewModel = viewModel
        } else if (binding is ChatMessageOtherBinding) {
            binding.viewModel = viewModel
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].sent == Message.BOT) BOT else USER
    }


}