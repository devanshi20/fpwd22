package com.example.atul_.eatit;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.atul_.eatit.Database.Database;

import com.example.atul_.eatit.Common.Common;
import com.example.atul_.eatit.ViewHolder.CartAdapter;
import com.example.atul_.eatit.ViewHolder.FavoritesAdapter;
import com.example.atul_.eatit.model.Favorites;
import com.example.atul_.eatit.model.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;


    List<Favorites> fav = new ArrayList<>();

    FavoritesAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_favorites);

        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        recyclerView=(RecyclerView)findViewById(R.id.recycler_fav);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadListFav();


    }

    private void loadListFav() {

        fav = new Database(this).getFav();
        adapter = new FavoritesAdapter(this,fav);
        recyclerView.setAdapter(adapter);
       

    }
}
