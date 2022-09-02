package com.programmerpro.retrofitandroidtutorial.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.programmerpro.retrofitandroidtutorial.R;
import com.programmerpro.retrofitandroidtutorial.activities.LoginActivity;
import com.programmerpro.retrofitandroidtutorial.activities.MainActivity;
import com.programmerpro.retrofitandroidtutorial.activities.ProfileActivity;
import com.programmerpro.retrofitandroidtutorial.api.RetrofitClient;
import com.programmerpro.retrofitandroidtutorial.model.DefaultResponse;
import com.programmerpro.retrofitandroidtutorial.model.LoginResponse;
import com.programmerpro.retrofitandroidtutorial.model.User;
import com.programmerpro.retrofitandroidtutorial.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    EditText edEmail, edName, edSchool;
    EditText edCurrPass, edNewPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edEmail = view.findViewById(R.id.editTextEmail);
        edName = view.findViewById(R.id.editTextName);
        edSchool = view.findViewById(R.id.editTextSchool);
        edCurrPass = view.findViewById(R.id.editTextCurrentPassword);
        edNewPass = view.findViewById(R.id.editTextNewPassword);

        view.findViewById(R.id.buttonSave).setOnClickListener(this);
        view.findViewById(R.id.buttonChangePassword).setOnClickListener(this);
        view.findViewById(R.id.buttonLogout).setOnClickListener(this);
        view.findViewById(R.id.buttonDelete).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSave:
                updateProfile();
                break;
            case R.id.buttonChangePassword:
                updatePassword();
                break;
            case R.id.buttonLogout:
                logout();
                break;
            case R.id.buttonDelete:
                deleteUser();
                break;

        }
    }

    private void deleteUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Are you sure?");
        builder.setMessage("This process is irreversible");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            User user = SharedPrefManager.getInstance(requireContext()).getUser();
            Call<DefaultResponse> call = RetrofitClient
                    .getInstance().getApi()
                    .deleteUser(user.getId());
            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    if (!response.body().isErr()){
                        SharedPrefManager.getInstance(requireContext()).clear();
                        Intent intent = new Intent(requireContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    Toast.makeText(requireContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {

                }
            });
        });

        builder.setNegativeButton("No", null);
        builder.create().show();
    }

    private void logout() {
        SharedPrefManager.getInstance(requireContext()).clear();
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void updatePassword() {

        String currentpassword = edCurrPass.getText().toString().trim();
        String newpassword = edNewPass.getText().toString().trim();

        if (currentpassword.isEmpty()) {
            edCurrPass.setError("Password required");
            edCurrPass.requestFocus();
            return;
        }

        if (newpassword.isEmpty()) {
            edNewPass.setError("Enter new password");
            edNewPass.requestFocus();
            return;
        }

        User user = SharedPrefManager.getInstance(requireContext()).getUser();
        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .updatePassword(currentpassword, newpassword, user.getEmail()
        );

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Toast.makeText(requireContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }

    private void updateProfile() {
        String email = edEmail.getText().toString().trim();
        String name = edName.getText().toString().trim();
        String school = edSchool.getText().toString().trim();

        if (email.isEmpty()) {
            edEmail.setError("Email is required");
            edEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError("Enter a valid email");
            edEmail.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            edName.setError("Name required");
            edName.requestFocus();
            return;
        }

        if (school.isEmpty()) {
            edSchool.setError("School required");
            edSchool.requestFocus();
            return;
        }

        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().updateUser(
                        user.getId(),
                        email,
                        name,
                        school
                );

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if (!response.body().isError()){
                    SharedPrefManager.getInstance(getActivity()).saveUser(response.body().getUser());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}