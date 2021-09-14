package com.asadrao.textreplacer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.asadrao.textreplacer.R;
import com.asadrao.textreplacer.SaveDataAdapter;
import com.asadrao.textreplacer.SaveModel;
import com.asadrao.textreplacer.utils.Database;

import java.util.ArrayList;

public class ShowSavedDataActivity extends AppCompatActivity {
    Database database;
    RecyclerView rvSaveData;
    ArrayList<SaveModel> saveModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_data);

        setTitle("Saved Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = new Database(ShowSavedDataActivity.this);
        rvSaveData = findViewById(R.id.rvSaveData);
        rvSaveData.setLayoutManager(new LinearLayoutManager(this));
        rvSaveData.setHasFixedSize(true);
        if (database.getAllSavedData() != null) {
            saveModelArrayList.clear();
            saveModelArrayList = database.getAllSavedData();
            rvSaveData.setAdapter(new SaveDataAdapter(ShowSavedDataActivity.this, saveModelArrayList));
        } else {
            Toast.makeText(this, "No Saved Data", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}