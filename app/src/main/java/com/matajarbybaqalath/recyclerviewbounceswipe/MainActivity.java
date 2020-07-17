package com.matajarbybaqalath.recyclerviewbounceswipe;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerMain;
    private AdapterMain adapterMain;
    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initRecycler();
    }

    //sets properties for recyclerview
    private void initRecycler() {
        for (int i = 0; i < 20; i++) { //adds data for data list
            data.add("item " + 1);
        }
        adapterMain = new AdapterMain(data);
        recyclerMain.setAdapter(adapterMain);

        //sets recyclerview itemtouch helper
        new ItemTouchHelper(new RecyclerItemTouchHelper(getApplicationContext(), new RecyclerItemTouchHelper.ItemSwipe() {
            @Override
            public void onSwipeLeft(RecyclerView.ViewHolder viewHolder, int position) { //left swipe callback
                deleteItem(viewHolder);
            }

            @Override
            public void onSwipeRight(RecyclerView.ViewHolder viewHolder, int position) { //right swipe call back
                deleteItem(viewHolder);
            }
        }, 0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT)).attachToRecyclerView(recyclerMain);

    }

    //to show dialog and delete item
    private void deleteItem(final RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete Item?");
        builder.setMessage("Are you sure to delete the item?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapterMain.removeItem(viewHolder.getAdapterPosition());
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void initViews() {
        recyclerMain = findViewById(R.id.recyclerMain);
    }
}