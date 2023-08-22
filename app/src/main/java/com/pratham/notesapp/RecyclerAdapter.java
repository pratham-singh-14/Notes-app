package com.pratham.notesapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.viewholder> {
    ArrayList<Datamodel> list;
    Context context;
    RecyclerAdapter(ArrayList<Datamodel> list, Context context){

        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        Datamodel model=list.get(position);

        holder.itemtitle.setText(model.title);
        holder.itemcontent.setText(model.content);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                        builder.setTitle("Delete "+model.title);
                        builder.setMessage("Are you sure you want to delete this notes");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBhelper dBhelper=new DBhelper(context);
                                dBhelper.Delete(model);
                                ((MainActivity)context).Shownote(dBhelper);
                            }
                        });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                                builder.show();
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.addnote);
                EditText dialogtitle,dialogcontent;
                TextView txt1;
                AppCompatButton dialogbtn;
                txt1=dialog.findViewById(R.id.txt1);
                dialogtitle=dialog.findViewById(R.id.dialogtitl);
                dialogcontent=dialog.findViewById(R.id.dialogcontnt);
                dialogbtn=dialog.findViewById(R.id.dialogaddbtn);
                dialogtitle.setText(model.title);
                dialogcontent.setText(model.content);
                txt1.setText("Update Note");
                dialogbtn.setText("Update");
                dialogbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String t=dialogtitle.getText().toString();
                        String c=dialogcontent.getText().toString();

                        if(c != null){
                            DBhelper bhelper=new DBhelper(context);

                            model.title=t;
                            model.content=c;
                            bhelper.Update(model);
                            ((MainActivity)context).Shownote(bhelper);
                            dialog.cancel();


                        }else {
                            Toast.makeText(context, "please write something!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView itemtitle,itemcontent;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            itemtitle=itemView.findViewById(R.id.itemtitle);
            itemcontent=itemView.findViewById(R.id.itemcontent);
        }
    }
}
