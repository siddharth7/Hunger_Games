package com.example.siddharth.hungergames;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by siddharth on 11/21/16.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.OrderViewHolder> {

    public List<ReviewOrderclass> reviewOrderclasses;

    ReviewAdapter(List<ReviewOrderclass> reviewOrderclasses) {
        this.reviewOrderclasses = reviewOrderclasses;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_items_card_view, parent, false);
        OrderViewHolder pvh = new OrderViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int i) {
        holder.orderitem.setText(reviewOrderclasses.get(i).name);
        holder.orderval.setText(""+reviewOrderclasses.get(i).quantity+" x Rs. "+ reviewOrderclasses.get(i).price+"/-");
        holder.total.setText("Rs. "+reviewOrderclasses.get(i).total_price+"/-");
    }

    @Override
    public int getItemCount() {
        return reviewOrderclasses.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView orderitem;
        TextView orderval;
        TextView total;

        OrderViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            orderitem = (TextView) itemView.findViewById(R.id.order_item);
            orderval = (TextView) itemView.findViewById(R.id.order_val);
            total = (TextView) itemView.findViewById(R.id.total_amount);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}