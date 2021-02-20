package com.example.homework2;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.ViewHolder> {

    private List<Beer> beers;
    private Context context;

    public BeerAdapter(List<Beer> beers){
        this.beers = beers;
    }

    @NonNull
    @Override
    public BeerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View beerView = inflater.inflate(R.layout.item_beer, parent, false);
        ViewHolder viewHolder = new ViewHolder(beerView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BeerAdapter.ViewHolder holder, int position) {
        Beer beer = beers.get(position);
        holder.textView_name.setText(beer.getName());
        holder.textView_description.setText(beer.getDescription());
        Picasso.get().load(beer.getImage_url()).into(holder.imageView_beer);
        if(beer.isFavorite()){
            try {
                InputStream ims = context.getAssets().open("favorite_on.png");
                Drawable d = Drawable.createFromStream(ims, null);
                holder.imageView_favorite.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                InputStream ims = context.getAssets().open("favorite_off.png");
                Drawable d = Drawable.createFromStream(ims, null);
                holder.imageView_favorite.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        holder.imageView_favorite.setOnClickListener(v -> {
            if(beer.isFavorite()){
                beer.setFavorite(false);
            }
            else{
                beer.setFavorite(true);
            }
            this.notifyItemChanged(position);
        });
        holder.imageView_beer.setOnClickListener(v -> {
            Intent intent = new Intent(context, FourthActivity.class);
            intent.putExtra("data", beer.getData());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return beers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_name;
        TextView textView_description;
        ImageView imageView_beer;
        ImageView imageView_favorite;

        public ViewHolder(View itemView){
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_description = itemView.findViewById(R.id.textView_description);
            imageView_beer = itemView.findViewById(R.id.imageView_beer);
            imageView_favorite = itemView.findViewById(R.id.imageView_favorite);
        }

    }
}

