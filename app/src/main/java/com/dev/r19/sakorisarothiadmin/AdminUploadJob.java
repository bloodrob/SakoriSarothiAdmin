package com.dev.r19.sakorisarothiadmin;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdminUploadJob extends AppCompatActivity {

    //variable for edittext and button
    private EditText nameOfJob;
    private EditText lastDateOfJob;
    private EditText stipenSalaryOfJob;
    private EditText experienecrOfJOb;
    private EditText detailsOfJod;
    private Spinner categoryOfJob ;
    private Button uploadJobDetails;

    private String JobName;
    private String JobSubject;
    private String LastDate;
    private String JobExperience;
    private String StiepndSalary;
    private String JobDetails;
    private String MyFileUrl;
    //use to get system times
    int requesttime;
    //arraylist for catedory and last date
    List<String> CategoryList;
    // for datepicker
    static Calendar myCalendar = Calendar.getInstance();
    // firebase to store job details
    FirebaseDatabase database;
    DatabaseReference ref;
    //set up a annotaion for comtable with adnroid version. here it is for Oreo. Api level 26
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_upload_job);
        //initiazing edittext and button
        nameOfJob = (EditText)findViewById(R.id.name_of_job);
        categoryOfJob = (Spinner)findViewById(R.id.subject_of_job);
        stipenSalaryOfJob = (EditText)findViewById(R.id.stipen_salary);
        experienecrOfJOb = (EditText)findViewById(R.id.experience);
        detailsOfJod = (EditText)findViewById(R.id.details_of_job);
        uploadJobDetails = (Button)findViewById(R.id.upload_job_details);
        //date for calendar view
        lastDateOfJob = (EditText)findViewById(R.id.last_date);
        lastDateOfJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AdminUploadJob.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DATE)).show();
            }
        });

        //initialization and assign of arraylist of category
        CategoryList = new ArrayList<String>();
        CategoryList.add("Bank");
        CategoryList.add("State_Govt");
        CategoryList.add("Central_Govt");
        CategoryList.add("Railway");
        CategoryList.add("Oil");
        CategoryList.add("Ongc");
        CategoryList.add("Educational_Institute");
        CategoryList.add("Research");
        CategoryList.add("It_Company");
        CategoryList.add("Private_Company");
        CategoryList.add("General_Recruitment");
        CategoryList.add("Insurance");
        //assigning value to the arrayadaptor
        final ArrayAdapter<String> getCategoryList = new ArrayAdapter<String>(AdminUploadJob.this, android.R.layout.simple_spinner_item, CategoryList);
        getCategoryList.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categoryOfJob.setAdapter(getCategoryList);
        // getting the selected value
        categoryOfJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                JobSubject = parent.getItemAtPosition(position).toString().trim();
                AdminPushNotification.subsTopic = JobSubject;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //uploadJObDetails method start
        uploadJobDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobName = nameOfJob.getText().toString().trim();
                JobExperience = experienecrOfJOb.getText().toString().trim();
                StiepndSalary = stipenSalaryOfJob.getText().toString().trim();
                JobDetails = detailsOfJod.getText().toString().trim();
                //Context for use in setLatestEventInfo
                Context context = getApplicationContext();
                // sending to the static variable to AdminPushNotification class
                AdminPushNotification.takeJobName = JobName.toString().trim();
                AdminPushNotification.takeLastDate = LastDate.toString().trim();

                // firebase work to save the data
                database = FirebaseDatabase.getInstance();
                ref = database.getReference("UploadedJobDetails");
                // mthod to assign key value
                SaveJobDetails();
                Intent intent = new Intent(AdminUploadJob.this, AdminUploadJobDetailsPdf.class);
                intent.putExtra("NameOfJob", JobName);
                startActivity(intent);
            }
        });
    }
    // method for save data
    private void SaveJobDetails() {
        AdminJobUploadModel jobUp = new AdminJobUploadModel();
        jobUp.setJobName(JobName);
        jobUp.setJobSubject(JobSubject);
        jobUp.setJobDetails(JobDetails);
        jobUp.setJobExperience(JobExperience);
        jobUp.setLastDate(LastDate);
        jobUp.setStiepndSalary(StiepndSalary);
        ref.child(JobName).setValue(jobUp);
        // method to save data
        CreateToSaveData();
    }
    private void CreateToSaveData(){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AdminJobUploadModel JobUp = dataSnapshot.getValue(AdminJobUploadModel.class);
                if (JobUp == null) {
                    Toast.makeText(AdminUploadJob.this, "No Data Entered", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(AdminUploadJob.this, "Data is suceessfully save to the database", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AdminUploadJob.this, "Something Wrong, Contact To Database Administrator", Toast.LENGTH_SHORT).show();
                return;
            }

        });
    }
    // Method for datePickerDialog
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(myCalendar.YEAR, year);
            myCalendar.set(myCalendar.MONTH, month);
            myCalendar.set(myCalendar.DAY_OF_MONTH, dayOfMonth);
            UpdateLabel();
        }
    };
    private void UpdateLabel() {
        String myDateFormat = "MM/dd/YY";
        SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat, Locale.ENGLISH);
        lastDateOfJob.setText(sdf.format(myCalendar.getTime()));
        LastDate = lastDateOfJob.getText().toString().trim();
        Toast.makeText(AdminUploadJob.this, "Your selected date is :" +LastDate, Toast.LENGTH_SHORT).show();
    }
}
