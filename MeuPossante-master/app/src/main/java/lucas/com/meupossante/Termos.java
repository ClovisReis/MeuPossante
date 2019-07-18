package lucas.com.meupossante;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Termos extends AppCompatActivity {


    private RadioGroup group;
    private Toolbar toolbar;
    private static final String NOME = "ACORDO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SharedPreferences pref = getSharedPreferences(NOME,0);
        if(pref.getString("status","").length() > 0){
            Intent it = new Intent(this, Cadastro.class);
            startActivity(it);
            finish();
        }
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
        // getSupportActionBar().setIcon(R.drawable.ic_logo);

    }

    public void OK(View view){
        if(view.getId() == R.id.aceito) {
            group  = (RadioGroup) findViewById(R.id.group);
            boolean concorda = R.id.Sim == group.getCheckedRadioButtonId();
            if(concorda){
                SharedPreferences pref = getSharedPreferences(NOME, 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("status","Concorda");
                editor.commit();
                Intent it = new Intent(this, Cadastro.class);
                startActivity(it);
                finish();
            }
            else{
                LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflate.inflate(R.layout.toast_inflate_termos, null);
                Toast toast = new Toast(this);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setView(layout);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
            }


        }

        }

        @Override
    protected void onDestroy(){
        super.onDestroy();
            Log.i("Destroi","onDestroy()");
        }



}
