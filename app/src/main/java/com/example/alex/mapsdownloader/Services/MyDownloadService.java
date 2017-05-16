package com.example.alex.mapsdownloader.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.alex.mapsdownloader.fragments.RegionsFragment;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MyDownloadService extends IntentService {

    private static final String TAG = MyDownloadService.class.getSimpleName();
    private static final String DOWNLOAD_PATH = "http://download.osmand.net/download.php?standard=yes&file=";
    private static final String DOWNLOADS_DIRECTORY_PATH =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

    public MyDownloadService() {
        super("MyDownloadService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        return START_REDELIVER_INTENT;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String mapPath = intent.getStringExtra(RegionsFragment.KEY_MAP_PATH);
        String name = intent.getStringExtra(RegionsFragment.KEY_NAME);
        try {
            Log.d("kek", "downloading : " + name);
            URL url = new URL(DOWNLOAD_PATH + mapPath);
            URLConnection connection = url.openConnection();
            connection.connect();

            int lengthOfFile = connection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            OutputStream output =
                    new FileOutputStream(DOWNLOADS_DIRECTORY_PATH + "/" + name);

            byte[] data = new byte[1024];
            long total = 0;
            int count;

            while ((count = input.read(data)) != -1) {
                total += count;
//                publishProgress((int) ((total * 100) / lengthOfFile),
//                        (int) total / 1024 / 1024, lengthOfFile / 1024 / 1024);
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

        } catch (MalformedURLException e) {
            Log.e(TAG, "Cannot create url", e);
        } catch (IOException e) {
            Log.e(TAG, "Cannot open connection", e);
            new Handler(getApplicationContext().getMainLooper()).post(() -> {
                Toast.makeText(getApplicationContext(),
                        "Cannot download the map : " + name, Toast.LENGTH_SHORT).show();
            });
        }
    }
}
