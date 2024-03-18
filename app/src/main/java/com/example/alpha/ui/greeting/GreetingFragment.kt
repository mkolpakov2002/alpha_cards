package com.example.alpha.ui.greeting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alpha.R
import com.example.alpha.databinding.FragmentGreetingBinding
import com.example.alpha.ui.permissions.permission.PermissionManager.checkIfCameraPermissionIsGranted
import com.google.android.material.button.MaterialButton

class GreetingFragment : Fragment() {

    private var binding: FragmentGreetingBinding? = null
    private var materialButtonNext: MaterialButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentGreetingBinding.inflate(
            inflater,
            container,
            false
        )

        materialButtonNext = binding!!.button

        materialButtonNext!!.setOnClickListener{
            checkIfCameraPermissionIsGranted(requireContext(), binding!!.root,
                R.id.navigation_home)
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}