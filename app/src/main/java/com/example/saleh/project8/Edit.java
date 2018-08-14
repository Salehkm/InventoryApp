package com.example.saleh.project8;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saleh.project8.data.contract;

public class Edit extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER = 0;
    private Uri mCurrentitemUri;
    private EditText mProductName;
    private EditText mPrice;
    private EditText mQuantity;
    private EditText mSupplierName;
    private EditText mSupplierPhone;
    private boolean mPetHasChanged = false;
    private boolean mProductHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mProductHasChanged = true;

            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        mCurrentitemUri=intent.getData();
        if (mCurrentitemUri == null) {
            setTitle("add item");
            invalidateOptionsMenu();
        } else {
            setTitle("edit item");
            getLoaderManager().initLoader(LOADER, null, this);
        }
        mProductName =(EditText)findViewById(R.id.productName);
        mPrice =(EditText) findViewById(R.id.price);
        mQuantity =(EditText)findViewById(R.id.quantity);
        mSupplierName =(EditText) findViewById(R.id.supplierName);
        mSupplierPhone =(EditText)findViewById(R.id.phone);
        mProductName.setOnTouchListener(mTouchListener);
        mPrice.setOnTouchListener(mTouchListener);
        mQuantity.setOnTouchListener(mTouchListener);
        mSupplierName.setOnTouchListener(mTouchListener);
        mSupplierPhone.setOnTouchListener(mTouchListener);
    }
    private void savePet() {
        String nameString = mProductName.getText().toString().trim();
        String priceString = mPrice.getText().toString().trim();
        String quantityString = mQuantity.getText().toString().trim();
        String supplierString = mSupplierName.getText().toString().trim();
        String phoneString = mSupplierPhone.getText().toString().trim();
        if (mCurrentitemUri == null) {
            if (TextUtils.isEmpty(nameString)) {
                Toast.makeText(this, "product name required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(priceString)) {
                Toast.makeText(this, "product price required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(quantityString)) {
                Toast.makeText(this, "product quantity required", Toast.LENGTH_SHORT).show();
                return;
            } if (TextUtils.isEmpty(supplierString)) {
                Toast.makeText(this, "supplier name required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(phoneString)) {
                Toast.makeText(this, "supplier name required", Toast.LENGTH_SHORT).show();
                return;
            }
        ContentValues values = new ContentValues();
        values.put(contract.Entry.name, nameString);
        values.put(contract.Entry.price, priceString);
            values.put(contract.Entry.quantity, quantityString);
            values.put(contract.Entry.supplierphone, phoneString);
        values.put(contract.Entry.suppliername, supplierString);
            Uri newUri = getContentResolver().insert(contract.Entry.CONTENT_URI, values);
         if (newUri == null) {
                Toast.makeText(this, "faild",
                        Toast.LENGTH_SHORT).show();
            } else {
             Toast.makeText(this, "successful",
                     Toast.LENGTH_SHORT).show();
         }
        }else{   if (TextUtils.isEmpty(nameString)) {
            Toast.makeText(this, "product name required", Toast.LENGTH_SHORT).show();
            return;
        }
            if (TextUtils.isEmpty(priceString)) {
                Toast.makeText(this, "product price required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(quantityString)) {
                Toast.makeText(this, "product name required", Toast.LENGTH_SHORT).show();
                return;
            } if (TextUtils.isEmpty(supplierString)) {
                Toast.makeText(this, "supplier name required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(phoneString)) {
                Toast.makeText(this, "supplier name required", Toast.LENGTH_SHORT).show();
                return;
            }

                ContentValues values = new ContentValues();

                values.put(contract.Entry.name, nameString);
                values.put(contract.Entry.price, priceString);
                values.put(contract.Entry.quantity, quantityString);
                values.put(contract.Entry.suppliername, supplierString);
                values.put(contract.Entry.supplierphone, phoneString);


                int rowsAffected = getContentResolver().update(mCurrentitemUri, values, null, null);
                if (rowsAffected == 0) {
                    Toast.makeText(this, "update failed",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
    }
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (mCurrentitemUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                savePet();
                finish();
                return true;
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;

            case android.R.id.home:
                if (!mPetHasChanged) {
                    NavUtils.navigateUpFromSameTask(Edit.this);
                    return true;
                }
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(Edit.this);
                            }
                        };
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // If the pet hasn't changed, continue with handling back button press
        if (!mPetHasChanged) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Since the editor shows all pet attributes, define a projection that contains
        // all columns from the pet table
        String[] projection = {
                contract.Entry._ID,
                contract.Entry.name,
                contract.Entry.price,
                contract.Entry.quantity,
                contract.Entry.supplierphone,
                contract.Entry.suppliername};
        return new CursorLoader(this,   // Parent activity context
                mCurrentitemUri,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(contract.Entry.name);
            int priceColumnIndex = cursor.getColumnIndex(contract.Entry.price);
            int quantityColumnIndex = cursor.getColumnIndex(contract.Entry.quantity);
            int supplierColumnIndex = cursor.getColumnIndex(contract.Entry.suppliername);
            int phoneColumnIndex = cursor.getColumnIndex(contract.Entry.supplierphone);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            int price = cursor.getInt(priceColumnIndex);
            int quantity = cursor.getInt(quantityColumnIndex);
            String supplier = cursor.getString(supplierColumnIndex);
            int phone = cursor.getInt(phoneColumnIndex);
            mProductName.setText(name);
            mPrice.setText(Integer.toString(price));
            mQuantity.setText(Integer.toString(quantity));
            mSupplierName.setText(supplier);
            mSupplierPhone.setText(Integer.toString(phone));


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mProductName.setText("");
        mPrice.setText("");
        mQuantity.setText("");
        mSupplierPhone.setText("");
        mSupplierName.setText("");
    }
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("unsaved will be lost");
        builder.setPositiveButton("discard?", discardButtonClickListener);
        builder.setNegativeButton("stay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deletePet() {
        // Only perform the delete if this is an existing pet.
        if (mCurrentitemUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentitemUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, "failed",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, "deleted",
                        Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("delete?");
        builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deletePet();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
