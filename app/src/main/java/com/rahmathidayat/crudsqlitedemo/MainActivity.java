package com.rahmathidayat.crudsqlitedemo;

import static com.rahmathidayat.crudsqlitedemo.R.id.edtname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static DBmain dBmain;
    private static EditText edtname;
    private static Button submit,edit,display;
    private static SQLiteDatabase sqLiteDatabase;
    private static int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dBmain = new DBmain(MainActivity.this);
        findid();
        getData();
        clear();
        editData();
    }

    private void editData() {
        if (getIntent().getBundleExtra("userdata") != null){
            Bundle bundle = getIntent().getBundleExtra("userdata");
            id = bundle.getInt("id");
            edtname.setText(bundle.getString("name"));
            edit.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        }
    }

    private void clear() {
        edtname.setText("");
    }

    private void getData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name",edtname.getText().toString());
                sqLiteDatabase=dBmain.getWritableDatabase();
                Long recid = sqLiteDatabase.insert("thing",null,contentValues);
                if (recid!=null){
                    Toast.makeText(MainActivity.this,"Data inserted successfully",Toast.LENGTH_SHORT).show();
                    clear();
                }
                else{
                    Toast.makeText(MainActivity.this,"something wrong try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DisplayData.class));
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name",edtname.getText().toString());

                sqLiteDatabase=dBmain.getWritableDatabase();
                long recid = sqLiteDatabase.update("thing",contentValues,"id="+id,null);
                if (recid!=-1){
                    Toast.makeText(MainActivity.this,"Data update successfully",Toast.LENGTH_SHORT).show();
                    submit.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.GONE);
                    clear();
                }
                else{
                    Toast.makeText(MainActivity.this,"something wrong try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findid() {
        edtname = findViewById(R.id.edtname);
        submit = findViewById(R.id.btn_submit);
        edit = findViewById(R.id.btn_edit);
        display = findViewById(R.id.btn_display);

    }
}