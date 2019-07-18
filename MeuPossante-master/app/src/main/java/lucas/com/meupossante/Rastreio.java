package lucas.com.meupossante;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.List;

import lucas.com.meupossante.DAO.ItemDAO;
import lucas.com.meupossante.VO.ItemVO;
import lucas.com.meupossante.DAO.VeiculoDAO;
import lucas.com.meupossante.VO.VeiculoVO;


public class Rastreio extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG = "rastreio";

    protected boolean mRequestingLocationUpdates;
    protected Switch rastreamentoSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rastreio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu1 = navigationView.getMenu();
        MenuItem veiculoTitle = menu1.findItem(R.id.name_veiculo);
        veiculoTitle.setTitle("Meu Veiculo");
        navigationView.setNavigationItemSelectedListener(this);

        rastreamentoSwitch = (Switch) findViewById(R.id.rastreamentoSwitch);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            rastreamentoSwitch.setChecked(extras.getBoolean("isRequesting"));
            mRequestingLocationUpdates = true;
        } else{
            mRequestingLocationUpdates = false;
        }

        if(RastreioService.isRunning){
            rastreamentoSwitch.setChecked(true);
        }

        updateValuesFromBundle(savedInstanceState);
    }

    @Override
    public void onResume() {
        Log.i(TAG, "Resume");
        super.onResume();

        rastreamentoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                startLocationUpdates();
            } else{
                updateVeiculo();
                stopLocationUpdates();
            }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
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
            Intent i = new Intent(getApplicationContext(), Manutencao.class);
            startActivity(i);
            finish();
        }  else if (id == R.id.nav_configuracoes) {
            Intent i = new Intent(getApplicationContext(), Configuracoes.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void updateVeiculo(){
        VeiculoDAO veiculoDAO = new VeiculoDAO(this);

        List<VeiculoVO> veiculoAux = veiculoDAO.findAll();
        VeiculoVO veiculo = veiculoAux.get(0);

        //Conversão de metros para quilômetros
        veiculo.setQuilometragem((float) (veiculo.getQuilometragem() + (RastreioService.totalDistance * 0.001)));
        veiculoDAO.update(veiculo);
    }

    protected void checkComponents(){
        ItemDAO itemDAO = new ItemDAO(this);
        List<ItemVO> itens = itemDAO.findAll();

        VeiculoDAO veiculoDAO = new VeiculoDAO(this);
        VeiculoVO veiculo = veiculoDAO.findAll().get(0);

        for (ItemVO item : itens) {
            Log.i(TAG, "Item: " + item.getName());
            Log.i(TAG, "Frequencia: " + String.valueOf(item.getFrequencia()));
            Log.i(TAG, "Ultima Troca: " + String.valueOf(item.getUltima_troca()));
            Log.i(TAG, "Total: " + String.valueOf(item.getFrequencia() + item.getUltima_troca()));
            Log.i(TAG, "Quilometragem: " + String.valueOf(veiculo.getQuilometragem()));
            Log.i(TAG, "Final Distance: " + String.valueOf(RastreioService.totalDistance));
            if(veiculo.getQuilometragem() >= item.getFrequencia() + item.getUltima_troca()){
                Intent i = new Intent(this, AlertaComponentes.class);
                startActivity(i);
                finish();
                break;
            }
        }
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(TAG, "Updating values from bundle");
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains("isRequesting")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("isRequesting");
                if(mRequestingLocationUpdates){
                    rastreamentoSwitch.setChecked(true);
                }
            }
        }
    }

    protected void startLocationUpdates() {
        Intent s = new Intent(this, RastreioService.class);
        startService(s);
        mRequestingLocationUpdates = true;
    }

    protected void stopLocationUpdates() {
        Intent s = new Intent(this, RastreioService.class);
        stopService(s);
        if(RastreioService.isRunning){
            checkComponents();
        }
        mRequestingLocationUpdates = false;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("isRequesting", mRequestingLocationUpdates);
        super.onSaveInstanceState(savedInstanceState);
    }
}