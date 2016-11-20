package com.example.siddharth.hungergames;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<CanteenMenu> canteenMenus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getIntent().setAction("Already created");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("menu", String.valueOf(breakfastMenus.get(0).quantity));
                int x= canteenMenus.size();
                int counter = 0;
                Intent intent=new Intent(getApplicationContext(), ReviewMenu.class);
                for(int i=0;i<x;i++)
                {
                    if(canteenMenus.get(i).quantity!=0) {

                        intent.putExtra("n" + counter, canteenMenus.get(i).name);
                        intent.putExtra("q" + counter, ""+canteenMenus.get(i).quantity);
                        intent.putExtra("p" + counter, ""+canteenMenus.get(i).price);
                        counter++;
                    }

                }
                if(counter==0)
                    Snackbar.make(view, "Please select some dishes", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else
                {
                    Log.d("Counter", "" + counter);
                    intent.putExtra("size", "" + counter);
                    startActivity(intent);
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView rv = (RecyclerView)findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        initializeData();
        RVAdapter adapter = new RVAdapter(canteenMenus);
        rv.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void initializeData() {
        canteenMenus = new ArrayList<>();
        canteenMenus.add(new CanteenMenu("Aloo Parantha", 60, R.drawable.emma,0));
        canteenMenus.add(new CanteenMenu("Aloo Puri", 50, R.drawable.lavery,0));
        canteenMenus.add(new CanteenMenu("Idli Wada", 50, R.drawable.lillie,0));
        canteenMenus.add(new CanteenMenu("Aloo Parantha", 60, R.drawable.emma,0));
        canteenMenus.add(new CanteenMenu("Aloo Puri", 50, R.drawable.lavery,0));
        canteenMenus.add(new CanteenMenu("Idli Wada", 50, R.drawable.lillie,0));
        canteenMenus.add(new CanteenMenu("Aloo Parantha", 60, R.drawable.emma,0));
        canteenMenus.add(new CanteenMenu("Aloo Puri", 50, R.drawable.lavery,0));
        canteenMenus.add(new CanteenMenu("Idli Wada", 50, R.drawable.lillie,0));
    }
        @Override
    protected void onResume() {
        Log.d("Example", "onResume");

        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if(action == null || !action.equals("Already created")) {
            Log.v("Example", "Force restart");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        // Remove the unique action so the next time onResume is called it will restart
        else
            getIntent().setAction(null);

        super.onResume();
    }
}

