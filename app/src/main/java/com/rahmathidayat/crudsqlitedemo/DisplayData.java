package com.rahmathidayat.crudsqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayData extends AppCompatActivity {
    DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    ListView listView;
    String []name;
    int[]id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        dBmain = new DBmain(DisplayData.this);

        findid();
        dis();
    }

    private void dis() {
        sqLiteDatabase = dBmain.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from thing",null);
        if (cursor.getCount()>0){
            id = new int[cursor.getCount()];
            name=new String[cursor.getCount()];
            int i=0;
            while (cursor.moveToNext()){
                id[i]=cursor.getInt(0);
                name[i] = cursor.getString(1);
                i++;
            }
            Custom adapter = new Custom();
            listView.setAdapter(adapter);
        }
    }

    private void findid() {
        listView = findViewById(R.id.listview);
    }

    private class Custom extends BaseAdapter {

        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            ImageView edit,delete;

            convertView = LayoutInflater.from(DisplayData.this).inflate(R.layout.singledata,parent,false);
            textView = convertView.findViewById(R.id.txt_name);
            edit = convertView.findViewById(R.id.edit_data);
            delete = convertView.findViewById(R.id.delete_data);

            textView.setText(name[position]);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",id[position]);
                    bundle.putString("name",name[position]);

                    Intent intent = new Intent(DisplayData.this,MainActivity.class);
                    intent.putExtra("userdata",bundle);
                    startActivity(intent);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sqLiteDatabase = dBmain.getReadableDatabase();
                    long recd = sqLiteDatabase.delete("thing","id="+id[position],null);
                    if (recd !=-1){
                        Toast.makeText(DisplayData.this,"Record deleted successfully",Toast.LENGTH_SHORT).show();
                        dis();
                    }
                }
            });
            return convertView;
        }
    }
}