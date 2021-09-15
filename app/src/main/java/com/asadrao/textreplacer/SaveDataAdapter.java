package com.asadrao.textreplacer;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asadrao.textreplacer.utils.Database;

public class SaveDataAdapter extends RecyclerView.Adapter<SaveDataAdapter.MyViewHolder> {
    Context context;
    ArrayList<SaveModel> saveModelArrayList;
    Database database;

    public SaveDataAdapter(Context context, ArrayList<SaveModel> notificationModelArrayList) {
        this.context = context;
        this.saveModelArrayList = notificationModelArrayList;
        database = new Database(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_save_data, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvOutput.setText(saveModelArrayList.get(position).output);
        holder.icDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.deleteCategory(saveModelArrayList.get(position).id);
                saveModelArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, saveModelArrayList.size());
            }
        });

        holder.icCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", saveModelArrayList.get(position).output);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        holder.icShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, saveModelArrayList.get(position).output);
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return saveModelArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOutput;
        ImageView icDelete, icCopy, icShare;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOutput = itemView.findViewById(R.id.tvOutput);
            icDelete = itemView.findViewById(R.id.icDelete);
            icCopy = itemView.findViewById(R.id.icCopy);
            icShare = itemView.findViewById(R.id.icShare);
        }
    }
}