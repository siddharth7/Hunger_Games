package com.example.siddharth.hungergames;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by siddharth on 11/21/16.
 */
public class ReviewMenu extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE =1 ;
    private List<ReviewOrderclass> reviewOrderclasses;
    TextView totalbill;
    EditText mNumber;
    Button mSplit, mPlace;
    String order="";
    String amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewmenu);
        Intent intent = getIntent();
        totalbill = (TextView) findViewById(R.id.total_amount);
        mSplit = (Button) findViewById(R.id.split_btn);
        mPlace = (Button) findViewById(R.id.place_order);
        mNumber = (EditText) findViewById(R.id.number);
        mSplit.setOnClickListener(this);
        mPlace.setOnClickListener(this);
        String size = intent.getStringExtra("size");
        int count = Integer.parseInt(size.toString());
        int tot = 0;
        reviewOrderclasses = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String name = intent.getStringExtra("n" + i);
            int quan = Integer.parseInt(intent.getStringExtra("q" + i));
            int price = Integer.parseInt(intent.getStringExtra("p" + i));
            int total_price = quan * price;
            tot += total_price;
            order+=name.replaceAll(" ","");
            reviewOrderclasses.add(new ReviewOrderclass(name, quan, price, total_price));
        }
        totalbill.setText("Total : " + tot);
        amount= String.valueOf(tot);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        ReviewAdapter adapter = new ReviewAdapter(reviewOrderclasses);
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.place_order) {
            new RequestTask().execute("http://192.168.56.74:8000/add_order/?order_item=" + order + "&order_amount=" + amount + "&order_number=" + mNumber.getText().toString());
        }
        else if (v.getId() == R.id.split_btn) {
            Uri uri = Uri.parse("content://contacts");
            Intent intent = new Intent(Intent.ACTION_PICK, uri);
            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = intent.getData();
                String[] projection = { ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

                Cursor cursor = getContentResolver().query(uri, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex);

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);

                Log.d("Number found", "ZZZ number : " + number +" , name : "+name);
                cursor.close();
            }
        }
    };
    class RequestTask extends AsyncTask<String, String, String> {

        ProgressDialog dialog = new ProgressDialog(ReviewMenu.this);
        @Override
        protected void onPreExecute() {
            // Show Progress dialog
            dialog.setMessage("Ordering..");
            dialog.show();
        }
        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                Log.d("url", uri[0]);
                StatusLine statusLine = response.getStatusLine();
                Log.d("serversentdata",statusLine.toString());
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            super.onPostExecute(result);
            //Do anything with response..
            Log.d("RES", result);
            Toast.makeText(getApplicationContext(), "Order Placed Succeefully", Toast.LENGTH_SHORT).show();
        }
    }

}
