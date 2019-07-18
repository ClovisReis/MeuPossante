package lucas.com.meupossante;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import lucas.com.meupossante.VO.OficinaVO;

public class ListaOficinas extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_oficinas);
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

        listView = (ListView) findViewById(R.id.listView);


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
        }  else if(id == R.id.nav_configuracoes){
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

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, List<OficinaVO>> {
        @Override
        protected List<OficinaVO> doInBackground(Void... params) {
            try {
                final String url = "http://192.168.1.100/meupossante/webservice.php";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                OficinaVO[] oficina = restTemplate.getForObject(url, OficinaVO[].class);
                return Arrays.asList(oficina);
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<OficinaVO> oficina) {
            listView.setAdapter(new OficinaListAdapter(getApplicationContext(), oficina));
        }

    }

    public class OficinaListAdapter extends BaseAdapter {

        private Context context;
        private List<OficinaVO> oficinaList;

        public OficinaListAdapter(Context context, List<OficinaVO> oficinaList) {

            this.context = context;
            this.oficinaList = oficinaList;
        }

        @Override
        public int getCount() {
            return oficinaList.size();
        }

        @Override
        public Object getItem(int position) {
            return oficinaList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.oficinas_list_view, null);
            }

            OficinaVO oficina = oficinaList.get(position);

            TextView mIdView = (TextView) convertView.findViewById(R.id.idOficinaList);
            TextView mNomeView = (TextView) convertView.findViewById(R.id.nomeOficinaList);
            TextView mEnderecoView = (TextView) convertView.findViewById(R.id.enderecoOficinaList);
            if (oficina.getId() != null) {
                mIdView.setText(String.format("id: %s", oficina.getId().toString()));
            } else {
                mIdView.setText("");
            }
            if (oficina.getNome() != null) {
                mNomeView.setText(String.format("Nome da Oficina: %s", oficina.getNome()));
            } else {
                mNomeView.setText("");
            }
            if (oficina.getEndereco() != null) {
                mEnderecoView.setText(String.format("Endereço: %s", oficina.getEndereco()));
            } else {
                mEnderecoView.setText("");
            }

            return convertView;
        }
    }

}
