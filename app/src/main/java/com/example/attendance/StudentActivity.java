package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.example.attendance.Adapter.StudentAdapter;
import com.example.attendance.Database.DBHelper;
import com.example.attendance.Fragment.MyCalander;
import com.example.attendance.Model.StudentItem;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    Toolbar toolbar;
    private  String className;
    private  String subjectName;
    private  int position;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<StudentItem> studentItems = new ArrayList<> ();
    private DBHelper dbHelper;
    private  int cid;
    private MyCalander calander;
    private TextView subtitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_student );


        calander = new MyCalander ();
        dbHelper = new DBHelper ( this );
        //className and subjectName input 1 set

        Intent intent = getIntent ();

        className = intent.getStringExtra ( "className" );
        subjectName = intent.getStringExtra ( "subjectName" );
        position = intent.getIntExtra ( "position",-1 );
        cid = intent.getIntExtra ( "cid", -1 );


        setToolbar ();

        loadData();
        //student name 1 set

        recyclerView = findViewById ( R.id.student_recyclerView );
        recyclerView.setHasFixedSize ( true );
        layoutManager = new LinearLayoutManager ( this );
        recyclerView.setLayoutManager ( layoutManager );
        adapter = new StudentAdapter ( this, studentItems );
        recyclerView.setAdapter ( adapter );
        //atten and afscen 1 set
     //   adapter.setOnItemClickListener ( position ->  changeStatus(position));

     //   loadStatusData ();


    }
///add student 3 set (15)
@SuppressLint("Range")
    private void loadData() {

        Cursor cursor = dbHelper.getStudentTable (cid);
        studentItems.clear ();
        while (cursor.moveToNext ()){
            long sid = cursor.getLong ( cursor.getColumnIndex ( DBHelper.S_ID ) );
            int roll = cursor.getInt ( cursor.getColumnIndex ( DBHelper.STUDENT_ROLL_KEY ) );
            String name = cursor.getString ( cursor.getColumnIndex ( DBHelper.STUDENT_TABLE_NAME) );
            studentItems.add ( new StudentItem ( sid, roll, name ) );

        }
        cursor.close ();

    }
//
//    private void changeStatus(int position) {
//
//        String status = studentItems.get ( position ).getStatus ();
//
//        if (status.equals ( "P" )) status = "A";
//        else status = "P";
//
//        studentItems.get ( position ).setStatus ( status );
//        adapter.notifyItemChanged ( position );
//
//
//    }

    //toobar kaj 2 set
    private void setToolbar(){
        toolbar = findViewById ( R.id.toolbar );
        TextView title = toolbar.findViewById ( R.id.title_toolbar );
        subtitle = toolbar.findViewById ( R.id.subtitle_toolbar );
        ImageButton back = toolbar.findViewById ( R.id.back );
        ImageButton save = toolbar.findViewById ( R.id.save );

      //  save.setOnClickListener ( v -> savaStatus() );

        title.setText ( className);


        subtitle.setText ( subjectName+ " | " + calander.getDate () );

        back.setOnClickListener ( v -> onBackPressed () );

        //name and statud and menu add 2 set start

        toolbar.inflateMenu ( R.menu.student_menu );
        toolbar.setOnMenuItemClickListener ( menuItem -> onMenuItemClick(menuItem) );





    }
//// status save 3 set
//    private void savaStatus() {
//        for (StudentItem studentItem : studentItems){
//            String status = studentItem.getStatus();
//            if (status == "p") status= "A";
//            long value = dbHelper.addStatus ( studentItem.getSid (), calander.getDate (), status );
//
//            if (value == - 1) dbHelper.updateStatus ( studentItem.getSid (), calander.getDate (), status );
//        }
//    }
//
//    // status load save 4 set
//    private void loadStatusData(){
//        for (StudentItem studentItem : studentItems){
//            String status = dbHelper.getStatus ( studentItem.getSid (), calander.getDate () );
//            if(status == null) studentItem.setStatus ( status );
//         else studentItem.setStatus ( "" );
//        }
//        adapter.notifyDataSetChanged ();
//    }

    private boolean onMenuItemClick(MenuItem menuItem) {

        if (menuItem.getItemId () == R.id.add_student){
            showAddStudentDialog();
        }
        else  if(menuItem.getItemId () == R.id.show_calander){
            showCalanderDialog ();
        }
        return true;
    }

    private void showCalanderDialog() {

        calander.show ( getSupportFragmentManager (), "" );
        calander.setOncalanderClickListener ( this:: oncalanderClickListener );
    }

    private void oncalanderClickListener(int year, int month, int day) {
       calander.setDate ( year, month,day );
       subtitle.setText ( subjectName+ " | " + calander.getDate() );
//loadStatusData ();
    }


    private void showAddStudentDialog() {

        MyDialog dialog = new MyDialog ();
        dialog.show ( getSupportFragmentManager (),MyDialog.STUDENT_ADD_DIALOG );

        dialog.setListener ( (roll, name) -> addStudent(roll, name) );

    }


    private void addStudent(String roll_string, String name) {
        //add student 2 set
        int roll = Integer.parseInt ( roll_string );
        long sid =  dbHelper.addStudent ( cid, roll, name );

        StudentItem studentItem = new StudentItem ( sid, roll, name );


        studentItems.add ( studentItem );
        adapter.notifyDataSetChanged ();
    }
    //name and statud and menu add 2 set end

    //delete and edit 2 set

//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId ()){
//            case 0:
//                showUpdateStudentDialog (item.getGroupId ());
//                break;
//            case 1:
//            deleteStudent (item.getGroupId ());
//        }
//        return super.onContextItemSelected ( item );
//    }
//
//    //student update 1set
//    private void showUpdateStudentDialog(int position) {
//
//        MyDialog dialog = new MyDialog ( studentItems.get ( position ).getName ());
//        dialog.show ( getSupportFragmentManager (), MyDialog.CLASS_UPDATE_DIALOG );
//        dialog.setListener (( roll_string, name ) -> updateStudent(position, name));
//
//    }
//
//    private void updateStudent(int position, String name) {
//        dbHelper.updateStudent(studentItems.get ( position ).getSid (), name);
//        studentItems.get ( position ).setName ( name );
//        adapter.notifyItemChanged ( position );
//
//    }
//
//    private void deleteStudent(int position) {
//
//        dbHelper.deleteStudent(studentItems.get ( position ).getSid ());
//        studentItems.remove ( position );
//        adapter.notifyItemRemoved ( position );
//    }
}