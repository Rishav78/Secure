package com.example.secure.RecyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.secure.R;
import com.example.secure.Modules.PasswordModel;

import java.util.List;

public class PasswordRecyclerView extends RecyclerView.Adapter<PasswordRecyclerView.PasswordViewHolder> {

    List<PasswordModel> list;

    public PasswordRecyclerView(List<PasswordModel> list){
        this.list = list;
    }

    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.password_layout, parent, false);
        return new PasswordViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordViewHolder holder, int position) {
        PasswordModel p = list.get(position);

        holder.hint.setEnabled(false);
        holder.hint.setText(p.getHint());
        holder.username.setEnabled(false);
        holder.username.setText(p.getUsername());
        holder.password.setEnabled(false);
        holder.password.setText(p.getPassword());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PasswordViewHolder extends RecyclerView.ViewHolder {

        EditText hint, username, password;

        public PasswordViewHolder(@NonNull View itemView) {
            super(itemView);

            hint = itemView.findViewById(R.id.showHint);
            username = itemView.findViewById(R.id.showUsername);
            password = itemView.findViewById(R.id.showPassword);
        }
    }
}
