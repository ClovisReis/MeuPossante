package lucas.com.meupossante;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import lucas.com.meupossante.DAO.VeiculoDAO;
import lucas.com.meupossante.VO.VeiculoVO;

public class Configuracoes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView configuracoesKMEditText;
    private VeiculoVO veiculoAux;
    private VeiculoDAO veiculoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
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

        configuracoesKMEditText = (TextView) findViewById(R.id.configuracoesModificarKMEditText);

        veiculoDAO = new VeiculoDAO(this);
        List<VeiculoVO> veiculo = veiculoDAO.findAll();
        veiculoAux = veiculo.get(0);
        configuracoesKMEditText.setText(veiculoAux.getQuilometragem().toString());
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
        } else if(id == R.id.nav_configuracoes){
            Intent i = new Intent(getApplicationContext(), Rastreio.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    public void onClick(View view) {
        if (!configuracoesKMEditText.getText().toString().equals("")) {
            veiculoAux.setQuilometragem(Float.parseFloat(configuracoesKMEditText.getText().toString()));
            veiculoDAO.update(veiculoAux);

            //Fecha o teclado
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            Toast.makeText(this, "Quilometragem Atualizada!!", Toast.LENGTH_LONG).show();
        }
    }
}
