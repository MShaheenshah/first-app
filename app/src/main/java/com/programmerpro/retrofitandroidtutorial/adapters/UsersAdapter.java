package com.programmerpro.retrofitandroidtutorial.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.programmerpro.retrofitandroidtutorial.R;
import com.programmerpro.retrofitandroidtutorial.model.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    Context context;
    List<User> list;

    public UsersAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsersViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_users, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        User user = list.get(position);

        holder.tvName.setText(user.getName());
        holder.tvEmail.setText(user.getEmail());
        holder.tvSchool.setText(user.getSchool());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvEmail, tvSchool;

        public UsersViewHolder(@NonNull View v) {
            super(v);

            tvName = v.findViewById(R.id.textViewName);
            tvEmail = v.findViewById(R.id.textViewEmail);
            tvSchool = v.findViewById(R.id.textViewSchool);
        }
    }
}
