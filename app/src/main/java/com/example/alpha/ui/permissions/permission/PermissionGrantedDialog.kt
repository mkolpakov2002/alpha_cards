package com.example.alpha.ui.permissions.permission

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.alpha.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PermissionGrantedDialog: DialogFragment() {

    private lateinit var customView: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        customView = requireActivity().layoutInflater.inflate(R.layout.dialog_permission_granted, null)

        val builder = MaterialAlertDialogBuilder(requireContext())
            .setView(customView)
        val ok = customView.findViewById<MaterialButton>(R.id.button2)
        ok.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("key", "onDestroy")
            dismiss()
        }
        return builder.create()
    }


}