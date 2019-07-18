package lucas.com.meupossante;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lucas.com.meupossante.DAO.ItemDAO;
import lucas.com.meupossante.VO.ItemVO;
import lucas.com.meupossante.DAO.VeiculoDAO;
import lucas.com.meupossante.VO.VeiculoVO;

public class Cadastro extends AppCompatActivity {

    public EditText apelido;
    public EditText quilo;
    public EditText quilomedia;
    public EditText cadastroNomeEditText;
    public EditText cadastroKMEditText;
    public EditText cadastroRevisaoEditText;
    public Button cadastroOK;
    private VeiculoDAO veiculoDAO;
    private ItemDAO itemDAO;
    private Toolbar toolbar;
    HashMap<String, Float> itemList = new HashMap<>();
    private static final String NOME = "PARAM";
    public static final String ARQUIVO = "apelidos.txt";
    public static final String ARQUIVOQUILO = "quilometragem.txt";
    public static final String ARQUIVOMEDIA = "media.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cadastroKMEditText = (EditText) findViewById(R.id.cadastroKMEditText);
        cadastroRevisaoEditText = (EditText) findViewById(R.id.cadastroRevisaoEditText);
        cadastroNomeEditText = (EditText) findViewById(R.id.cadastroNomeEditText);
        cadastroOK = (Button) findViewById(R.id.cadastroOK);

        cadastroKMEditText.addTextChangedListener(new NumberTextWatcherForThousand(cadastroKMEditText));
        cadastroRevisaoEditText.addTextChangedListener(new NumberTextWatcherForThousand(cadastroRevisaoEditText));


        Intent alterar = getIntent();
        if(alterar.getExtras() == null) {

            SharedPreferences pref = getSharedPreferences(NOME, 0);
            if (pref.getString("status", "").length() > 0) {
                Intent it = new Intent(this, Principal.class);
                startActivity(it);
                finish();
            }
        }

        // Busca das informações dos arquivos

        apelido = (EditText) findViewById(R.id.cadastroNomeEditText);
        quilo = (EditText) findViewById(R.id.cadastroKMEditText);
        quilomedia = (EditText) findViewById(R.id.cadastroRevisaoEditText);
        File f = getFileStreamPath(ARQUIVO);
        File q = getFileStreamPath(ARQUIVOQUILO);
        File m = getFileStreamPath(ARQUIVOMEDIA);
        try {
            if (f.exists()) {
                FileInputStream in = openFileInput(ARQUIVO);
                int tamanho = in.available();
                byte bytes[] = new byte[tamanho];
                in.read(bytes);
                String s = new String(bytes);
                apelido.setText("       "+s);
            }

            if(q.exists()){
                FileInputStream in = openFileInput(ARQUIVOQUILO);
                int tamanho = in.available();
                byte bytes[] = new byte[tamanho];
                in.read(bytes);
                String s = new String(bytes);
                quilo.setHint("       "+s);
            }

            if(m.exists()){
                FileInputStream in = openFileInput(ARQUIVOMEDIA);
                int tamanho = in.available();
                byte bytes[] = new byte[tamanho];
                in.read(bytes);
                String s = new String(bytes);
                quilomedia.setHint("       "+s);
            }
        }

        catch(FileNotFoundException e){
            Log.e("Cadastro", "Arquivo não encontrado" + e.getMessage(), e);
        }

        catch(IOException e){
            Log.e("Cadastro", e.getMessage(), e);
        }

        //Fim da busca de informações do arquivo


