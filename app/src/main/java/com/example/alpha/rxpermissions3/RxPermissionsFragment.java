package com.example.alpha.rxpermissions3;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.subjects.PublishSubject;


public class RxPermissionsFragment extends Fragment {

    private static final int PERMISSIONS_REQUEST_CODE = 42;

    // Contains all the current permission requests.
    // Once granted or denied, they are removed from it.
    private final Map<String, PublishSubject<Permission>> mSubjects = new HashMap<>();
    private boolean mLogging;

    public RxPermissionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    void requestPermissions(@NonNull String[] permissions) {
        requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode != PERMISSIONS_REQUEST_CODE) return;

        boolean[] shouldShowRequestPermissionRationale = new boolean[permissions.length];

        for (int i = 0; i < permissions.length; i++) {
            shouldShowRequestPermissionRationale[i] = shouldShowRequestPermissionRationale(permissions[i]);
        }

        onRequestPermissionsResult(permissions, grantResults, shouldShowRequestPermissionRationale);
    }

    void onRequestPermissionsResult(String[] permissions, int[] grantResults, boolean[] shouldShowRequestPermissionRationale) {
        for (int i = 0, size = permissions.length; i < size; i++) {
            log("onRequestPermissionsResult  " + permissions[i]);
            // Find the corresponding subject
            PublishSubject<Permission> subject = mSubjects.get(permissions[i]);
            if (subject == null) {
                // No subject found
                Log.e(RxPermissions.TAG, "RxPermissions.onRequestPermissionsResult invoked but didn't find the corresponding permission request.");
                return;
            }
            mSubjects.remove(permissions[i]);
            boolean granted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            subject.onNext(new Permission(permissions[i], granted, shouldShowRequestPermissionRationale[i]));
            subject.onComplete();
        }
    }

    boolean isGranted(String permission) {
        final FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity == null) {
            throw new IllegalStateException("This fragment must be attached to an activity.");
        }
        return fragmentActivity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    boolean isRevoked(String permission) {
        final FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity == null) {
            throw new IllegalStateException("This fragment must be attached to an activity.");
        }
        return fragmentActivity.getPackageManager().isPermissionRevokedByPolicy(permission, getActivity().getPackageName());
    }

    public void setLogging(boolean logging) {
        mLogging = logging;
    }

    public PublishSubject<Permission> getSubjectByPermission(@NonNull String permission) {
        return mSubjects.get(permission);
    }

    public void setSubjectForPermission(@NonNull String permission, @NonNull PublishSubject<Permission> subject) {
        mSubjects.put(permission, subject);
    }

    void log(String message) {
        if (mLogging) {
            Log.d(RxPermissions.TAG, message);
        }
    }

}