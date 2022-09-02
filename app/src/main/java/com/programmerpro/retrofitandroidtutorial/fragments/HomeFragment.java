package com.programmerpro.retrofitandroidtutorial.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.programmerpro.retrofitandroidtutorial.R;
import com.programmerpro.retrofitandroidtutorial.storage.SharedPrefManager;

public class HomeFragment extends Fragment {

    private TextView tvEmail, tvName, tvSchool;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvEmail = view.findViewById(R.id.textViewEmail);
        tvName = view.findViewById(R.id.textViewName);
        tvSchool = view.findViewById(R.id.textViewSchool);

        tvEmail.setText(SharedPrefManager.getInstance(requireContext()).getUser().getEmail());
        tvName.setText(SharedPrefManager.getInstance(requireContext()).getUser().getName());
        tvSchool.setText(SharedPrefManager.getInstance(requireContext()).getUser().getSchool());

    }
}