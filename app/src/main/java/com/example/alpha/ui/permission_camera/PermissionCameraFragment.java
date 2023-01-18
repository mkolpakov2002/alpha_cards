package com.example.alpha.ui.permission_camera;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alpha.R;
import com.example.alpha.databinding.FragmentNotificationsBinding;
import com.example.alpha.databinding.FragmentPermissionCameraBinding;
import com.example.alpha.ui.notifications.NotificationsViewModel;
public class PermissionCameraFragment extends Fragment {
    private FragmentPermissionCameraBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPermissionCameraBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}