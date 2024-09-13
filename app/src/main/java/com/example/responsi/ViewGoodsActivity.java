package com.example.responsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.responsi.adapters.CustomCursorAdapter;
import com.example.responsi.adapters.DBHelper2;


public class ViewGoodsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView Is;
    DBHelper2 dbHelper2;
    int listData;
    SharedPreferences viewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goods);
        this.setTitle("UADmart");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper2 = new DBHelper2(this);
        Is = (ListView)findViewById(R.id.list_barang);
        Is.setOnItemClickListener(this);

        viewData = getSharedPreferences("currentListView", 0);
        listData = viewData.getInt("currentListView", 0);

        allData();
    }

    public void allData(){
        Cursor cursor = dbHelper2.allData();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cursor, 1);
        Is.setAdapter(customCursorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout){
            Toast.makeText(ViewGoodsActivity.this, "Logout Berhasil!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ViewGoodsActivity.this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long I) {
        TextView getID = (TextView)view.findViewById(R.id.listID);
        final long id = Long.parseLong(getID.getText().toString());
        Cursor cur = dbHelper2.oneData(id);
        cur.moveToFirst();
    }

    @Override
    protected void onResume() {
        super.onResume();
        allData();
    }
}