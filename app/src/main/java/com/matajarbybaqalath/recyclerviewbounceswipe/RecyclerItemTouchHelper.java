package com.matajarbybaqalath.recyclerviewbounceswipe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/*
 * custom touch helper implementation
 * */
public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    Context context;
    ItemSwipe itemSwipe;
    private boolean swipeBack = false;

    private float x1, x2;
    static final int MIN_DISTANCE = 200;

    public RecyclerItemTouchHelper(Context context, ItemSwipe itemSwipe, int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
        this.context = context;
        this.itemSwipe = itemSwipe;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof AdapterMain.HeaderVH) return 0; // to prevent header item swipe
        return super.getSwipeDirs(recyclerView, viewHolder);

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull final RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder.getAdapterPosition() > 0) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(context, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeLeftLabel("Remove")
                    .setSwipeLeftLabelColor(Color.WHITE)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .addSwipeRightLabel("Move to Wishlist")
                    .setSwipeRightLabelColor(Color.WHITE)
                    .setActionIconTint(Color.WHITE)
                    .create()
                    .decorate();

            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            x1 = event.getX();
                            break;
                        case MotionEvent.ACTION_UP:
                            x2 = event.getX();
                            float deltaX = x2 - x1;

                            if (Math.abs(deltaX) > MIN_DISTANCE) {
                                // Left to Right swipe action
                                if (x2 > x1) {
                                    Toast.makeText(context, "Left to Right swipe", Toast.LENGTH_SHORT).show();
                                    itemSwipe.onSwipeLeft(viewHolder, viewHolder.getAdapterPosition());
                                }

                                // Right to left swipe action
                                else {
                                    Toast.makeText(context, "Right to Left swipe", Toast.LENGTH_SHORT).show();
                                    itemSwipe.onSwipeRight(viewHolder, viewHolder.getAdapterPosition());
                                }

                            } else {
                                // consider as something else - a screen tap for example
                            }
                            break;
                    }

                    if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                        swipeBack = true;
                    } else {
                        swipeBack = false;
                    }
                    return false;
                }
            });
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    //callback interface
    public interface ItemSwipe {
        void onSwipeLeft(RecyclerView.ViewHolder viewHolder, int position);

        void onSwipeRight(RecyclerView.ViewHolder viewHolder, int position);

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return swipeBack ? 0 : super.convertToAbsoluteDirection(flags, layoutDirection);
    }

}
