package com.example.amansingh.library;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class view_books extends AppCompatActivity {

    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ListView books;
    SQLiteDatabase db;
    Button search;
    EditText search_book;
    String status;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);
        search= (Button)findViewById(R.id.search_book_btn);
        search_book = (EditText) findViewById(R.id.search_book);
        books = (ListView)findViewById(R.id.books_list);
        db = this.openOrCreateDatabase("library",MODE_PRIVATE,null);
        books.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (search_book.getText().toString().length() == 0 && count == 0) {
                        Cursor c = db.rawQuery("select * from books", null);
                        c.move(position + 1);
                        Intent i = new Intent(getApplicationContext(), book_details.class);
                        i.putExtra("id", c.getInt(c.getColumnIndex("id")));
                        startActivity(i);
                        finish();
                    } else if (search_book.getText().toString().length() > 0 && count > 0) {
                        Cursor c = db.rawQuery("Select * from books where name like '" + search_book.getText().toString() + "%'", null);
                        c.move(position + 1);
                        Log.i("id", "" + c.getInt(c.getColumnIndex("id")));
                        Intent i = new Intent(getApplicationContext(), book_details.class);
                        i.putExtra("id", c.getInt(c.getColumnIndex("id")));
                        startActivity(i);
                        finish();
                    }
                }
                catch(Exception e)
                {
                 e.printStackTrace();
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = search_book.getText().toString();
                if(search.length()!=0)
                {
                    getsearchbook(search);
                    count++;
                }
                else
                if(search.length()==0)
                {
                    Toast.makeText(view_books.this,"Input Required",Toast.LENGTH_LONG).show();
                }
            }
        });
        getbooks();
    }
    void getbooks()
    {
        list = new ArrayList<String>();
        try{
            Cursor c = db.rawQuery("Select * from books",null);
            c.moveToFirst();
            int nameindex = c.getColumnIndex("name");
            int statusid = c.getColumnIndex("status");
            while(c!=null)
            {
                if(c.getInt(statusid)!=0)
                {
                     status = "Issued";
                }
                else
                    if(c.getInt(statusid)==0)
                    {
                        status = "Not Issued";
                    }
                    list.add("\n  Book Name - "+c.getString(nameindex)+"\n  Status - "+status+"\n");
                c.moveToNext();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
            books.setAdapter(adapter);
        }
    }
    void getsearchbook(String val)
    {
        try {
            list = new ArrayList<String>();
            Cursor c = db.rawQuery("Select * from books where name like '"+val+"%'", null);
            c.moveToFirst();
            int nameindex = c.getColumnIndex("name");
            int statusid = c.getColumnIndex("status");
            while (c != null) {
                if(c.getInt(statusid)!=0)
                {
                    status = "Issued";
                }
                else
                if(c.getInt(statusid)==0)
                {
                    status = "Not Issued";
                }
                list.add("\n  Book Name - "+c.getString(nameindex)+"\n  Status - "+status+"\n");
                c.moveToNext();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
            books.setAdapter(adapter);
        }
    }
}
