package com.example.amansingh.library;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button issue;
    Button add_book;
    Button add_student;
    Button view_books;
    Button view_students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        issue = (Button)findViewById(R.id.issue);
        add_book = (Button)findViewById(R.id.addbook);
        add_student = (Button)findViewById(R.id.addstudent);
        view_books = (Button)findViewById(R.id.viewbooks);
        view_students = (Button)findViewById(R.id.viewstudents);

        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),Issue_book.class);
                startActivity(i);
            }
        });

        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Add_book.class);
                startActivity(i);
            }
        });

        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Add_student.class);
                startActivity(i);
            }
        });
        view_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), com.example.amansingh.library.view_books.class);
                startActivity(i);
            }
        });
        view_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), View_student.class);
                startActivity(i);
            }
        });

    }
}
