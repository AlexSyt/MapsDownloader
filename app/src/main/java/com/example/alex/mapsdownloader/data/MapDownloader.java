package com.example.alex.mapsdownloader.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.alex.mapsdownloader.models.Region;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MapDownloader {

    private static final String TAG = MapDownloader.class.getSimpleName();
    private static final String DOWNLOAD_PATH = "http://download.osmand.net/download.php?standard=yes&file=";
    private static final String DOWNLOADS_DIRECTORY_PATH =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

    public static void download(final Region region, final Context context) {
        final ProgressDialog progress = new ProgressDialog(context);

        new AsyncTask<Void, Integer, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress.setMessage("Downloading: " + region.getName());
                progress.setCancelable(false);
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setMax(100);
                progress.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                        (dialog, which) -> {
                            cancel(true);
                            dialog.cancel();
                        });
                progress.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL(DOWNLOAD_PATH + region.getDownloadPath());
                    URLConnection connection = url.openConnection();
                    connection.connect();

                    int lengthOfFile = connection.getContentLength();

                    InputStream input = new BufferedInputStream(url.openStream(), 8192);
                    OutputStream output =
                            new FileOutputStream(DOWNLOADS_DIRECTORY_PATH + "/" + region.getFileName());

                    byte[] data = new byte[1024];
                    long total = 0;
                    int count;

                    while ((count = input.read(data)) != -1 && !isCancelled()) {
                        total += count;
                        publishProgress((int) ((total * 100) / lengthOfFile),
                                (int) total / 1024 / 1024, lengthOfFile / 1024 / 1024);
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();

                } catch (MalformedURLException e) {
                    Log.e(TAG, "Cannot create url", e);
                } catch (IOException e) {
                    Log.e(TAG, "Cannot open connection", e);
                    new Handler(context.getMainLooper()).post(() -> {
                        Toast.makeText(context, "Cannot download the map", Toast.LENGTH_SHORT).show();
                    });
                }

                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                progress.setProgress(values[0]);
                progress.setProgressNumberFormat(values[1] + " MB / " + values[2] + " MB");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progress.hide();
                super.onPostExecute(aVoid);
            }

            @Override
            protected void onCancelled(Void aVoid) {
                new File(DOWNLOADS_DIRECTORY_PATH + "/" + region.getFileName()).delete();
            }
        }.execute();
    }
}
