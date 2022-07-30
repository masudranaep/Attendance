package com.example.attendance;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {

    public static final String CLASS_ADD_DIALOG= "addClass";
    public static final String STUDENT_ADD_DIALOG= "addStudent";
    public static final String CLASS_UPDATE_DIALOG = "updateClass";
    public static  final String STUDENT_UPDATE_DIALOG = "updateStudent";

    private OnClickListener listener;
    private int roll;
    private String name;


    public MyDialog(int roll, String name) {

        this.roll = roll;
        this.name = name;
    }
    public MyDialog(){

    }

    public MyDialog(String name) {
    }


    public interface OnClickListener{

        void onClick(String text1, String text2);
    }

    public void setListener(OnClickListener listener) {
      this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = null;
        if (getTag ().equals ( CLASS_ADD_DIALOG )) dialog = getAddClassDialog();
        // menu add 3 set
        if(getTag ().equals ( STUDENT_ADD_DIALOG ))dialog = getAddStudentDialog ();
        if(getTag ().equals ( CLASS_UPDATE_DIALOG ))dialog = getUpdateClassDialog ();
        if (getTag ().equals ( STUDENT_UPDATE_DIALOG ))dialog = getUpdateStuidentDialog ();


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable (android.graphics.Color.TRANSPARENT));

        return dialog;
    }

    private Dialog getUpdateStuidentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder (getActivity () );
        View view = LayoutInflater.from ( getActivity () ).inflate ( R.layout.dialog, null );
        builder.setView ( view );

//        AlertDialog dialog = builder.create ();
//        dialog.show ();

        TextView title = view.findViewById ( R.id.titleDialog );
        title.setText ( "Update Student" );


        //class name and subject name add 3 set
        EditText roll_Edt = view.findViewById ( R.id.edt01 );
        EditText  name_Edt = view.findViewById ( R.id.edt02 );

        roll_Edt.setHint ( "Roll" );
        name_Edt.setHint ( "Name" );

        Button cancel = view.findViewById ( R.id.cancel_btn );
        Button add = view.findViewById ( R.id.add_btn );
        add.setText ( "Update" );
        roll_Edt.setText ( roll +"");
        roll_Edt.setEnabled ( false );
        name_Edt.setText ( name);

        cancel.setOnClickListener ( v-> dismiss () );

        add.setOnClickListener ( v-> {
            //add button click krle class name and sub namne asbe

            String roll = roll_Edt.getText ().toString ();
            String name = name_Edt.getText ().toString ();
        
            listener.onClick ( roll, name);
            dismiss ();

        });
        return builder.create ();

    }
    //Update class 2 set (11) -> Mainactivity
    private Dialog getUpdateClassDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder (getActivity () );
        View view = LayoutInflater.from ( getActivity () ).inflate ( R.layout.dialog, null );
        builder.setView ( view );

//        AlertDialog dialog = builder.create ();
//        dialog.show ();

        TextView title = view.findViewById ( R.id.titleDialog );
        title.setText ( "Update Class" );


        //class name and subject name add 3 set
        EditText class_Edt = view.findViewById ( R.id.edt01 );
        EditText  subject_Edt = view.findViewById ( R.id.edt02 );

        class_Edt.setHint ( "Class Name" );
        subject_Edt.setHint ( "Subject Name" );

        Button cancel = view.findViewById ( R.id.cancel_btn );
        Button add = view.findViewById ( R.id.add_btn );

        add.setText ( "Update" );

        cancel.setOnClickListener ( v-> dismiss () );

        add.setOnClickListener ( v-> {
            //add button click krle class name and sub namne asbe

            String className = class_Edt.getText ().toString ();
            String subjectName = subject_Edt.getText ().toString ();
            listener.onClick ( className, subjectName );
            dismiss ();
        });


        return builder.create ();
    }

    private Dialog getAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder (getActivity () );
        View view = LayoutInflater.from ( getActivity () ).inflate ( R.layout.dialog, null );
        builder.setView ( view );

//        AlertDialog dialog = builder.create ();
//        dialog.show ();

        TextView title = view.findViewById ( R.id.titleDialog );
        title.setText ( "Add New Student" );


        //class name and subject name add 3 set
        EditText roll_Edt = view.findViewById ( R.id.edt01 );
        EditText  name_Edt = view.findViewById ( R.id.edt02 );

        roll_Edt.setHint ( "Roll" );
        name_Edt.setHint ( "Name" );

        Button cancel = view.findViewById ( R.id.cancel_btn );
        Button add = view.findViewById ( R.id.add_btn );

        cancel.setOnClickListener ( v-> dismiss () );

        add.setOnClickListener ( v-> {
            //add button click krle class name and sub namne asbe

            String roll = roll_Edt.getText ().toString ();
            String name = name_Edt.getText ().toString ();
            roll_Edt.setText ( String.valueOf (Integer.parseInt ( roll )+ 1) );
            name_Edt.setText ( "" );
            listener.onClick ( roll, name);
            dismiss ();

        });
        return builder.create ();
    }

    private Dialog getAddClassDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder (getActivity () );
        View view = LayoutInflater.from ( getActivity () ).inflate ( R.layout.dialog, null );
        builder.setView ( view );

//        AlertDialog dialog = builder.create ();
//        dialog.show ();

        TextView title = view.findViewById ( R.id.titleDialog );
        title.setText ( "Add New Class" );


        //class name and subject name add 3 set
       EditText class_Edt = view.findViewById ( R.id.edt01 );
       EditText  subject_Edt = view.findViewById ( R.id.edt02 );

       class_Edt.setHint ( "Class Name" );
       subject_Edt.setHint ( "Subject Name" );

        Button cancel = view.findViewById ( R.id.cancel_btn );
        Button add = view.findViewById ( R.id.add_btn );

        cancel.setOnClickListener ( v-> dismiss () );

        add.setOnClickListener ( v-> {
            //add button click krle class name and sub namne asbe

            String className = class_Edt.getText ().toString ();
            String subjectName = subject_Edt.getText ().toString ();
            listener.onClick ( className, subjectName );
            dismiss ();
        });


        return builder.create ();
    }
}
