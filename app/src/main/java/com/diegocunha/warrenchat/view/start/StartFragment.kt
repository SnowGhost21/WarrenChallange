package com.diegocunha.warrenchat.view.start

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diegocunha.warrenchat.R

class StartFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        findNavController().navigate(R.id.action_startFragment_to_homeFragment)
        return inflater.inflate(R.layout.fragment_start, container, false)
    }
}