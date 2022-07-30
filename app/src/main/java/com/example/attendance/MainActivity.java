package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.attendance.Adapter.ClassAdapter;
import com.example.attendance.Database.DBHelper;
import com.example.attendance.Model.ClassItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton Fab;

    RecyclerView recyclerView;

    ClassAdapter classAdapter;

    RecyclerView.LayoutManager layoutManager;
    ArrayList<ClassItem> classItems = new ArrayList<> ();

    Toolbar toolbar;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        dbHelper = new DBHelper ( this );
        Fab = findViewById ( R.id.flaging );
        Fab.setOnClickListener ( v -> showDialog () );

        loadData();

        //recylcar class name and subject name add 2set
        recyclerView = findViewById ( R.id.RecyclerView );

        recyclerView.setHasFixedSize ( true );
        layoutManager = new LinearLayoutManager ( this );
        recyclerView.setLayoutManager ( layoutManager );
        classAdapter = new ClassAdapter ( this, classItems );
        recyclerView.setAdapter ( classAdapter );

        // ek class theke onn activity jaewar 3set
        classAdapter.setOnItemClickListener ( position -> gotoIntemActivity ( position ) );

      setToolbar ();




    }

    //load class database 3 set (3)

    @SuppressLint("Range")
    private void loadData(){

        Cursor cursor = dbHelper.getClassTable();
        classItems.clear ();

        while (cursor.moveToNext ()){
             int id = cursor.getInt ( cursor.getColumnIndex ( DBHelper.C_ID ) );
             String className = cursor.getString ( cursor.getColumnIndex ( DBHelper.CLASS_NAME_KEY ) );
             String subjectName = cursor.getString ( cursor.getColumnIndex ( DBHelper.SUBJECT_NAME_KEY ) );

            classItems.add ( new ClassItem ( id, className, subjectName ) );
        }

    }


    //toobar kaj 1 set
    private void setToolbar(){
        toolbar = findViewById ( R.id.toolbar );
        TextView title = toolbar.findViewById ( R.id.title_toolbar );
        TextView subtitle = toolbar.findViewById ( R.id.subtitle_toolbar );
        ImageButton back = toolbar.findViewById ( R.id.back );
        ImageButton save = toolbar.findViewById ( R.id.save );


        title.setText ( "Attenance" );
        subtitle.setVisibility ( View.GONE );
        back.setVisibility ( View.INVISIBLE );
        save.setVisibility ( View.INVISIBLE );
    }

    private void gotoIntemActivity(int position) {


        Intent intent = new Intent (this, StudentActivity.class);

        intent.putExtra ( "className", classItems.get ( position ).getClassName () );
        intent.putExtra ( "subjectName", classItems.get ( position ).getSubjectName () );

        intent.putExtra ( "position", position );
        intent.putExtra ( "cid", classItems.get ( position ).getCid () );
        startActivity ( intent );
    }



    //dialog show start 1st
    private void showDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder ( this );
//        View view = LayoutInflater.from ( this ).inflate ( R.layout.dialog, null );
//        builder.setView ( view );
//
//       AlertDialog dialog = builder.create ();
//       dialog.show ();
//
//       //class name and subject name add 3 set
//        class_Edt = view.findViewById ( R.id.edt01 );
//        subject_Edt = view.findViewById ( R.id.edt02 );
//
//        Button cancel = view.findViewById ( R.id.cancel_btn );
//        Button add = view.findViewById ( R.id.add_btn );
//
//        cancel.setOnClickListener ( v-> dialog.dismiss () );
//
//        add.setOnClickListener ( v-> {
//
//            addClass ();
//            dialog.dismiss ();
//        });



        MyDialog dialog = new MyDialog ();
        dialog.show ( getSupportFragmentManager (), MyDialog.CLASS_ADD_DIALOG );

        dialog.setListener ( (className, subjectName) -> addClass (className, subjectName) );

    }

    private void addClass(String className, String subjectName) {

        // databse sathe add 4 set (4)
        long cid  = dbHelper.addClass(className, subjectName);
        ClassItem classItem = new ClassItem ( cid, className, subjectName );
        classItems.add ( classItem);

        classAdapter.notifyDataSetChanged ();


    }
    //delete and edit class 2 set (7)


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId ()){
            case 0:
                showUpdateDialog(item.getGroupId ());
                break;
            case 1:
             DeleteClass(item.getGroupId ());


        }
        return super.onContextItemSelected ( item );

    }

    //update 1 set (10) -> MyDialog
    private void showUpdateDialog(int position) {
        MyDialog dialog = new MyDialog ();
        dialog.show ( getSupportFragmentManager (), MyDialog.CLASS_UPDATE_DIALOG );
        dialog.setListener ( (className, subjectName ) ->updateClass(position, className, subjectName));

    }
    //update 3 set (12) ->DbHelper
    private void updateClass(int position, String className, String subjectName) {
        dbHelper.UpdateClass ( classItems.get ( position ).getCid (), className, subjectName );
        classItems.get ( position ).setClassName (className);
        classItems.get ( position ).setSubjectName (  subjectName);
        classAdapter.notifyItemChanged ( position );

    }
//delete and edit 3 set (8) -> DBhelper

    private void DeleteClass(int position) {

        dbHelper.deleteClass(classItems.get ( position ).getCid ());
        classItems.remove ( position );

        classAdapter.notifyItemRemoved ( position );

    }
}