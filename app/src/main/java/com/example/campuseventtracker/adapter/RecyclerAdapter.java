package com.example.campuseventtracker.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuseventtracker.R;
import com.example.campuseventtracker.databinding.ListHomeBinding;
import com.example.campuseventtracker.db.DatabaseHelper;
import com.example.campuseventtracker.model.ClickListener;
import com.example.campuseventtracker.model.Events;
import com.example.campuseventtracker.model.Helper;


import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Vh> {
    List<Events> list;
    Context context;
    ClickListener clickListener;
    DatabaseHelper helper;

    public RecyclerAdapter(List<Events> list, Context context, ClickListener clickListener) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
        helper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_home, parent, false);
        return new Vh(view);
    }

    public void setList(List<Events> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public List<Events> getList() {
        return list;
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {
        Events model = list.get(position);
        holder.binding.img.setImageURI(Uri.parse(model.getImage()));
        holder.binding.tvName.setText(model.getName());
        if (model.isFavourite()) {
            holder.binding.fav.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            holder.binding.fav.setImageResource(R.drawable.baseline_favorite_border_24);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(position);
            }
        });
        holder.binding.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isFavourite()) {
                    model.setFavourite(false);
                    helper.removeFromFavorites(Helper.usersData.getId(), model.getId());
                }else {
                    model.setFavourite(true);
                    helper.addToFavorites(Helper.usersData.getId(), model.getId());
                }
                notifyItemChanged(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Vh extends RecyclerView.ViewHolder {
        ListHomeBinding binding;

        public Vh(@NonNull View itemView) {
            super(itemView);
            binding = ListHomeBinding.bind(itemView);
        }
    }
}
