package com.learnyourself.learn;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.util.List;

public class ReaderClasss extends AppCompatActivity {
String filenames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_classs);
        Intent intents = getIntent();
        filenames = intents.getStringExtra("filename");
       // showPdf();
     /*   File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/pdf/"+filenames);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        Toast.makeText(this, Uri.fromFile(file).toString(), Toast.LENGTH_SHORT).show();
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);*/
        /*File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/pdf/"+"84velden.pdf");
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }*/
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/pdf/"+filenames);
        viewPdf(Uri.fromFile(file));
    }
    private void viewPdf(Uri file) {

        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(file, "application/pdf");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("No Application Found");
            builder.setMessage("Download from Android Market?");
            builder.setPositiveButton("Yes, Please", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                marketIntent.setData(Uri.parse("market://details?id=com.gappstudios.autowifi3gdataswitch.san.basicpdfviewer"));
                startActivity(marketIntent);

            }
        });
            builder.setNegativeButton("No, Thanks", null);
            builder.create().show();

        }
    }

    public void showPdf()
    {
        //File file = new File(Environment.getExternalStorageDirectory()+"/pdf/Read.pdf");
///storage/emulated/0/pdf/MIS_3005_Heat_Emitter_Guide.pdf
        File file = new File(Environment.getExternalStorageDirectory()+"/pdf/"+"84velden.pdf");
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);
    }
}
