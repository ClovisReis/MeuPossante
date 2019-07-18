package lucas.com.meupossante;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class Agua extends AppCompatActivity {

    EditText aguaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agua);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        aguaEditText = (EditText) findViewById(R.id.servicoedit);
        aguaEditText.addTextChangedListener(new NumberTextWatcherForThousand(aguaEditText));

    }

    public void aguaClick(View view){
        if(view.getId() == R.id.salvarAgua) {
            RadioGroup group  = (RadioGroup) findViewById(R.id.aguaGroup);
            boolean concorda = R.id.radio1Agua == group.getCheckedRadioButtonId();
            if(concorda){

            }
            else{

            }
            finish();

        }

    }
}
