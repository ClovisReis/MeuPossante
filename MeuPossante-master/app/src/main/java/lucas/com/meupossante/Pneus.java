package lucas.com.meupossante;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Pneus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pneus);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void pneusClick(View view){
        if(view.getId() == R.id.salvarPneus) {
            RadioGroup group  = (RadioGroup) findViewById(R.id.pneusGroup);
            boolean concorda = R.id.radio1Pneus == group.getCheckedRadioButtonId();
            if(concorda){

            }
            else{

            }
            finish();

        }

    }
}
