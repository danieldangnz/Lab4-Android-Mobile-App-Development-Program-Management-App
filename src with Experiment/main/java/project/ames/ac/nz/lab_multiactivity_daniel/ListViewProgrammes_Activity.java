package project.ames.ac.nz.lab_multiactivity_daniel;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ListViewProgrammes_Activity extends AppCompatActivity {
    /////////////////////////////////////////////////////////////////////////////////////////
    //1: Declare variables
    private ListView listView;
    private String[] programs_array;

    /////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_programmes_layout);

        //2: Find references and do casting to make the link between front-end and back-end
        listView = (ListView) findViewById(R.id.listview_menu);

        //3: Retrieve the "all_programmes_array" declared in strings.xml
        //Use getResource() function to access to String-array stored in strings.xml
        programs_array = getResources().getStringArray(R.array.all_programmes_array);

        //4: Declare an ArrayAdapter to create the list with programs_array being laid out
        ArrayAdapter<String> arrayAdapt = new ArrayAdapter<String>(this, R.layout.item_layout, programs_array);
        listView.setAdapter(arrayAdapt);

        //5: Make the ListView actionable by declaring an onClick() listener for each of them.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                //Read out the name of clicked item
                TextView tvItemClicked = (TextView) itemClicked;
                String strItemClicked = tvItemClicked.getText().toString();
                //Pop up a Toast to display the selected programme in the ListView
                Toast.makeText(getApplicationContext(), strItemClicked, Toast.LENGTH_SHORT).show();

                //6: Create an "Intent" object to transition from this activity (ListViewProgrammes_Activity) to
                // another activity (ProgrammeDetail_Activity)
                Intent intent = new Intent(ListViewProgrammes_Activity.this, ProgrammeDetail_Activity.class);
                //Attach "data" associated with "intent" object: "program_name" and "program_id"
                intent.putExtra("program_name", programs_array[position]);//Store program title
                intent.putExtra("program_id", position);//Store the order of programmes in "all_programmes_array"
                //Start activity
                startActivity(intent);
            }
        });

        //Ask permission to WRITE and READ SDCard
        askForPermission("android.permission.WRITE_EXTERNAL_STORAGE", 1);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Download XML file to storage in device
        try {
            //------------------------------------------------------------------
            //"Download XML file" from Internet
            //Create a new thread to download a text file from Internet
            //URL = https://sites.google.com/site/cs102mobileappdev/xml_file/all_programs_detail.xml
            final String file_url = "https://sites.google.com/site/cs102mobileappdev/xml_file/all_programs_detail.xml";
            final String savedFileName = "all_programs_detail.xml";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DownloadFiles(file_url, savedFileName);
                }
            }).start();
            //Toast.makeText(this, "Download successfully all_programs_detail.xml ", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "Download XML file");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void DownloadFiles(String fileURL, String fileName) {
        try {
            //Create URL object containing file URL to be downloaded
            URL url = new URL(fileURL);

            //Open Stream to download file from Internet (URL)
            InputStream inputStream = url.openStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            byte[] buffer = new byte[1024];
            int length;

            //Create a local file and channel DataInputStream into that file
            //Environment.getExternalStorageDirectory() returns the "/storage/sdcard" folder
            String path = Environment.getExternalStorageDirectory().toString();
            //Create folder "MyDownloadFile" to store my downloaded files ==> /storage/sdcard/MyDownLoadFile
            File folder = new File(path + "/MyDownloadFile");
            //Check whether the folder exists or not. If not, create the folder
            if (!folder.exists()) {
                folder.mkdirs();
            }
            //Create the file with fileName stored in the above created folder (local sdcard)
            File myFile = new File(folder.getAbsolutePath(), fileName);
            myFile.createNewFile();

            //Start downloading file and channel data into the created filed
            FileOutputStream fileOutputStream = new FileOutputStream(myFile);
            while ((length = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

            //When download process is done, send a broadcast message "File Download Finished"
            // with the intent filter "nz.ac.bcs.cs102.FileDownloadFinished"
            Intent broadcast_message = new Intent();
            broadcast_message.putExtra("MyBroadcastMessage", fileName + " - Download Finished!");
            broadcast_message.setAction("nz.ac.bcs.cs102.FileDownloadFinished");
            //Send  broadcast message
            //LoadFileFromInternet_Activity.context.sendBroadcast(broadcast_message);

        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate", "malformed url error", mue);
            //Broadcast "MalFormed URL Error" message
            Intent broadcast_message = new Intent();
            broadcast_message.putExtra("MyBroadcastMessage", "MalFormed URL Error");
            broadcast_message.setAction("nz.ac.bcs.cs102.FileDownloadFinished");
            //Send  broadcast message
            //LoadFileFromInternet_Activity.context.sendBroadcast(broadcast_message);

        } catch (IOException ioe) {
            Log.e("SYNC getUpdate", "io error", ioe);
            //Broadcast "IO Error" message
            Intent broadcast_message = new Intent();
            broadcast_message.putExtra("MyBroadcastMessage", "IO Error");
            broadcast_message.setAction("nz.ac.bcs.cs102.FileDownloadFinished");
            //Send  broadcast message
            //LoadFileFromInternet_Activity.context.sendBroadcast(broadcast_message);

        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
            //Broadcast "Security Error" message
            Intent broadcast_message = new Intent();
            broadcast_message.putExtra("MyBroadcastMessage", "Security Error");
            broadcast_message.setAction("nz.ac.bcs.cs102.FileDownloadFinished");
            //Send  broadcast message
            //LoadFileFromInternet_Activity.context.sendBroadcast(broadcast_message);
        }

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(ListViewProgrammes_Activity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ListViewProgrammes_Activity.this, permission)) {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(ListViewProgrammes_Activity.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(ListViewProgrammes_Activity.this, new String[]{permission}, requestCode);
            }
        } else {
            //createDirectory();
            // Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    ////////////////////////////////////////////////////////
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.v("onRequestpermission", requestCode + "//");
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                //Location
                case 4:
                    //createDirectory();
            }

        } else {
            Log.v("Permission Denied", requestCode + "else//");
        }
    }

}
