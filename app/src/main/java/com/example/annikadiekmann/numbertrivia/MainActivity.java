package com.example.annikadiekmann.numbertrivia;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<NumberItem> trivias;
    private RecyclerView mRecyclerView;
    private NumberAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        trivias = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NumberAdapter(trivias);
        mRecyclerView.setAdapter(mAdapter);

        // Adds a line between each item
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestData();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void requestData() {
        NumberAPIServices service = NumberAPIServices.retrofit.create(NumberAPIServices.class);
        Call<NumberItem> call = service.getRandomnumber();

        call.enqueue(new Callback<NumberItem>() {
            @Override
            public void onResponse(Call<NumberItem> call, Response<NumberItem> response) {
                NumberItem dayQuoteItem = response.body();
                Log.i(TAG, "text: "+dayQuoteItem.getText());
                Log.i(TAG," "+dayQuoteItem);

                trivias.add(0,new NumberItem(dayQuoteItem.getText(), dayQuoteItem.getNumber()));
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NumberItem> call, Throwable t) {
                Log.i(TAG," "+t.getMessage());

            }
        });


    }
}
