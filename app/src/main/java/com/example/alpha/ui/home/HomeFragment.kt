package com.example.alpha.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.alpha.R
import com.example.alpha.databinding.FragmentHomeBinding
import com.google.android.material.button.MaterialButton

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private var materialButton: MaterialButton? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val textView = binding!!.textHome
        homeViewModel.text.observe(viewLifecycleOwner) { text: String? -> textView.text = text }

        materialButton = binding!!.scanCard

        materialButton!!.setOnClickListener {
            Navigation.findNavController(materialButton!!).navigate(R.id.liveBarcodeScanningActivity)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}