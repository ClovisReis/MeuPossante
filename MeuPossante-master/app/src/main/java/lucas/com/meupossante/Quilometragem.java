package lucas.com.meupossante;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static lucas.com.meupossante.Cadastro.ARQUIVO;
import static lucas.com.meupossante.Cadastro.ARQUIVOQUILO;

public class Quilometragem extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public TextView apelido;
    public EditText quilo;

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quilometragem);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.drawable.ic_logo);

        //EXPANDABLE
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("manutencao", "Permission is not granted, requesting");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
        } else {
            Log.d("manutencao", "Permission is granted");
        }

        // APELIDO e QUILOMETRAGEM DO CARRO VIA ARQUIVO
        apelido = (TextView) findViewById(R.id.veiculo1);
        quilo = (EditText) findViewById(R.id.texto);
        quilo.addTextChangedListener(new NumberTextWatcherForThousand(quilo));
        File f = getFileStreamPath(ARQUIVO);
        File q = getFileStreamPath(ARQUIVOQUILO);
        try {
            if (f.exists()) {
                FileInputStream in = openFileInput(ARQUIVO);
                int tamanho = in.available();
                byte bytes[] = new byte[tamanho];
                in.read(bytes);
                String s = new String(bytes);
                apelido.setText(s);
            }

            if(q.exists()){
                FileInputStream in = openFileInput(ARQUIVOQUILO);
                int tamanho = in.available();
                byte bytes[] = new byte[tamanho];
                in.read(bytes);
                String s = new String(bytes);
                quilo.setHint("       "+s);
            }
        }

        catch(FileNotFoundException e){
            Log.e("Quilometragem", "Arquivo não encontrado" + e.getMessage(), e);
        }

        catch(IOException e){
            Log.e("Quilometragem", e.getMessage(), e);
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


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.primeiroveiculo){
            Intent i = new Intent(getApplicationContext(), Rastreio.class);
            startActivity(i);
        }  else if(id == R.id.nav_configuracoes){
            Intent i = new Intent(getApplicationContext(), Configuracoes.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
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
                    Intent it = new Intent(Quilometragem.this,Manutencao.class);
                    startActivity(it);
                    onBackPressed();
                }
                else if (groupPosition == 0 && childPosition == 2) {
                    Intent it = new Intent(Quilometragem.this,Cadastro.class);
                    it.putExtra("chave","alterar");
                    startActivity(it);
                    onBackPressed();
                }

                return false;
            }
        });
    }

    public void salvarQuilometragemClick(View view) {

        if (view.getId() == R.id.salvarQuilometragem) {
            try {
                FileOutputStream outquilometragem = openFileOutput(ARQUIVOQUILO, MODE_PRIVATE);
                outquilometragem.write(quilo.getText().toString().getBytes());
                outquilometragem.close();

            } catch (FileNotFoundException e) {
                Log.e("Arquivo nao encontrado", e.getMessage(), e);
            } catch (IOException e) {
                Log.e("Erro Arquivo Exception", e.getMessage(), e);
            }

        }
        finish();
    }



}
