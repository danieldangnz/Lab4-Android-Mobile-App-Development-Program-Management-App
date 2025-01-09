package project.ames.ac.nz.lab_multiactivity_daniel;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ProgrammeDetail_Activity extends AppCompatActivity {
    //////////////////////////////////////////////////////////////////////////////////////////
    //1: Declare variables
    private TextView selected_program;
    private ImageView programmeIllustrationImage;
    private TextView programDescription, programQualificationLevel, programDuration, programCareer;
    //Declare an array of 12 illustration images for 12 programmes
    private int[] illustration_images_array = {R.drawable.illustration_image1,
            R.drawable.illustration_image2, R.drawable.illustration_image3, R.drawable.illustration_image4,
            R.drawable.illustration_image5, R.drawable.illustration_image6, R.drawable.illustration_image7,
            R.drawable.illustration_image8, R.drawable.illustration_image9, R.drawable.illustration_image10,
            R.drawable.illustration_image11, R.drawable.illustration_image12};
    //Other "String" variables storing programme details
    private String programTitle, description, qualificationLevel, duration, career;
    private String program_name;//receive from ListviewProgrammes_Activity
    private int program_id;//receive from ListviewProgrammes_Activity

    //////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.programme_detail_layout);

        //2: Find references and do casting for visual elements: make connections between UI and java code
        selected_program = (TextView) findViewById(R.id.selected_program);
        programmeIllustrationImage = (ImageView) findViewById(R.id.programme_illustration_image);
        programDescription = (TextView) findViewById(R.id.programDescription);
        programQualificationLevel = (TextView) findViewById(R.id.qualification_level);
        programDuration = (TextView) findViewById(R.id.durationTxt);
        programCareer = (TextView) findViewById(R.id.careerTxt);

        //3: Extract data stored in "Intent" object sent from ListView_Activity and display it
        //Extract "program_name" attached with "Intent" -> then display it in "selected_program" TextView
        program_name = getIntent().getExtras().getString("program_name");
        selected_program.setText(program_name);
        //Extract "program_id" attached with "Intent" -> display the corresponding programme illustration image
        program_id = getIntent().getExtras().getInt("program_id");
        programmeIllustrationImage.setImageResource(illustration_images_array[program_id]);

        //4: Extract the relevant information from "all_programs_detail.xml" file by using XML parser
        try {
            //5: Open all_programs_detail.xml file for XMLPullParser

            //------------------------------------------------------------------
            //Method 1: Open xml file stored in "Asset" folder for XMLPullParser
            //InputStream inputStream = getAssets().open("all_programs_detail.xml");
            //------------------------------------------------------------------
            //Method 2: Open XML file directly online on Internet
            //String url = "https://sites.google.com/site/cs102mobileappdev/xml_file/all_programs_detail.xml";
            //InputStream inputStream = new URL(url).openStream();
            //------------------------------------------------------------------
            //Method 3: Open XML file already downloaded & saved in external storage of device
            String fpath = Environment.getExternalStorageDirectory().toString() + "/MyDownloadFile/all_programs_detail.xml";
            File file = new File(fpath);
            InputStream inputStream = null;
            //Check if the file exists
            if (file.exists()) {
                //File exists, load it to inputStream
                inputStream = new FileInputStream(file);
            } else {
                //Load "default" xml file in "asset" folder
                //5: Open program_detail.xml file stored in Assets folder
                inputStream = getAssets().open("all_programs_detail.xml");
                Toast.makeText(this, "Allow app access photos, media and fileson your device", Toast.LENGTH_SHORT).show();
            }

            //-------------------------------------------------------------------------
            //6: Use of XML DOM Parser for extracting data
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance().newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element element = document.getDocumentElement();
            element.normalize();

            //7: Read all the nodes containing tag "program"
            NodeList nodeList = document.getElementsByTagName("program");

            //8: Loop through all nodes to find the relevant selected program
            boolean program_found = false;
            for (int i = 0; i < nodeList.getLength(); i++) {
                //9: Get "node" in xml file
                Node node = nodeList.item(i);
                Element sub_Element = (Element) node;
                //9: Get the program "title"
                programTitle = sub_Element.getElementsByTagName("title").item(0)
                        .getChildNodes().item(0).getNodeValue();
                programDescription.setText(programTitle);
                //10: Check if the program title is the selected program. If yes, display its detail
                if (programTitle.contains(program_name)) {
                    description = sub_Element.getElementsByTagName("description").item(0)
                            .getChildNodes().item(0).getNodeValue();
                    qualificationLevel = sub_Element.getElementsByTagName("qualification_level").item(0)
                            .getChildNodes().item(0).getNodeValue();
                    duration = sub_Element.getElementsByTagName("duration").item(0)
                            .getChildNodes().item(0).getNodeValue();
                    career = sub_Element.getElementsByTagName("career").item(0)
                            .getChildNodes().item(0).getNodeValue();
                    //11: Change the variable program_found to "true"
                    program_found = true;
                }
            }

            //11: If not found any program in the xml file, assign all variables to "Not found"
            if (!program_found) {
                description = "Not found";
                qualificationLevel = "Not found";
                duration = "Not found";
                career = "Not found";
            }

            //12: Display the extracted program details into the TextView on Layout
            programDescription.setText(description);
            programQualificationLevel.setText(qualificationLevel);
            programDuration.setText(duration);
            programCareer.setText(career);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "Load xml file");
        }
    }
}
