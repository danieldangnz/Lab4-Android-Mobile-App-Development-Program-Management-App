package project.ames.ac.nz.lab_multiactivity_daniel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    }
}
