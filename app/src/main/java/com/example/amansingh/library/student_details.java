package com.example.amansingh.library;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class student_details extends AppCompatActivity {

    TextView name;
    TextView branch;
    TextView roll;
    TextView books;
    TextView book1;
    TextView book2;
    Button issue;
    Button return1;
    Button return2;
    SQLiteDatabase db;
    int id;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        i= getIntent();
        id = i.getIntExtra("roll",0);
        name = (TextView)findViewById(R.id.student_name);
        branch = (TextView)findViewById(R.id.student_branch);
        roll = (TextView)findViewById(R.id.rollno);
        book1 = (TextView)findViewById(R.id.book1);
        book2 = (TextView)findViewById(R.id.book2);
        books = (TextView)findViewById(R.id.booksissued);
        issue = (Button)findViewById(R.id.issue_stu);
        return1 = (Button)findViewById(R.id.return_book1);
        return2 = (Button)findViewById(R.id.return_book2);
        db = this.openOrCreateDatabase("library",MODE_PRIVATE,null);
        getdetails(id);
        return1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alt = new AlertDialog.Builder(student_details.this);
                alt.setMessage("Do you want to return this book").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                book1();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = alt.create();
                alert.setTitle("Confirm");
                if (book1.getText().length() > 9)
                {
                    alert.show();
                }
            }
        });
        return2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alt2 = new AlertDialog.Builder(student_details.this);
                alt2.setMessage("Do you want to return this book").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                book2();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = alt2.create();
                alert.setTitle("Confirm");
                if (book2.getText().length() > 9) {
                    alert.show();
                }
            }
        });
        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Issue_book.class);
                i.putExtra("roll",id);
                startActivity(i);
                finish();
            }
        });
    }
    void getdetails(int id)
    {
        Cursor c = db.rawQuery("Select * from students where roll='"+id+"'",null);
        c.moveToFirst();
        name.setText(name.getText()+" "+c.getString(c.getColumnIndex("name")));
        branch.setText(branch.getText()+" "+c.getString(c.getColumnIndex("branch")));
        if(c.getInt(c.getColumnIndex("no_of_books"))<2)
        {
            if(c.getInt(c.getColumnIndex("no_of_books"))==1)
            {
                books.setText(books.getText()+" "+c.getInt(c.getColumnIndex("no_of_books")));
                book1.setText(book1.getText()+"\n"+c.getString(c.getColumnIndex("book1")));
            }
            else
            {
                books.setText(books.getText()+" "+c.getInt(c.getColumnIndex("no_of_books")));
            }
        }
        else
        {
            books.setText(books.getText() + " " + c.getInt(c.getColumnIndex("no_of_books")));
            book1.setText(book1.getText()+"\n"+c.getString(c.getColumnIndex("book1")));
            book2.setText(book2.getText()+"\n"+c.getString(c.getColumnIndex("book2")));
            issue.setEnabled(false);
        }
        roll.setText(roll.getText()+"\n"+c.getString(c.getColumnIndex("roll")));
    }

    void book1()
    {
        Cursor c= db.rawQuery("Select * from students where roll='"+id+"'",null);
        c.moveToFirst();
        int no_books = c.getInt(c.getColumnIndex("no_of_books"));
        if(no_books != 0)
        {
            no_books = c.getInt(c.getColumnIndex("no_of_books")) - 1;
        }
        db.execSQL("UPDATE students set no_of_books = '"+no_books+"'where roll='"+id+"'");
        db.execSQL("UPDATE books set status=0 where name = '"+c.getString(c.getColumnIndex("book1"))+"'");
        db.execSQL("UPDATE students set book1='' where roll='"+id+"'");
        books.setText("Books Issued "+no_books);
        book1.setText("Book 1 - ");
        Toast.makeText(this,"Your Book is returned",Toast.LENGTH_LONG).show();
        issue.setEnabled(true);
    }
    void book2()
    {
        Cursor c= db.rawQuery("Select * from students where roll='"+id+"'",null);
        c.moveToFirst();
        int no_books = c.getInt(c.getColumnIndex("no_of_books"));
        if(no_books != 0) {
            no_books = c.getInt(c.getColumnIndex("no_of_books")) - 1;
        }
        db.execSQL("UPDATE books set status=0 where name = '"+c.getString(c.getColumnIndex("book2"))+"'");
        db.execSQL("UPDATE students set book2='' where roll='"+id+"'");
        db.execSQL("UPDATE students set no_of_books = '"+no_books+"'where roll='"+id+"'");
        books.setText("Books Issued "+no_books);
        book2.setText("Book 2 - ");
        Toast.makeText(this,"Your Book is returned",Toast.LENGTH_LONG).show();
        issue.setEnabled(true);
    }
}
