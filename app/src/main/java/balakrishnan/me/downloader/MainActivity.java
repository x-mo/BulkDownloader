package balakrishnan.me.downloader;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import balakrishnan.me.bulkdownloader.ImageDownloaderException;
import balakrishnan.me.bulkdownloader.ImageDownloaderHelper;
import balakrishnan.me.bulkdownloader.ProgressModel;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Nammu.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
            @Override
            public void permissionGranted() {
                try {

                    File folder = new File(Environment.getExternalStorageDirectory() +
                            File.separator + getPackageName() + File.separator + "images");
                    boolean success = true;
                    FileUtils.deleteDirectory(folder);
                    if (!folder.exists()) {
                        success = folder.mkdirs();
                    }
                    if (success) {
                        Log.d("mkdir", "success");
                        // Do something on success
                    } else {
                        Log.d("mkdir", "failure");
                        // Do something else on failure
                    }

                    new ImageDownloaderHelper().setDownloadStatus(getCallback())
                            .setUrl("https://5bc9d0eb57adaa001375b1c6.mockapi.io/sampleget")
                            .setCollectionId(500)
                            .createImageDownloadWorkURl();

                } catch (ImageDownloaderException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void permissionRefused() {
//                Toast.makeText(this, R.string.permission_required, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public ImageDownloaderHelper.DownloadStatus getCallback() {
        return new ImageDownloaderHelper.DownloadStatus() {
            @Override
            public void DownloadedItems(int totalurls, int downloadPercentage, int successPercent, int failurePercent) {
//               You can get Total Image Downloaded progress here
                Log.d(TAG, "DownloadedItems: " + downloadPercentage);
            }

            @Override
            public void CurrentDownloadPercentage(LinkedHashMap<String, ProgressModel> trackRecord) {
//              You can get current file downloaded progress here.
//                Log.d(TAG, "CurrentDownloadPercentage: " + trackRecord.size());
//                for (ProgressModel progressModel : trackRecord.values()) {
////                   Percentage downloaded
//                    Log.d(TAG, "CurrentDownloadPercentage: getProgress: " + progressModel.getProgress());
////                    Downloaded Size in bytes
//                    Log.d(TAG, "CurrentDownloadPercentage: getDownloadedSize: " + progressModel.getDownloadedSize());
////                    Downloaded Size in MB
//                    Log.d(TAG, "CurrentDownloadPercentage: getDownloadedSizeInMB: " + progressModel.getDownloadedSizeInMB());
////                    File Size in bytes
//                    Log.d(TAG, "CurrentDownloadPercentage: getFileSize: " + progressModel.getFileSize());
////                    File Size in MB
//                    Log.d(TAG, "CurrentDownloadPercentage: getFileSizeInMB: " + progressModel.getFileSizeInMB());
//                }
            }

        };
    }
}
