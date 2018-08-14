package com.example.saleh.project8;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.saleh.project8.data.contract;

/**
 * Created by saleh on 19/06/18.
 */

public class Adapter extends CursorAdapter {
    public Adapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.layout, viewGroup, false);

    }

    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {
        final int columnIdIndex = cursor.getColumnIndex(contract.Entry._ID);

        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceView = (TextView) view.findViewById(R.id.price);
        TextView quantityView = (TextView) view.findViewById(R.id.qquantity);
        int nameColumnIndex = cursor.getColumnIndex(contract.Entry.name);
        int priceColumnIndex = cursor.getColumnIndex(contract.Entry.price);
        int quantityColumnIndex = cursor.getColumnIndex(contract.Entry.quantity);
        final String itemID = cursor.getString(columnIdIndex);

        String Name = cursor.getString(nameColumnIndex);
         String Price = cursor.getString(priceColumnIndex);
         final String Quantity = cursor.getString(quantityColumnIndex);
        nameTextView.setText(Name);
        priceView.setText(Price);
        quantityView.setText(Quantity);
        Button button =(Button)view.findViewById(R.id.sale);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    MainActivity Activity = (MainActivity) context;
                Activity.itemSaleCount(Integer.valueOf(itemID), Integer.valueOf(Quantity));
                }
            });
        Button productEditButton = view.findViewById(R.id.edit);
        productEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Edit.class);
                Uri currentProdcuttUri = ContentUris.withAppendedId(contract.Entry.CONTENT_URI, Long.parseLong(itemID));
                intent.setData(currentProdcuttUri);
                context.startActivity(intent);
            }
        });
        Button details = view.findViewById(R.id.details);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Details.class);
                Uri currentProdcuttUri = ContentUris.withAppendedId(contract.Entry.CONTENT_URI, Long.parseLong(itemID));
                intent.setData(currentProdcuttUri);
                context.startActivity(intent);
            }
        });


    }
}
