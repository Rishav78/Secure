package com.example.secure.RecyclerViews;

import android.content.Context;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.secure.R;
import com.example.secure.Modules.PasswordModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PasswordRecyclerView extends RecyclerView.Adapter<PasswordRecyclerView.PasswordViewHolder> {

    List<PasswordModel> list;
    FirebaseAuth mAuth;

    public PasswordRecyclerView(List<PasswordModel> list){
        this.list = list;
        mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.password_layout, parent, false);
        return new PasswordViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PasswordViewHolder holder, int position) {
        final PasswordModel p = list.get(position);

        toogleEditable(holder, false);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePasswordFromDatabase(p.getId());
            }
        });

        holder.visiblePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.password.setTransformationMethod(null);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.password.setTransformationMethod(null);
                holder.update.setVisibility(View.VISIBLE);
                toogleEditable(holder, true);
            }
        });

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword(holder, p);
            }
        });


        holder.hint.setText(p.getHint());

        holder.username.setText(p.getUsername());

        holder.password.setText(p.getPassword());
    }

    private void updatePassword(PasswordViewHolder holder, PasswordModel p) {
        String email = holder.username.getText().toString().trim(),
                password = holder.password.getText().toString().trim();

        String id = mAuth.getCurrentUser().getUid();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("password").child(id).child(p.getId());

        if(email.isEmpty()) {
            holder.username.setError("Can't be Empty");
            holder.username.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            holder.password.setError("Can't be Empty");
            holder.password.requestFocus();
            return;
        }

        database.setValue(new PasswordModel(p.getId(), p.getHint(), email, password));

        toogleEditable(holder, false);
        holder.password.setTransformationMethod(new PasswordTransformationMethod());
        holder.update.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PasswordViewHolder extends RecyclerView.ViewHolder {

        EditText username, password;
        TextView hint;
        Button edit, delete, visiblePassword, update;

        public PasswordViewHolder(@NonNull View itemView) {
            super(itemView);

            hint = itemView.findViewById(R.id.showHint);
            username = itemView.findViewById(R.id.showUsername);
            password = itemView.findViewById(R.id.showPassword);

            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            visiblePassword = itemView.findViewById(R.id.visiblePassword);
            update = itemView.findViewById(R.id.update);
        }
    }


    private void deletePasswordFromDatabase(String id) {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("password").child(userId).child(id);
        database.removeValue();
    }

    private void toogleEditable(PasswordViewHolder holder, boolean v) {
        holder.username.setEnabled(v);
        holder.password.setEnabled(v);
    }

}
