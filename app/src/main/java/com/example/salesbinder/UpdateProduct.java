package com.example.salesbinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateProduct extends AppCompatActivity {
    private ArrayList<ItemModel> itemList;
    private RecyclerView recyclerView;

    FirebaseDatabase db;
    DatabaseReference dbRef;
    RecyclerViewAdapter adapter;

    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);



        recyclerView = findViewById(R.id.recycler_view);
        toolbar = findViewById(R.id.update_product_toolbar);
        itemList = new ArrayList<>();

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("");
        // retrieving data form firebase
        populateItemList();
        setAdapter();
    }

    private void setAdapter() {
         adapter = new RecyclerViewAdapter(itemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);



    }

    private void populateItemList() {

        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference().child("Items");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                  itemList.clear();
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren() ){
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){

                    ItemModel item = dataSnapshot2.getValue(ItemModel.class);

                    itemList.add(item);

                    Toast.makeText(getApplicationContext(),"hello "+ item.getNAME(),Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

//         itemList.add(new ItemModel("ChiniGura chal",
//                 "pulao chal",
//                 "Rice",
//                 "01/02/2022",
//                 "AbcXyz",
//                 10,
//                 20,
//                 1000,
//                 3,
//                 true
//                 ));
//
//        itemList.add(new ItemModel("Katari chal",
//                "chal",
//                "Rice",
//                "01/02/2022",
//                "AbcXzz",
//                20,
//                20,
//                1560,
//                3,
//                true
//                ));



    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id ==  R.id.search_view_menu){
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search_view_menu);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

              adapter.getFilter().filter(newText);
                return true;
            }
        });


        return  true;
    }
}