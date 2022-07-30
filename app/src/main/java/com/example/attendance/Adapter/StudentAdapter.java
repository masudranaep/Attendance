package com.example.attendance.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.Model.ClassItem;
import com.example.attendance.Model.StudentItem;
import com.example.attendance.R;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {


    ArrayList<StudentItem> studentItems;
    Context context;

    // ek class theke onn activity jaewar 1set
    private OnItemClickListener onItemClickListener;

    public interface  OnItemClickListener{

        void onClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    public StudentAdapter(Context context, ArrayList<StudentItem> studentItems){

        this.studentItems = studentItems;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.student_items, parent, false );
        return new StudentViewHolder ( itemView ,onItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {

        holder.roll.setText ( studentItems.get ( position ).getRoll () +"" );
        holder.name.setText ( studentItems.get ( position ).getName () );
        holder.status.setText ( studentItems.get ( position ).getStatus () );
        //atten color 2 set
        holder.cardView.setCardBackgroundColor ( getColor(position) );

    }

    private int getColor(int position) {

        String status = studentItems.get ( position ).getStatus ();
        if (status.equals ( "P" ))
            return Color.parseColor ("#" + Integer.toHexString ( ContextCompat.getColor ( context, R.color.atten ) ));
        else  if (status.equals ( "A" ))
            return Color.parseColor ( "#" + Integer.toHexString ( ContextCompat.getColor ( context, R.color.absen ) ) );

        return Color.parseColor ( "#" + Integer.toHexString ( ContextCompat.getColor ( context,R.color.white ) ) );


    }

    @Override
    public int getItemCount() {
        return studentItems.size ();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView roll;
        TextView name;
        TextView status;
        CardView cardView;

        public StudentViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super ( itemView );

            roll = itemView.findViewById ( R.id.roll );
            name = itemView.findViewById ( R.id.name );
            status = itemView.findViewById ( R.id.status );


            cardView = itemView.findViewById ( R.id.cardView );
            // ek class theke onn activity jaewar 2set

            itemView.setOnClickListener ( v->onItemClickListener.onClick ( getAdapterPosition () ) );
        }

        // delete and edit 1 set (6) -> mainactivity
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add ( getAdapterPosition (), 0, 0, "Edit" );
            menu.add ( getAdapterPosition (), 1, 0, "Delete" );

        }
    }



}
