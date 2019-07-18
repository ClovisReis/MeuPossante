package lucas.com.meupossante;

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
import android.widget.TextView;

import java.util.List;

import lucas.com.meupossante.DAO.ItemDAO;
import lucas.com.meupossante.DAO.VeiculoDAO;
import lucas.com.meupossante.VO.ItemVO;
import lucas.com.meupossante.VO.VeiculoVO;

public class AlertaComponentes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView itensAlerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta_componentes);
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

        itensAlerta = (TextView) findViewById(R.id.itensAlerta);
        checkComponents();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.primeiroveiculo) {
            Intent i = new Intent(getApplicationContext(), Principal.class);
            startActivity(i);
        } else if(id == R.id.nav_configuracoes){
            Intent i = new Intent(getApplicationContext(), Configuracoes.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
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

    protected void checkComponents(){
        ItemDAO itemDAO = new ItemDAO(this);
        List<ItemVO> itens = itemDAO.findAll();

        VeiculoDAO veiculoDAO = new VeiculoDAO(this);
        VeiculoVO veiculo = veiculoDAO.findAll().get(0);

        for (ItemVO item : itens) {
            if(veiculo.getQuilometragem() >= item.getFrequencia() + item.getUltima_troca()){
                itensAlerta.append("- " + item.getName() + "\n");
            }
        }
    }
}
