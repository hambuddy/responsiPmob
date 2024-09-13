package com.example.responsi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.responsi.adapters.DBHelper2;

import java.text.DateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    DBHelper2 dbHelper2;
    Button BtnProses;
    EditText TxtId, TxtJudul, TxtHarga, TxtStok, TxStatus;
    long id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        this.setTitle("Input Data");

        dbHelper2 = new DBHelper2(this);

        id = getIntent().getLongExtra(DBHelper2.row_id, 0);

        TxtId = (EditText)findViewById(R.id.txtId);
        TxtJudul = (EditText)findViewById(R.id.txtJudul);
        TxtHarga = (EditText)findViewById(R.id.txtHarga);
        TxtStok = (EditText)findViewById(R.id.txtStok);
        TxStatus = (EditText)findViewById(R.id.txStatus);
        BtnProses = (Button)findViewById(R.id.btnProses);

        getData();

        BtnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesEdit();
            }
        });

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
    }

    private void prosesEdit() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setMessage("Proses Edit Barang ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Proses", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertAndUpdate();
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getData() {

        Cursor cur = dbHelper2.oneData(id);
        if (cur.moveToFirst()){
            @SuppressLint("Range") String idBarang = cur.getString(cur.getColumnIndex(DBHelper2.row_id));
            @SuppressLint("Range") String nama = cur.getString(cur.getColumnIndex(DBHelper2.row_nama));
            @SuppressLint("Range") String harga = cur.getString(cur.getColumnIndex(DBHelper2.row_harga));
            @SuppressLint("Range") String deskripsi = cur.getString(cur.getColumnIndex(DBHelper2.row_deskripsi));

            TxtId.setText(idBarang);
            TxtJudul.setText(nama);
            TxtHarga.setText(harga);
            TxtStok.setText(deskripsi);

            if (TxtId.equals("")){
                BtnProses.setVisibility(View.GONE);
            }else {
                BtnProses.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        String idpinjam = TxtId.getText().toString().trim();

        MenuItem itemDelete = menu.findItem(R.id.action_delete);
        MenuItem itemClear = menu.findItem(R.id.action_clear);
        MenuItem itemSave = menu.findItem(R.id.action_save);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_save:
                insertAndUpdate();
        }
        switch (item.getItemId()){
            case R.id.action_clear:
                TxtJudul.setText("");
                TxtHarga.setText("");
                TxtStok.setText("");
        }
        switch (item.getItemId()){
            case R.id.action_delete:
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                builder.setMessage("Data ini akan dihapus");
                builder.setCancelable(true);
                builder.setPositiveButton("hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper2.deleteData(id);
                        Toast.makeText(AddActivity.this,"Terhapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void insertAndUpdate(){
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        TxStatus.setText(currentDateTimeString);
        String idBarang = TxtId.getText().toString().trim();
        String judul = TxtJudul.getText().toString().trim();
        String harga = TxtHarga.getText().toString().trim();
        String deskripsi = TxtStok.getText().toString().trim();
        String status = TxStatus.getText().toString().trim();

        ContentValues values = new ContentValues();

        values.put(DBHelper2.row_nama, judul);
        values.put(DBHelper2.row_deskripsi, deskripsi);
        values.put(DBHelper2.row_harga, harga);

        if (judul.equals("")){
            TxtJudul.setError("Nama Tidak Boleh Kosong!");
        }
        if (harga.equals("")){
            TxtHarga.setError("Harga Tidak Boleh Kosong!");
        }
        if (deskripsi.equals("")){
            TxtStok.setError("Stok Tidak Boleh Kosong!");
        }
        if (judul.equals("") || harga.equals("") || deskripsi.equals("")){
            Toast.makeText(AddActivity.this, "Isi data dengan Lengkap!", Toast.LENGTH_SHORT).show();
        }else {
            if (idBarang.equals("")){
                values.put(DBHelper2.row_status, status);
                dbHelper2.insertData(values);
            }else {
                dbHelper2.updateData(values, id);
            }

            Toast.makeText(AddActivity.this,"Data Tersimpan", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}