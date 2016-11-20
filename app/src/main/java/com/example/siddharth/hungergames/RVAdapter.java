package com.example.siddharth.hungergames;

/**
 * Created by siddharth on 11/21/16.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by siddharthsingh on 06/05/16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    public List<CanteenMenu> canteenMenus;

    RVAdapter(List<CanteenMenu> canteenMenus){
        this.canteenMenus = canteenMenus;
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView personName;
        TextView personAge;
        EditText val;
        FloatingActionButton fab,fab2;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            fab =(FloatingActionButton)itemView.findViewById(R.id.fab);
            fab2=(FloatingActionButton)itemView.findViewById(R.id.fab2);
            val=(EditText)itemView.findViewById(R.id.item_val);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);

            fab.setOnClickListener(this);
            fab2.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.fab2)
            {
                int x= Integer.parseInt(val.getText().toString());
                x++;
                canteenMenus.get(getAdapterPosition()).quantity=x;
                val.setText(Integer.toString(x));

            }
            else if(v.getId()==R.id.fab)
            {
                int x= Integer.parseInt(val.getText().toString());
                if(x==0)
                {

                }
                else
                {
                    x--;
                    val.setText(Integer.toString(x));
                }
            }
        }
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int i) {
        holder.personName.setText(canteenMenus.get(i).name);
        holder.personAge.setText("Rs. " + canteenMenus.get(i).price + "/- (incl.tax)");
        holder.personPhoto.setImageResource(canteenMenus.get(i).photoId);
//        new ImageLoadTask(canteenMenus.get(i).photoId, holder.personPhoto).execute();
        holder.val.setText(Integer.toString(canteenMenus.get(i).quantity));
    }

    @Override
    public int getItemCount() {
        return canteenMenus.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }


}