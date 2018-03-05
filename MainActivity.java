package emreyuksell.simplecurrencyconverter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView textView,textView1,textView2;
    EditText editText;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    public void init(){
        textView = findViewById(R.id.text);
        textView1 = findViewById(R.id.text2);
        textView2 = findViewById(R.id.text3);
        editText = findViewById(R.id.editText);
    }

    public void getRates(View view){

        DownloadData downloadData = new DownloadData();

        try{

            String url = "https://api.fixer.io/latest?base=";
            String chosenBase = editText.getText().toString();
            downloadData.execute(url+chosenBase);

        }catch (Exception e){

            e.printStackTrace();

        }

    }

    private class DownloadData extends AsyncTask<String, Void, String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try{

                JSONObject jsonObject = new JSONObject(s);
                String base= jsonObject.getString("base");
                String date= jsonObject.getString("date");
                String rates= jsonObject.getString("rates");
                JSONObject jsonObject1 = new JSONObject(rates);
                String usd = jsonObject1.getString("USD");
                String tl = jsonObject1.getString("TRY");
                textView.setText("TRY:"+tl);
                textView1.setText("USD:"+usd);


            }catch (Exception e){
                e.printStackTrace();

            }

        }
        //Go to website and get the data
        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;
            try{
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream =  httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader= new InputStreamReader(inputStream);

                //Read Data
                int data= inputStreamReader.read();

                while( data > 0 ){

                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();


                }

                return result;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }


        }
    }

}
