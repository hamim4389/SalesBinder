package com.example.salesbinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        recyclerView = findViewById(R.id.recycler_view);
        itemList = new ArrayList<>();

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
}