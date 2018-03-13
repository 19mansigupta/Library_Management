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

public class View_student extends AppCompatActivity {

    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ListView students;
    Button search;
    EditText search_stu;
    SQLiteDatabase db;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        search= (Button)findViewById(R.id.stusearch_btn);
        search_stu = (EditText) findViewById(R.id.search_stu);
        students = (ListView)findViewById(R.id.student_list);
        db = this.openOrCreateDatabase("library",MODE_PRIVATE,null);
        students.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(search_stu.getText().toString().length() >= 0 && count == 0)
                    {
                    Cursor c = db.rawQuery("select * from students",null);
                    c.move(position+1);
                    Log.i("id",""+c.getInt(c.getColumnIndex("roll")));
                    Intent i = new Intent(getApplicationContext(),student_details.class);
                    i.putExtra("roll",c.getInt(c.getColumnIndex("roll")));
                    startActivity(i);
                    finish();
                    }
                    else
                    if(search_stu.getText().toString().length() > 0 && count>0)
                    {
                        Cursor c = db.rawQuery("Select * from students where name like '"+search_stu.getText().toString()+"%'",null);
                        c.move(position+1);
                        Log.i("id",""+c.getInt(c.getColumnIndex("roll")));
                        Intent i = new Intent(getApplicationContext(),student_details.class);
                        i.putExtra("roll",c.getInt(c.getColumnIndex("roll")));
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
                String search = search_stu.getText().toString();
                if(search.length()!=0)
                {
                    getsearchstu(search);
                    count++;
                }
                else
                if(search.length()==0)
                {
                    Toast.makeText(View_student.this,"Input Required",Toast.LENGTH_LONG).show();
                }
            }
        });
        getstudents();
    }
    void getstudents()
    {
        try {
            list = new ArrayList<String>();
            Cursor c = db.rawQuery("Select * from students", null);
            c.moveToFirst();
            while (c != null) {
                list.add("\n  Name of Student  -  " + c.getString(c.getColumnIndex("name")) + "\n  No of books issued - " + c.getInt(c.getColumnIndex("no_of_books"))+"\n");
                c.moveToNext();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
            students.setAdapter(adapter);
        }
    }
    void getsearchstu(String val)
    {
        try {
            list = new ArrayList<String>();
            Cursor c = db.rawQuery("Select * from students where name like '"+val+"%'", null);
            c.moveToFirst();
            while (c != null) {
                list.add("\n  Name of Student  -  " + c.getString(c.getColumnIndex("name")) + "\n  No of books issued - " + c.getInt(c.getColumnIndex("no_of_books"))+"\n");
                c.moveToNext();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
            students.setAdapter(adapter);
        }
    }
}
