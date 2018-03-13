package com.example.amansingh.library;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Issue_book extends AppCompatActivity
{
    EditText roll;
    EditText book;
    Button issue;
    Button cancel;
    SQLiteDatabase db;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);
        i = getIntent();
        roll = (EditText)findViewById(R.id.stu_roll);
        book = (EditText)findViewById(R.id.book_ID);
        issue = (Button)findViewById(R.id.issue_book);
        cancel = (Button)findViewById(R.id.cancel_book);
        if(i.hasExtra("roll"))
        {
            roll.setText(""+i.getIntExtra("roll",0));
        }
        if(i.hasExtra("book_id"))
        {
            book.setText(""+i.getIntExtra("book_id",0));
        }
            db = this.openOrCreateDatabase("library", MODE_PRIVATE, null);
            issue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check();
                }
            });
    }
    void check()
    {
        Cursor c1 = db.rawQuery("select * from students",null);
        Cursor c2 = db.rawQuery("select * from books",null);
        c1.moveToFirst();
        c2.moveToFirst();
        label: while(!c1.isAfterLast())
        {
            if(c1.getInt(c1.getColumnIndex("roll")) == Integer.parseInt(roll.getText().toString()))
            {
                while(!c2.isAfterLast())
                {
                    Log.i("id",""+c2.getInt(c2.getColumnIndex("id")));
                    Log.i("id1",""+Integer.parseInt(book.getText().toString()));
                    if(c2.getInt(c2.getColumnIndex("id")) == Integer.parseInt(book.getText().toString()))
                    {
                        bookissue(Integer.parseInt(roll.getText().toString()),Integer.parseInt(book.getText().toString()));
                        break label;
                    }
                    c2.moveToNext();
                    if(c2.isAfterLast())
                    {
                        Toast.makeText(this,"Book Does not exist",Toast.LENGTH_LONG).show();
                    }
                }
            }
            c1.moveToNext();
            if(c1.isAfterLast())
            {
                Toast.makeText(this,"Student Does not exist",Toast.LENGTH_LONG).show();
            }
        }
    }
    void bookissue(int roll,int book)
    {
        Cursor c1 = db.rawQuery("Select * from students where roll='"+roll+"'",null);
        Cursor c2 = db.rawQuery("Select * from books where id='"+book+"'",null);
        c1.moveToFirst();
        c2.moveToFirst();
        if(c1.getInt(c1.getColumnIndex("no_of_books"))<2 && c2.getInt(c2.getColumnIndex("status"))==0)
        {
            if(c1.getInt(c1.getColumnIndex("no_of_books"))==0)
            {
                db.execSQL("UPDATE students set book1 = '"+c2.getString(c2.getColumnIndex("name"))+"' where roll ='"+roll+"'");
                db.execSQL("UPDATE students set no_of_books=1 where roll='"+roll+"'");
                db.execSQL("UPDATE books set status=1 where id='"+book+"'");
            }
            else
                if(c1.getInt(c1.getColumnIndex("no_of_books"))==1)
                {
                    db.execSQL("UPDATE students set book2 = '"+c2.getString(c2.getColumnIndex("name"))+"' where roll ='"+roll+"'");
                    db.execSQL("UPDATE students set no_of_books=2 where roll='"+roll+"'");
                    db.execSQL("UPDATE books set status=1 where id='"+book+"'");
                }
            Toast.makeText(this,"Book Issued",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Either book is issued or student issued limit reached",Toast.LENGTH_LONG).show();
        }
    }
}
