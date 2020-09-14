package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button btnAdd;
    EditText etItem;
    RecyclerView rvitems;
    ItemsAdapter itemsAdapter;
    private android.util.Log Log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvitems = findViewById(R.id.rvitems);

        loadItems();

        ItemsAdapter.OnLongClickListner onLongClickListner =  new ItemsAdapter.OnLongClickListner(){
            @Override
            public void onItemLongClick(int position) {
//                Delete the item from the model
                items.remove(position);
//                Notify the adapter which position we deleted from
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", LENGTH_SHORT).show();
                saveItems();
            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListner);
        rvitems.setAdapter(itemsAdapter);
        rvitems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
//                Add item to model
                items.add(todoItem);
//                Notify adapter that item has been inserted
                itemsAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "Data.txt");
    }
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity.java", "Error reading items", e);
            items = new ArrayList<>();
        }
    }
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity.java", "Error writing items", e);
        }
    }
}