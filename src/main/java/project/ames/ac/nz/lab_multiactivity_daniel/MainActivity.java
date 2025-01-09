package project.ames.ac.nz.lab_multiactivity_daniel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //About AMES - 1: Declare "aboutAmes" variable as ImageView type
    private ImageView aboutAmes;

    //About AC - 1: Declare "aboutAC" variable as ImageView type
    private ImageView aboutAC;

    //View All Programmes - 1: Declare "viewAllProgrammes" variable as ImageView type
    private ImageView viewAllProgrammes;

    //Apply - 1: Declare "apply" variable as ImageView type
    private ImageView apply;

    //Contact us - 1: Declare "contactUs" variable as ImageView type
    private ImageView contactUs;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //About AMES - 2: Find references and do casting for "aboutAmes"
        aboutAmes = (ImageView) findViewById(R.id.aboutAMES);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //About AMES - 3: Set click listener for the "aboutAmes" ImageView
        aboutAmes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an Intent object to transition from this activity (MainActivity) to
                // another activity (AboutAMES_Activity)
                Intent intent = new Intent(MainActivity.this, AboutAMES_Activity.class);
                startActivity(intent);//Start Activity
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        //About AC - 2: Find references and do casting for "aboutAC"
        aboutAC = (ImageView) findViewById(R.id.aboutAC);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //About AC - 3: Set click listener for the "aboutAmes" ImageView
        aboutAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an Intent object to transition from this activity (MainActivity) to
                // another activity (AboutAC_Activity)
                Intent intent = new Intent(MainActivity.this, AboutAC_Activiy.class);
                startActivity(intent);//Start Activity
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        //View All Programmes - 2: Find references and do casting for "viewAllProgrammes"
        viewAllProgrammes = (ImageView) findViewById(R.id.viewAllProgrammes);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //View All Programmes - 3: Set click listener for the "viewAllProgrammes" ImageView
        viewAllProgrammes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an Intent object to transition from this activity (MainActivity) to
                // another activity (ListViewProgrammes_Activity)
                Intent intent = new Intent(MainActivity.this, ListViewProgrammes_Activity.class);
                startActivity(intent);//Start Activity
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Apply - 2: Find references and do casting for "apply"
        apply = (ImageView) findViewById(R.id.apply);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //View All Programmes - 3: Set click listener for the "apply" ImageView
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an Intent object to transition from this activity (MainActivity) to
                // another activity (Enrol_Activity)
                Intent intent = new Intent(MainActivity.this, Enrol_Activity.class);
                startActivity(intent);//Start Activity
            }
        });


        ////////////////////////////////////////////////////////////////////////////////////////////
        //Apply - 2: Find references and do casting for "contactUs"
        contactUs = (ImageView) findViewById(R.id.contact_us);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //View All Programmes - 3: Set click listener for the "contactUs" ImageView
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an Intent object to transition from this activity (MainActivity) to
                // another activity (ContactUs_Activity)
                Intent intent = new Intent(MainActivity.this, ContactUs_Activity.class);
                startActivity(intent);//Start Activity
            }
        });


    }
}
