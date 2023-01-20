package com.example.alpha.ui.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import com.example.alpha.R;

public interface PermissionManager {
    static boolean checkIfCameraPermissionIsGranted(Context context, View view, Integer id){
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            checkIfLocationPermissionIsGranted(context, view, id);
            return true;
        } else {
            Navigation.findNavController(view).navigate(R.id.permissionCameraFragment);
            return false;
        }
    }

    static boolean checkIfLocationPermissionIsGranted(Context context, View view, Integer id){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            Navigation.findNavController(view).navigate(id);
            return true;
        } else {
            Navigation.findNavController(view).navigate(R.id.permissionLocationFragment);
            return false;
        }
    }
}
