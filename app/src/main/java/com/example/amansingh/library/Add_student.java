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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Add_student extends AppCompatActivity {

    SQLiteDatabase db;
    EditText roll;
    EditText student_name;
    EditText student_branch;
    RadioGroup group;
    RadioButton gender;
    Button save;
    Button cancel;
    int roll_number;
    String name , branch, stu_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        roll = (EditText)findViewById(R.id.roll);
        student_name = (EditText)findViewById(R.id.stu_name);
        student_branch = (EditText)findViewById(R.id.stu_branch);
        group = (RadioGroup)findViewById(R.id.radioSex);
        save = (Button)findViewById(R.id.save_stu);
        cancel = (Button)findViewById(R.id.cancel_stu);

try {
    db = this.openOrCreateDatabase("library", MODE_PRIVATE, null);
    db.execSQL("CREATE TABLE IF NOT EXISTS students(roll INTEGER PRIMARY KEY,name VARCHAR,branch VARCHAR, gender VARHCAR,no_of_books INTEGER,book1 VARCHAR,book2 VARCHAR)");
}
catch(Exception e)
{
    e.printStackTrace();
}
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                gender = (RadioButton)findViewById(group.getCheckedRadioButtonId());
                stu_gender = gender.getText().toString();
                name = student_name.getText().toString();
                branch = student_branch.getText().toString();
                save_student(name,branch,stu_gender);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }
    void save_student(String stu_name,String stu_branch,String gender)
    {
         try {
             if (stu_name.length() != 0 && stu_branch.length() != 0 && gender.length() != 0 && (roll.getText().toString()).length() != 0)
             {
                 roll_number = Integer.parseInt(roll.getText().toString());
                 if(roll_number>300) {
                     db.execSQL("INSERT INTO students VALUES('" + roll_number + "','" + stu_name + "','" + stu_branch + "','" + gender + "',0,'','')");
                     Toast.makeText(this, "Student Added", Toast.LENGTH_LONG).show();
                     Intent i = new Intent(getApplicationContext(), MainActivity.class);
                     startActivity(i);
                 }else
                 {
                     Toast.makeText(this, "Wrong Roll Number Format", Toast.LENGTH_LONG).show();
                 }
             }
             else
             {
                 Toast.makeText(this, "Invalid Details", Toast.LENGTH_LONG).show();
             }
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
    }
}
