package lucas.com.meupossante;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


import static lucas.com.meupossante.Cadastro.ARQUIVO;

public class Principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public TextView apelido;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    ImageView icones;
    //Alerta de mes em mes
    public static final String ARQUIVOALERTA = "flagalerta.txt";
    final long mes = Long.parseLong("2628000000");
    private long soma;
    private String dataalerta;
    private String valorarquivo = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.drawable.ic_logo);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Alerta de mes em mes

        long dataatualmilis = Calendar.getInstance().getTimeInMillis();

        File aa = getFileStreamPath(ARQUIVOALERTA);
        try{
            if (aa.exists()) {
                FileInputStream in = openFileInput(ARQUIVOALERTA);
                int tamanho = in.available();
                byte bytes[] = new byte[tamanho];
                in.read(bytes);
                valorarquivo = new String(bytes);
            }

            if(valorarquivo.equals("0")){}

            else if(dataatualmilis>=Long.parseLong(valorarquivo) && valorarquivo != "1") {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Principal.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();


                soma = (dataatualmilis + mes);
                dataalerta = String.valueOf(soma);

                try {
                    FileOutputStream out = openFileOutput(ARQUIVOALERTA, MODE_PRIVATE);
                    out.write(dataalerta.getBytes());
                    out.close();

                } catch (FileNotFoundException e) {
                    Log.e("Arquivo nao encontrado", e.getMessage(), e);
                } catch (IOException e) {
                    Log.e("Erro Arquivo Exception", e.getMessage(), e);
                }


                //Click

                Button mLogin = (Button) mView.findViewById(R.id.btnLink);
                Button mLogin2 = (Button) mView.findViewById(R.id.btnCancelar);
                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // Cria arquivo para armezenar a flag que indica que já foi avaliado
                        try {
                            FileOutputStream out = openFileOutput(ARQUIVOALERTA, MODE_PRIVATE);
                            out.write("0".getBytes());
                            out.close();

                        } catch (FileNotFoundException e) {
                            Log.e("Arquivo nao encontrado", e.getMessage(), e);
                        } catch (IOException e) {
                            Log.e("Erro Arquivo Exception", e.getMessage(), e);
                        }

                        //Link para a google play

                        String url = "http://google.com";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        dialog.dismiss();
                    }
                });

                mLogin2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }


                });

            }
            else if(!aa.exists()) {
                soma = (dataatualmilis + mes);
                dataalerta = String.valueOf(soma);

                try {
                    FileOutputStream out = openFileOutput(ARQUIVOALERTA, MODE_PRIVATE);
                    out.write(dataalerta.getBytes());
                    out.close();

                } catch (FileNotFoundException e) {
                    Log.e("Arquivo nao encontrado", e.getMessage(), e);
                } catch (IOException e) {
                    Log.e("Erro Arquivo Exception", e.getMessage(), e);
                }

            }


        }
        catch(FileNotFoundException e){
            Log.e("Alerta", "Arquivo não encontrado" + e.getMessage(), e);
        }

        catch(IOException e){
            Log.e("Alerta", e.getMessage(), e);
        }


    }



    public void Principais(View view){
        if(view.getId() == R.id.manut){
            Intent it = new Intent(this, Manutencao.class);
            startActivity(it);

        }
        else if(view.getId() == R.id.altregistro){
            Intent it = new Intent(this, Cadastro.class);
            it.putExtra("chave","alterar");
            startActivity(it);
        }

        else if(view.getId() == R.id.quilometragem){
            Intent it = new Intent(this, Quilometragem.class);
            startActivity(it);

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.primeiroveiculo) {
            // Handle the camera action
        } else if (id == R.id.nav_configuracoes) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareMenuData() {

        //Veículo 1

        MenuModel menuModel = new MenuModel("Veículo", true, true, "ic_carro"); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Manutenção", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Quilometragem", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Alterar Registro", false, false);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        //Configurações

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Configurações", true, true, "ic_menu_preferences"); //Menu of Python Tutorials
        headerList.add(menuModel);

        childModel = new MenuModel("Notificações", false, false);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }


        //Compra

        menuModel = new MenuModel("Comprar Aplicativo", true, false, "ic_menu_help"); //Menu of Python Tutorials
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        //Fale com a gente

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Fale com a gente", true, true); //Menu of Python Tutorials
        headerList.add(menuModel);

        childModel = new MenuModel("meupossante", false, false, "instagram");
        childModelsList.add(childModel);

        childModel = new MenuModel("aplicativomeupossante", false, false, "facebook");
        childModelsList.add(childModel);

        childModel = new MenuModel("aplicativomeupossante@gmail.com", false, false, "email");
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }


    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        //WebView webView = findViewById(R.id.webView);
                        //webView.loadUrl(headerList.get(groupPosition).url);
                        onBackPressed();
                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (groupPosition == 0 && childPosition == 0) {
                    Intent it = new Intent(Principal.this,Manutencao.class);
                    startActivity(it);
                    onBackPressed();
                }
                else if (groupPosition == 0 && childPosition == 1) {
                    Intent it = new Intent(Principal.this,Quilometragem.class);
                    startActivity(it);
                    onBackPressed();
                }
                else if (groupPosition == 0 && childPosition == 2) {
                    Intent it = new Intent(Principal.this,Cadastro.class);
                    it.putExtra("chave","alterar");
                    startActivity(it);
                    onBackPressed();
                }

                else if(groupPosition == 3 && childPosition == 0){
                    String url = "http://www.instagram.com/meupossante";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }

                else if(groupPosition == 3 && childPosition == 1){
                    String url = "http://www.facebook.com/aplicativomeupossante";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }

                else if(groupPosition == 3 && childPosition == 2){
                    String url = "http://gmail.com";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }


                return false;
            }
        });
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("Destroi","onDestroy()");
    }

}