        /*veiculoDAO = new VeiculoDAO(this);
        itemDAO = new ItemDAO(this);
        if(!veiculoDAO.findAll().isEmpty()){
            Intent i = new Intent(getApplicationContext(), Manutencao.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }

        //Inicialização dos itens da tabela Item. Hash de nomes e frequencias de trocas
        itemList.put("Água", (float) 999999999);
        itemList.put("Alinhamento", (float) 10000);
        itemList.put("Arrefecimento", (float) 30000);
        itemList.put("Balanceamento", (float) 10000);
        itemList.put("Bateria", (float) 10000);
        itemList.put("Cambagem", (float) 10000);
        itemList.put("Correia", (float) 50000);
        itemList.put("Embreagem", (float) 50000);
        itemList.put("Filtro de Ar", (float) 15000);
        itemList.put("Filtro de Combustível", (float) 10000);
        itemList.put("Freios", (float) 10000);
        itemList.put("Óleo", (float) 10000);
        itemList.put("Pneus", (float) 999999999);
        itemList.put("Revisão", (float) 10000);
        itemList.put("Rodízio de Pneus", (float) 10000);
        */
    }

    /*
        private class AsyncCreateDB extends AsyncTask<Void, Void, Void>
        {
            ProgressDialog pdLoading = new ProgressDialog(Cadastro.this);
            String veiculoNome = cadastroNomeEditText.getText().toString();
            Float veiculoKM = Float.parseFloat(cadastroKMEditText.getText().toString());
            Float veiculoRevisao = Float.parseFloat(cadastroRevisaoEditText.getText().toString());

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //this method will be running on UI thread
                pdLoading.setMessage("\t" + getResources().getString(R.string.carregando));
                pdLoading.show();
            }
            @Override
            protected Void doInBackground(Void... params) {
                VeiculoVO veiculo = new VeiculoVO();
                veiculo.setName(veiculoNome);
                veiculo.setQuilometragem(veiculoKM);
                veiculo.setUltima_revisao(veiculoRevisao);
                veiculoDAO.insert(veiculo);

                ItemVO item = new ItemVO();
                item.setUltima_troca(veiculoRevisao);

                for (Map.Entry<String, Float> entry : itemList.entrySet()) {
                    item.setName(entry.getKey());
                    item.setFrequencia(entry.getValue());
                    itemDAO.insert(item);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                //this method will be running on UI thread
                Intent i = new Intent(getApplicationContext(), Manutencao.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();

                pdLoading.dismiss();
            }
        }
    */
    public void onClick(View view) {
        if (view.getId() == R.id.cadastroOK) {
            if (cadastroNomeEditText.getText().length() == 0
                    || cadastroKMEditText.getText().length() == 0
                    || cadastroRevisaoEditText.getText().length() == 0) {
                Toast toast = Toast.makeText(this, "Por favor preencha todos os campos", Toast.LENGTH_SHORT);
                toast.show();
            }

            else {
                //new AsyncCreateDB().execute();

                //Criação de arquivos
                try{
                    FileOutputStream out = openFileOutput(ARQUIVO, MODE_PRIVATE);
                    out.write(cadastroNomeEditText.getText().toString().getBytes());
                    out.close();

                    FileOutputStream outquilometragem = openFileOutput(ARQUIVOQUILO, MODE_PRIVATE);
                    outquilometragem.write(cadastroKMEditText.getText().toString().getBytes());
                    outquilometragem.close();

                    FileOutputStream outmedia = openFileOutput(ARQUIVOMEDIA, MODE_PRIVATE);
                    outmedia.write(cadastroRevisaoEditText.getText().toString().getBytes());
                    outmedia.close();
                }

                catch(FileNotFoundException e){
                    Log.e("Arquivo nao encontrado",e.getMessage(), e);
                }
                catch (IOException e){
                    Log.e("Erro Arquivo Exception", e.getMessage(), e);
                }

                //Fim da Criação de arquivos


                //Inicio do SharedPreferences
                SharedPreferences pref = getSharedPreferences(NOME, 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("status","PARAM");
                editor.commit();
                Intent it = new Intent(this, Principal.class);
                //it.putExtra("chave",cadastroNomeEditText.getText().toString());
                startActivity(it);
                finish();
                //Fim do SharedPreferences
            }



        }



        // TOASTS INFLATER
        if (view.getId() == R.id.icduvidamedia) {
            final ImageView imgView = (ImageView) findViewById(R.id.mqdp);
            imgView.setVisibility(View.VISIBLE);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgView.setVisibility(View.INVISIBLE); // Ou View.GONE
                }
            }, 3000);
        }
        if (view.getId() == R.id.icduvidaquilo) {
            final ImageView imgView = (ImageView) findViewById(R.id.quilodiaria);
            imgView.setVisibility(View.VISIBLE);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgView.setVisibility(View.INVISIBLE); // Ou View.GONE
                }
            }, 3000);
        }
        if (view.getId() == R.id.icduvidaapelido) {
            final ImageView imgView = (ImageView) findViewById(R.id.veiculone);
            imgView.setVisibility(View.VISIBLE);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgView.setVisibility(View.INVISIBLE); // Ou View.GONE
                }
            }, 3000);
            //Toast toast = Toast.makeText(this,"Dê um apelido ao seu veículo", Toast.LENGTH_SHORT);
            //toast.show();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("Destroi","onDestroy()");
    }
}
