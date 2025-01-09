package project.ames.ac.nz.lab_multiactivity_daniel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Declare variable
    private Button aboutCollegeBtn, viewBCSProgramBtn, contactUsBtn;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find references and do casting for 3 Buttons
        aboutCollegeBtn = (Button) findViewById(R.id.aboutCollegeBtn);
        viewBCSProgramBtn = (Button) findViewById(R.id.bcsProgramBtn);
        contactUsBtn = (Button) findViewById(R.id.contactBtn);

        //Set listener for the "About College" Button
        aboutCollegeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an Intent object to transition from this activity (MainActivity) to
                // another activity (AboutCollege_Activity)
                Intent intent = new Intent(MainActivity.this, AboutCollege_Activity.class);
                startActivity(intent);
            }
        });

        //Set listener for the "BCS Program" Button
        viewBCSProgramBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an Intent object to transition from this activity (MainActivity) to
                // another activity (BCSProgram_Activity)
                Intent intent = new Intent(MainActivity.this, BCSProgram_Activity.class);
                startActivity(intent);
            }
        });

        //Set listener for the "Contact Us" Button
        contactUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an Intent object to transition from this activity (MainActivity) to
                // another activity (ContactUs_Activity)
                Intent intent = new Intent(MainActivity.this, ContactUs_Activity.class);
                startActivity(intent);
            }
        });
    }
}
