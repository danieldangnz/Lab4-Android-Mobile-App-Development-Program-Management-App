package project.ames.ac.nz.lab_multiactivity_daniel;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Enrol_Activity extends AppCompatActivity {
    ////////////////////////////////////////////////////////////////////////////////////
    //Declare global objects and variables
    //Spinner - 1: declare variable "selectPrograms_spinner"
    private Spinner selectPrograms_spinner;
    //
    private String[] programs_array;
    private int program_selected_id;

    //Enrolment form - 1: Declare variables
    private EditText firstname, lastname;
    private EditText emailAddress, phoneNumber, birthDate;
    //private EditText address, lastSecondary;
    private RadioButton domestic_yes, domestic_no;
    private Button submitBtn;
    private String enrolment_form;

    ////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enrolment_form_layout);

        //////////////////////////////////////////////////////////////////////////////////
        //Spinner - 2: Find control elements and do casting for "selectPrograms_spinner"
        selectPrograms_spinner = (Spinner) findViewById(R.id.spinner_ames_programmes);

        //////////////////////////////////////////////////////////////////////////////////
        //Spinner - 3: Populate spinner or drop-down menu with 12 AMES programmes
        //1: Retrieve 12 AMES programmes declared in strings.xml file ("all_programmes_array")
        programs_array = getResources().getStringArray(R.array.all_programmes_array);
        //2: Declare a list of programmes to be selected
        List<String> program_list = new ArrayList<>(Arrays.asList(programs_array));
        //3: Create Adapter [ArrayAdapter, datatype:String] for spinner
        ArrayAdapter<String> program_Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                program_list);
        //4: Define drop layout style - list view with radio button for program_Adapter
        program_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //5: Attach Adapter to Spinner: Attach program_Adapter programs_spinner ()
        selectPrograms_spinner.setAdapter(program_Adapter);
        //6: Set the default
        selectPrograms_spinner.setSelection(0, true);// Position = 0: BCS programme
        //7: Set the text color of the spinner's default item
        View program_spinner_item_view = selectPrograms_spinner.getSelectedView();
        ((TextView) program_spinner_item_view).setTextColor(Color.parseColor("#FF000000"));
        ((TextView) program_spinner_item_view).setTextSize(15);

        //8: Set the listener when each option (item) of programs_spinner has been clicked,
        // and then set color for selected item
        selectPrograms_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Override onItemSelected() method
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //parent = AdapterView where the selection happend
                //view = the view within the AdapterView that was clicked
                //position = the position of the view in the adapter
                //id = the row id of the item that has ben selected
                //Set the text color and text size for the selected item
                ((TextView) view).setTextColor(Color.parseColor("#FF0818a1"));
                //Try other method: Color.rgb(200,0,0);
                ((TextView) view).setTextSize(16);
                //Get the id of selected item, this id will be used later process
                program_selected_id = position;
            }

            //Override onNothingSelected() method
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //////////////////////////////////////////////////////////////////////////////////
        //Enrolment form - 2: Find control elements and do casting for all UI elements
        firstname = (EditText) findViewById(R.id.firstnameEdt);
        lastname = (EditText) findViewById(R.id.lastnameEdt);
        emailAddress = (EditText) findViewById(R.id.emailEdt);
        phoneNumber = (EditText) findViewById(R.id.phoneNumberEdt);
        birthDate = (EditText) findViewById(R.id.birthDateEdt);
        //address
        //lastSecondary
        domestic_yes = (RadioButton) findViewById(R.id.domestic_Yes);
        domestic_no = (RadioButton) findViewById(R.id.domestic_No);
        submitBtn = (Button) findViewById(R.id.submitBtn);


        //////////////////////////////////////////////////////////////////////////////////
        //Enrolment form - 3: When users click “Submit” button, check all the fields in the form
        // with *symbol to make sure that all those fields are filled in.
        // Then the app uses SmsManager API to send SMS to the college or use implicit Intent
        // (Intent.ACTION_SEND) to send enrolment form by email to the college
        //Set Click Listener for "Submit" button
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1: Get all information on Enrolment Form
                //Get programme selected/interested
                String program_selected = programs_array[program_selected_id];
                //Get full-name
                String fullname = firstname.getText().toString() + " " + lastname.getText().toString();
                //Get email address
                String email = emailAddress.getText().toString();
                //Get phone number
                String phone = phoneNumber.getText().toString();
                //Get date of birth
                final String birthday = birthDate.getText().toString();
                //Get "Domestic" student status
                String domestic = "";
                if (domestic_yes.isChecked()) {
                    domestic = "Yes";
                } else if (domestic_no.isChecked()) {
                    domestic = "No";
                }

                //2: Compose the content of "enrolment_form" by combining all fields in the enrolment form
                enrolment_form = "Fullname: " + fullname + ". "
                        + "Email: " + email + ". "
                        + "DoB: " + birthday + ". "
                        + "Mobile: " + phone + ". "
                        + "Program: " + program_selected + ". "
                        + "Domestic: " + domestic;

                //3: Check whether firstname, lastname, email, phone, DoB have been filled in
                if (TextUtils.isEmpty(firstname.getText().toString())
                        || TextUtils.isEmpty(lastname.getText().toString())
                        || TextUtils.isEmpty(emailAddress.getText().toString())
                        || TextUtils.isEmpty(phoneNumber.getText().toString())
                        || TextUtils.isEmpty(birthDate.getText().toString())) {
                    //If any of these fields is empty, pop up a message to ask user to fill in
                    Toast.makeText(getApplicationContext(), "Fill all fields (*) first",
                            Toast.LENGTH_LONG).show();
                    //Return enrolment_form_layout
                    return;
                }

                //4: Send enrolment form by SMS to college number: 02108219683
                //College contact information
                String college_phoneNumber = "02108219683";//Change to your phone number to TEST
                String college_email = "bcs.amesit@gmail.com";
                //IMPLEMENT SENDING SMS IN ANDROID
                //Check the "android.permission.SEND_SMS" permission in AndroidManifest
                if (ContextCompat.checkSelfPermission(Enrol_Activity.this,
                        Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    //Should we shown an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Enrol_Activity.this,
                            Manifest.permission.SEND_SMS)) {
                        Toast.makeText(getBaseContext(), "Permission required", Toast.LENGTH_SHORT).show();
                    } else {
                        //No explanation needed, we can request the permission
                        ActivityCompat.requestPermissions(Enrol_Activity.this,
                                new String[]{Manifest.permission.SEND_SMS}, 1);
                    }

                } else {
                    //Permission already granted run sms send -> send enrolment_from by SMS
                    //Initiate an object "intent" linked to SmsSender class
                    Intent intent = new Intent(getApplicationContext(), Enrol_Activity.class);
                    //initiate an object of PendingIntent
                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                    //Call the method sendTextMessage() to send SMS messages with a PendingIntent
                    SmsManager sms = SmsManager.getDefault();
                    //Send message
                    sms.sendTextMessage(college_phoneNumber, null, enrolment_form, pi, null);
                    Toast.makeText(Enrol_Activity.this, "Message Sent", Toast.LENGTH_SHORT).show();
                }
            }
        });












        /*


    ////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onClick(View myView) {
        //Declare local objects and variables
        //Get name
        String name = fullName.getText().toString();
        //Get email address
        String email = emailAddress.getText().toString();
        //Get date of birth
        String date = birthDate.getText().toString();
        //Get phone number
        String phone = phoneNumber.getText().toString();

        //Get select program interested
        String program_selected = programs_array[program_selected_id];

        //Get previous tertiary study
        String tertiaryStudy = "Yes";
        if (domestic_yes.isChecked()) {
            tertiaryStudy = "Yes";
        } else {
            tertiaryStudy = "No";
        }

        //Compose the application form content to send
        String application_form = "Application form.\n" + "Fullname: " + name + ".\n"
                + "Email: " + email + ".\n"
                + "Date of birth: " + date + ".\n"
                + "Contact number/mobile: " + phone + ".\n"
                + "Program interested: " + program_selected + ".\n"
                + "Previous tertiary study: " + tertiaryStudy;


        //Check name and email to make sure that they are filled in
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)) {
            //If name or email is empty, pop up a message to ask user to fill in
            Toast.makeText(this, "You have to enter both fullname and email address", Toast.LENGTH_LONG).show();
            //Quit the onClick() method
            return;
        }

        //Information
        String college_phoneNumber = "02108219683";
        String college_email = "trinhdtr@gmail.com";

        switch (myView.getId()) {

            case R.id.viaEmail_btn:
                //"Send via Email" button has been clicked
                //IMPLEMENT SENDING EMAIL
                //Create an emailIntent with "ACTION_SEND" property
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                //Set data for emailIntent
                emailIntent.setData(Uri.parse("mailto:"));//only email apps should handle this
                //set type of text
                emailIntent.setType("text/plain");
                //Add recipient address to the Intent
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{college_email});
                //Add Email subject to the Intent
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Application Form");
                //Add Email body text to Intent
                emailIntent.putExtra(Intent.EXTRA_TEXT, application_form);
                //This will make such that when user returns to their app, their app is displayed,
                //instead of the email app.
                emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //Catch exception when sending email
                try {
                    //Start An activity to send email
                    startActivity(Intent.createChooser(emailIntent, "Choose a Mail Client ...."));
                    //After selecting one of Email Client
                    Log.i("Application Form: ", "Email sent successfully");
                    Toast.makeText(this, "Email sent successfully", Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException ex) {
                    //When error happens, display a message
                    Toast.makeText(this, "There is no email client installed", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.viaSMS_btn:
                //"Send via Email" button has been clicked
                //IMPLEMENT SENDING SMS
                //Check the request permission to view and send SMS
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) !=
                        PackageManager.PERMISSION_GRANTED) {
                    //Should we shown an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                        Toast.makeText(getBaseContext(), "Permission required", Toast.LENGTH_SHORT).show();
                    } else {
                        //No explanation needed, we can request the permission
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
                    }
                } else {
                    // permission already granted run sms send
                    //Initiate an object "intent" linked to SmsSender class
                    Intent intent = new Intent(this, ApplicationForm_Activity.class);
                    //initiate an object of PendingIntent
                    PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
                    //Call the method sendTextMessage() to send SMS messages with a PendingIntent
                    SmsManager sms = SmsManager.getDefault();
                    //Send message
                    sms.sendTextMessage(college_phoneNumber, null, application_form, pi, null);
                    Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
                }
                break;


            default:
                //Errors or Exceptions - Do nothing
                break;

        }
        */
    }
}
