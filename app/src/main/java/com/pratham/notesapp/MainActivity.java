package com.pratham.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fabadd;
    LinearLayout includ;
    AppCompatButton btnadd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView=findViewById(R.id.recycler);
        fabadd=findViewById(R.id.fabadd);
        includ=findViewById(R.id.includ);
        btnadd=findViewById(R.id.btnadd);
        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.addnote);
                EditText dialogtitle,dialogcontent;
                AppCompatButton dialogbtn;
                dialogtitle=dialog.findViewById(R.id.dialogtitl);
                dialogcontent=dialog.findViewById(R.id.dialogcontnt);
                dialogbtn=dialog.findViewById(R.id.dialogaddbtn);

                dialogbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String t=dialogtitle.getText().toString();
                        String c=dialogcontent.getText().toString();
                        if(t.equals("") || c.equals("")){
                            Toast.makeText(MainActivity.this, "Please enter something!", Toast.LENGTH_SHORT).show();
                        }else{
                            DBhelper dBhelper=new DBhelper(MainActivity.this);
                            dBhelper.noteadd( t , c);
                            Toast.makeText(MainActivity.this, "Notes add Successfully", Toast.LENGTH_SHORT).show();
                            Shownote(dBhelper);
                            dialog.cancel();

                        }
                    }
                });dialog.show();

            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabadd.performClick();
            }
        });
        DBhelper dBhelper=new DBhelper(MainActivity.this);
    Shownote(dBhelper);

    }

    public void Shownote(DBhelper dBhelper) {

        ArrayList<Datamodel> list=dBhelper.fetchnotes();
        if(list.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
         RecyclerAdapter adapter=   new RecyclerAdapter(list,MainActivity.this);
         adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        includ.setVisibility(View.GONE);
    }else {
        includ.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
}}