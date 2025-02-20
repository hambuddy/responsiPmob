package com.example.responsi.adapters;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.responsi.R;

public class CustomCursorAdapter extends CursorAdapter {

    private LayoutInflater ly;
    private SparseBooleanArray mSelectedItems;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CustomCursorAdapter(Context context, Cursor c, int flags){
        super(context, c, flags);
        ly = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSelectedItems = new SparseBooleanArray();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = ly.inflate(R.layout.row_data, viewGroup, false);
        MyHolder holder = new MyHolder();

        holder.ListID = (TextView)v.findViewById(R.id.listID);
        holder.ListBarang = (TextView)v.findViewById(R.id.ListBarang);
        holder.ListHarga = (TextView)v.findViewById(R.id.listHarga);
        holder.ListDeskripsi = (TextView)v.findViewById(R.id.listDeskripsi);
        holder.ListStatus = (TextView)v.findViewById(R.id.listStatus);

        v.setTag(holder);
        return v;

    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder) view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(DBHelper2.row_id)));
        holder.ListBarang.setText   ("Nama Barang      : " + cursor.getString(cursor.getColumnIndex(DBHelper2.row_nama)));
        holder.ListHarga.setText    ("Harga Barang      : " + cursor.getString(cursor.getColumnIndex(DBHelper2.row_harga)));
        holder.ListDeskripsi.setText("Stok Barang         : " + cursor.getString(cursor.getColumnIndex(DBHelper2.row_deskripsi)));
        holder.ListStatus.setText(cursor.getString(cursor.getColumnIndex(DBHelper2.row_status)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListBarang;
        TextView ListHarga;
        TextView ListDeskripsi;
        TextView ListStatus;
    }
}
