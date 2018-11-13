package com.simple.simplecontactlist.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simple.simplecontactlist.R;
import com.simple.simplecontactlist.modelclasses.Contact;
import com.simple.simplecontactlist.recycler_click_listener.ClickListener;
import com.simple.simplecontactlist.recycler_click_listener.RecyclerTouchListener;
import com.simple.simplecontactlist.recycleradapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String json_url = "http://jsonplaceholder.typicode.com/users";

    private RequestQueue requestQueue;
    private Gson gson;

    // private  List<Contact> posts;
    public static List<Contact> contactList = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList.clear();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        LinearLayoutManager mLManager_efct1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLManager_efct1);
        recyclerView.setHasFixedSize(true);


        requestQueue = Volley.newRequestQueue(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        fetchContactlist();


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                Intent intent = new Intent(MainActivity.this, Contact_Details_Activity.class);
                intent.putExtra("selectedFramePosition", position);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void fetchContactlist() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, json_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (pDialog != null) {
                    pDialog.dismiss();
                    pDialog = null;
                }

                List<Contact> contact_response = Arrays.asList(gson.fromJson(response, Contact[].class));
                for (Contact contact : contact_response) {
                    contactList.add(contact);
                }
                recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, contactList);
                recyclerView.setAdapter(recyclerViewAdapter);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ascending_action) {

            Collections.sort(contactList, new Comparator<Contact>() {
                public int compare(Contact m1, Contact m2) {
                    return m1.getName().compareTo(m2.getName());
                }
            });
            recyclerViewAdapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.descending_action) {
            Collections.sort(contactList, new Comparator<Contact>() {
                public int compare(Contact m1, Contact m2) {
                    return m2.getName().compareTo(m1.getName());
                }
            });
            recyclerViewAdapter.notifyDataSetChanged();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            recyclerViewAdapter.notifyDataSetChanged();
        } catch (Exception e) {

        }
    }
}
