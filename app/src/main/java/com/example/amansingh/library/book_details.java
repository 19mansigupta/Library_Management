package com.example.amansingh.library;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class book_details extends AppCompatActivity {

    Intent i;
    int id;
    TextView bookname;
    TextView authorname;
    TextView book_id;
    Button issue;
    TextView status;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        i = getIntent();
        id = i.getIntExtra("id",0);
        bookname = (TextView)findViewById(R.id.book_name);
        authorname = (TextView)findViewById(R.id.author_name);
        book_id = (TextView)findViewById(R.id.bookid);
        status = (TextView)findViewById(R.id.status);
        issue = (Button)findViewById(R.id.issue);
        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(getApplicationContext(),Issue_book.class);
               Log.i("id",""+id);
               i.putExtra("book_id",id);
               startActivity(i);
               finish();
            }
        });

        db = this.openOrCreateDatabase("library",MODE_PRIVATE,null);
        getdetails(id);
    }
    void getdetails(int id)
    {
        Cursor c = db.rawQuery("Select * from books where id='"+id+"'",null);
        c.moveToFirst();
        bookname.setText(bookname.getText()+" "+c.getString(c.getColumnIndex("name")));
        authorname.setText(authorname.getText()+" "+c.getString(c.getColumnIndex("author")));
        if(c.getInt(c.getColumnIndex("status"))!=0)
        {
            status.setText("Already Issued");
            issue.setEnabled(false);
        }
        else
        if(c.getInt(c.getColumnIndex("status"))==0)
        {
            status.setText("Available for issue");
        }
        book_id.setText(book_id.getText()+"\n"+c.getString(c.getColumnIndex("id")));
    }
}
