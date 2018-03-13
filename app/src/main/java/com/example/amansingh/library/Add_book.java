package com.example.amansingh.library;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_book extends AppCompatActivity {

    EditText Book_id;
    EditText Book_name;
    EditText Author_name;
    Button save;
    Button cancel;
    SQLiteDatabase db;
    int id;
    String name , author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        Book_id = (EditText) findViewById(R.id.book_id);
        Book_name = (EditText)findViewById(R.id.book_name);
        Author_name = (EditText)findViewById(R.id.author_name);
        save = (Button)findViewById(R.id.save_book);
        cancel = (Button)findViewById(R.id.cancel_book);

        try{
             db = this.openOrCreateDatabase("library",MODE_PRIVATE,null);
             db.execSQL("CREATE TABLE IF NOT EXISTS books(id INTEGER PRIMARY KEY,name VARCHAR,author VARCHAR,status BOOLEAN)");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               name = Book_name.getText().toString();
               author = Author_name.getText().toString();
               save_book(name,author);
            }
        });
    }
    void save_book(String book,String Author)
    {
        if(Book_id.getText().toString().length()!=0 && book.length()!=0 && Author.length()!=0) {
            id = Integer.parseInt(Book_id.getText().toString());
            if (id > 1000) {
                db.execSQL("INSERT INTO books VALUES('" + id + "','" + book + "','" + Author + "',0)");
                Toast.makeText(this,"Book Added",Toast.LENGTH_LONG).show();
            } else
            {
                Toast.makeText(this,"Invalid ID Pattern",Toast.LENGTH_LONG).show();
            }
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this,"Invalid Details",Toast.LENGTH_LONG).show();
        }
    }
}
