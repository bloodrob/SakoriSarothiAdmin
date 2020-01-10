package com.dev.r19.sakorisarothiadmin;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.dev.r19.sakorisarothiadmin.AdminUploadJob.JobExperience;
import static com.dev.r19.sakorisarothiadmin.AdminUploadJob.LastDate;
import static com.dev.r19.sakorisarothiadmin.AdminUploadJob.StiepndSalary;

public class AdminUploadJobDetailsPdf extends AppCompatActivity {

    // string to get the value from other class
    String getTheName;
    //button and imageview
    private Button choosePdf, uploadPdf;
    private TextView showSuccessPdfUpload, getThePdfName;
    // uri to set a path to store the file
    private Uri pathToPdf;
    // use in the file chooser as a passed code
    final static int PICK_PDF_CODE = 2342;
    //Firebase strorage variable
    FirebaseStorage database;
    StorageReference stref;
    // Firebase Database Variable
    private FirebaseDatabase databaseToUrl;
    private DatabaseReference refToDatbaseToUrl;

    //static String for firebase
    static String JobName, JobSubject, JobDetails;
    // for set up progressbar
    ProgressDialog pdD;
    // static string
    static String getTheFileName;
    //static string to passed the data from afunction
    static String myResult;
    // stroring the download url;
    static String MyFileUrl;
    // static string to use the get key value in another page
    static String getPushedIdForUse;
    //set up a annotaion for comtable with adnroid version. here it is for Oreo. Api level 26
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_upload_job_details_pdf);
        //Initialization
        choosePdf = (Button)findViewById(R.id.choose_pdf);
        uploadPdf = (Button)findViewById(R.id.upload_pdf);
        showSuccessPdfUpload = (TextView)findViewById(R.id.show_success_upload_pdf);
        getThePdfName = (TextView)findViewById(R.id.get_the_pdf_name);
        // pogress dialog Initialization
        pdD = new ProgressDialog(this);
        pdD.setTitle("Uploading the file, Please don't press back.");
        pdD.setCanceledOnTouchOutside(false);
        // getting the string from AdminSendJobNotification class
        getTheName = AdminUploadJob.JobName.toString().trim();
        //listener for choose the image
        choosePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // method to choose image
                PdfChooser();
            }
        });
        // listener for uploadthe pdf
        uploadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PdfUploader();
            }
        });
    }
    // get the pdf from the storage
    private void PdfChooser(){
        // creating a intent for file Chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select Pdf"), PICK_PDF_CODE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // check if the chooser choose file or not
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData()!=null) {
            //if file is selected
            if (data.getData()!= null) {
                // uploading the file
                pathToPdf = data.getData();
                getThePdfName.setText("Here your file to be uploaded, if it is correct then you can upload it" + pathToPdf);
                Toast.makeText(AdminUploadJobDetailsPdf.this, "File Selected", Toast.LENGTH_SHORT).show();
            }
            if (data.getData() == null){
                Toast.makeText(AdminUploadJobDetailsPdf.this, "File not Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // method to upload the pdf file to firebase
    private void PdfUploader() {
        pdD.show();
        database = FirebaseStorage.getInstance();
        stref = database.getReference("Uploaded Job Pdf/" +getTheName + System.currentTimeMillis() + ".pdf");
        stref.putFile(pathToPdf).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pdD.dismiss();
                Toast.makeText(AdminUploadJobDetailsPdf.this, "Uploaded SuccessFully", Toast.LENGTH_SHORT).show();
                showSuccessPdfUpload.setText("Successfuly uploaded, You can Choose other file to upload or go back to previous page.");
                // getting the uploaded url
                stref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUri1 = uri;
                        MyFileUrl = downloadUri1.toString().trim();
                        Toast.makeText(AdminUploadJobDetailsPdf.this, "Url Is : "+MyFileUrl, Toast.LENGTH_LONG).show();
                        // method to get the node and add a new data on the child node
                        JobName = getTheName.toString().trim();
                        databaseToUrl = FirebaseDatabase.getInstance();
                        addUrlInNewNode(JobName, JobSubject,LastDate,JobExperience,StiepndSalary, JobDetails, MyFileUrl);
                    }
                });
            }
        });
        stref.putFile(pathToPdf).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminUploadJobDetailsPdf.this, "Uploaded Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // function of add the data in a new node of existing data
    private void addUrlInNewNode(String JobName, String JobSubject, String LastDate, String JobExperience, String StipendSalary, String JObDetails, String MyFileUrl) {
        AdminJobUploadModel jobUpMod = new AdminJobUploadModel(JobName, JobSubject,LastDate,JobExperience,StiepndSalary, JobDetails, MyFileUrl);
        refToDatbaseToUrl = databaseToUrl.getReference("UploadedJobDetails");
        // pushing the value
        refToDatbaseToUrl.child(JobName+"/MyFileUrl").setValue(MyFileUrl.toString());
        // intent to AdminSendjobNotificatio
        Intent intent = new Intent(AdminUploadJobDetailsPdf.this, AdminMainActivity.class);
        startActivity(intent);
    }
}
