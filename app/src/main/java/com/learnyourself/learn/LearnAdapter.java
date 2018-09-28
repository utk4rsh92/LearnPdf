package com.learnyourself.learn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LearnAdapter extends RecyclerView.Adapter<LearnAdapter.MyViewHolder> {
    private List<LearnData> learnData;
    Context ctx;
    File file;
    String file_name;

    public LearnAdapter(List<LearnData> learnData, Context applicationContext) {
        this.learnData = learnData;
        this.ctx = applicationContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvName.setText(learnData.get(position).getName());
        holder.tvUrl.setText(learnData.get(position).getUrl());
        holder.st_pdf_name.setText(learnData.get(position).getFileName_st());
        holder.staus.setText(learnData.get(position).getStstus());
        if (learnData.get(position).getStstus().equalsIgnoreCase("0")) {
            holder.relativeLayout.setBackgroundColor(Color.RED);
            holder.downloads.setText("Download");
        } else {
            holder.relativeLayout.setBackgroundColor(Color.GREEN);
            holder.downloads.setText("View");
        }


        holder.downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (learnData.get(position).getStstus().equalsIgnoreCase("0")) {
                    file_name = learnData.get(position).getFileName_st();
                    String pdfUrl = learnData.get(position).getFileName_st();
//                    pdfUrl = pdfUrl.replace(".pdf", "");
                    file_name = learnData.get(position).getFileName_st();
                    new attemptLogin().execute(pdfUrl, learnData.get(position).getUrl());
                } else {
                    file_name = learnData.get(position).getFileName_st();
          /*          String st_new=  learnData.get(position).getFileName_st();
                    st_new = */
                    file_name = learnData.get(position).getFileName_st();
                    Intent inty = new Intent(ctx, ReaderClasss.class);
                    inty.putExtra("filename", file_name);
                    inty.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(inty);

                   /* File file = new File("/storage/emulated/0/pdf/" + file_name);
                    //  File file = new File(Environment.getExternalStorageDirectory()+learnData.get(position).getFileName_st());
                    PackageManager packageManager = ctx.getPackageManager();

                    Intent testIntent = new Intent(Intent.ACTION_VIEW);
                    testIntent.setType("application/pdf");
                    List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri uri = Uri.fromFile(file);
                    intent.setDataAndType(uri, "application/pdf");
                    ctx.startActivity(intent);*/
                }
            }
        });


        //   RemotePDFViewPager pager = new RemotePDFViewPager(ctx,pdfUrl, listener);

        //  PRDownloaderConfig config =  PRDownloaderConfig.newBuilder()
        //  .setDatabaseEnabled(true)
        // .build();
    }

    public void showPdf() {
//         /storage/emulated/0/pdf/MIS_3005_Heat_Emitter_Guide
        File file = new File(Environment.getExternalStorageDirectory() + "/pdf/" + file_name);
        PackageManager packageManager = ctx.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return learnData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvUrl, staus, st_pdf_name;
        public RelativeLayout relativeLayout;
        public Button downloads;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.usersName);
            tvUrl = (TextView) itemView.findViewById(R.id.url);
            staus = (TextView) itemView.findViewById(R.id.staus);
            st_pdf_name = (TextView) itemView.findViewById(R.id.st_pdf_name);
            downloads = (Button) itemView.findViewById(R.id.downloads);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativel);
        }
    }

    class attemptLogin extends AsyncTask<String, String, Void> {

        ProgressDialog progressDialog = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ctx, "", "Please Wait...", true);

        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String extStorageDirectory = Environment.getExternalStorageDirectory()
                        .toString();
                File folder = new File(extStorageDirectory, "pdf");
                folder.mkdir();
                file = new File(folder, params[0]);
                try {
                    file.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                FileOutputStream f = new FileOutputStream(file);
                URL u = new URL(params[1]);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();

                InputStream in = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = in.read(buffer)) > 0) {
                    f.write(buffer, 0, len1);
                }
                f.close();
                //progressDialog.dismiss();

            } catch (Exception e) {
                e.printStackTrace();

            }

            return null;
        }


        @Override
        protected void onPostExecute(final Void getResponse) {

            try {

                try {
                    if (progressDialog != null) progressDialog.dismiss();

                Intent inty = new Intent(ctx, MainActivity.class);
                inty.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(inty);

                } catch (Exception ev) {
                    System.out.print(ev.getMessage());
                }

            } catch (Exception ev) {
                ev.getStackTrace();
            }

        }
    }
}
