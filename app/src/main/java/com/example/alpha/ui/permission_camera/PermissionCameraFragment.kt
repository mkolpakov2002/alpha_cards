package com.example.alpha.ui.permission_camera

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.alpha.R
import com.example.alpha.databinding.FragmentPermissionCameraBinding
import com.example.alpha.rxpermissions3.RxPermissions
import com.example.alpha.ui.permission.PermissionManager
import com.example.alpha.ui.permission.PermissionManager.checkIfLocationPermissionIsGranted
import com.google.android.material.button.MaterialButton
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PermissionCameraFragment : Fragment() {
    private var binding: FragmentPermissionCameraBinding? = null
    private var materialButtonGrant: MaterialButton? = null
    private var rxPermissions: RxPermissions? = null
    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermissionCameraBinding.inflate(
            inflater,
            container,
            false
        )

        rxPermissions = RxPermissions(this)

        materialButtonGrant = binding!!.button

        materialButtonGrant!!.setOnClickListener{
            // Must be done during an initialization phase like onCreate
            disposables.add(rxPermissions!!
                .request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (!granted) { // Always true pre-M
                        Toast.makeText(requireActivity(), "Разрешение не дано.", Toast.LENGTH_SHORT).show()
                    }
                    checkIfLocationPermissionIsGranted(
                        requireContext(), binding!!.root,
                        R.id.navigation_home
                    )
                })
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
        binding = null
    }

    override fun onResume() {
        super.onResume()
        }
    }
