package com.example.alpha.ui.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.alpha.R;
import com.example.alpha.databinding.FragmentMapBinding;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;

    private MapView mapView = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Configuration.getInstance().load(requireActivity().getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(requireActivity().getApplicationContext()));
        MapViewModel mapViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);

        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mapView = binding.mapView;

        mapView.setUseDataConnection(true);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(14);
        GpsMyLocationProvider myLocationProvider = new GpsMyLocationProvider(requireActivity());
        MyLocationNewOverlay myLocationNewOverlay = new MyLocationNewOverlay(myLocationProvider, mapView);
        myLocationNewOverlay.enableMyLocation();
        myLocationNewOverlay.enableFollowLocation();

        Bitmap icon = BitmapFactory.decodeResource(requireActivity().getResources(), R.drawable.baseline_emoji_people_24);
        myLocationNewOverlay.setPersonIcon(icon);
        mapView.getOverlays().add(myLocationNewOverlay);

        myLocationNewOverlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                mapView.getOverlays().clear();
                mapView.getOverlays().add(myLocationNewOverlay);
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mapView.getController().animateTo(myLocationNewOverlay.getMyLocation());
                    }
                });
            }
        });

//        final TextView textView = binding.textDashboard;
//        mapViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(requireActivity().getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(requireActivity().getApplicationContext()));
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Configuration.getInstance().save(requireActivity().getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(requireActivity().getApplicationContext()));
        if (mapView != null) {
            mapView.onPause();
        }
    }
}