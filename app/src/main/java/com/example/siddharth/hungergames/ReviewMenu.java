package com.example.siddharth.hungergames;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siddharth on 11/21/16.
 */
public class ReviewMenu extends AppCompatActivity {

    private List<ReviewOrderclass> reviewOrderclasses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewmenu);
        Intent intent = getIntent();
        String size = intent.getStringExtra("size");
        int count = Integer.parseInt(size.toString());
        reviewOrderclasses = new ArrayList<>();
        for(int i=0;i<count;i++)
        {
            String name = intent.getStringExtra("n" + i);
            int quan = Integer.parseInt(intent.getStringExtra("q" + i));
            int price = Integer.parseInt(intent.getStringExtra("p" + i));
            int total_price = quan * price;
            reviewOrderclasses.add(new ReviewOrderclass(name, quan, price, total_price));
        }
        RecyclerView rv = (RecyclerView)findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        ReviewAdapter adapter = new ReviewAdapter(reviewOrderclasses);
        rv.setAdapter(adapter);
    }
}
