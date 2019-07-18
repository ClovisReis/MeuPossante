package lucas.com.meupossante;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import lucas.com.meupossante.DAO.ItemDAO;
import lucas.com.meupossante.VO.ItemVO;
import lucas.com.meupossante.DAO.VeiculoDAO;
import lucas.com.meupossante.VO.VeiculoVO;

public class ManutencaoItem extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView manutencaoItemTitulo;
    private TextView manutencaoItemDescricao;
    private ImageView manutencaoItemImagem;
    private TextView manutencaoUltimaTrocaTextView;
    private TextView observacao;
    private EditText manutencaoEditText;
    private ImageButton oabaixo;
    private ImageButton oacima;
    private TextView txtage;
    private TextView servico;
    private EditText servicoedit;
    private Button salvaroa;
    RelativeLayout.LayoutParams params;
    public static final String ARQUIVOREVISAO = "revisaogeral.txt";
    private ItemDAO itemDAO;
    private ItemVO item;
    private VeiculoDAO veiculoDAO;
    private File r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manutencao_item);

        manutencaoItemImagem = (ImageView) findViewById(R.id.manutencaoItemImagem);
        manutencaoItemTitulo = (TextView) findViewById(R.id.manutencaoItemTitulo);
        manutencaoItemDescricao = (TextView) findViewById(R.id.manutencaoItemDescricao);
        manutencaoUltimaTrocaTextView = (TextView) findViewById(R.id.manutencaoItemQuilometragemAtualTextView);
        manutencaoEditText = (EditText) findViewById(R.id.manutencaoItemEditText);
        manutencaoEditText.addTextChangedListener(new NumberTextWatcherForThousand(manutencaoEditText));
        oabaixo = (ImageButton) findViewById(R.id.oabaixoButton);
        oacima = (ImageButton) findViewById(R.id.oacimaButton);
        txtage = (TextView) findViewById(R.id.txtage);
        servico = (TextView) findViewById(R.id.servico);
        observacao = (TextView) findViewById(R.id.observacao);
        servicoedit = (EditText) findViewById(R.id.servicoedit);
        servicoedit.addTextChangedListener(new NumberTextWatcherForThousand(servicoedit));
        salvaroa = (Button) findViewById(R.id.salvarOA);

        //Alteracao de posição dos componentes do XML below
         params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.BELOW,R.id.textopcoesavancadas);
        observacao.setLayoutParams(params);
//Alteracao de posição dos componentes do XML Margem

        ViewGroup.MarginLayoutParams margem = (ViewGroup.MarginLayoutParams)observacao.getLayoutParams();
        margem.topMargin = 34;
        observacao.setLayoutParams(margem);

//Fim das alterações das posições dos componentes
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu1 = navigationView.getMenu();
        MenuItem veiculoTitle = menu1.findItem(R.id.name_veiculo);
        veiculoTitle.setTitle("Meu Veículo");
        navigationView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            itemDAO = new ItemDAO(this);
            item = new ItemVO();
            item.setName(extras.getString("item"));
            item = itemDAO.findByName(item);

            Float frequenciaItem = item.getUltima_troca() + item.getFrequencia();

            manutencaoUltimaTextView.setText(frequenciaItem.toString() + "KM");

            veiculoDAO = new VeiculoDAO(this);
            List<VeiculoVO> veiculo = veiculoDAO.findAll();

            if(veiculo.get(0).getQuilometragem() >= frequenciaItem){
                manutencaoUltimaTextView.setTextColor(Color.RED);
                manutencaoItemAlerta.setText(getResources().getString(R.string.manutencao_item_alerta));
                manutencaoItemAlertaButton.setVisibility(View.VISIBLE);
            }

            manutencaoKMTextView.setText(veiculo.get(0).getQuilometragem().toString() + "KM");

*/
        Bundle extras = getIntent().getExtras();
            switch (extras.getString("item")){
                case "Água":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_agua);
                    manutencaoItemTitulo.setText(R.string.agua);
                    manutencaoItemDescricao.setText(R.string.descricao_agua);
                    manutencaoUltimaTrocaTextView.setText(R.string.legenda_agua);
                    mudancaEdit();
                    break;
                case "Alinhamento":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_alinhamento);
                    manutencaoItemTitulo.setText(R.string.alinhamento);
                    manutencaoItemDescricao.setText(R.string.descricao_alinhamento);
                    manutencaoUltimaTrocaTextView.setText(R.string.legenda_alinhamento);
                    mudancaEdit();
                    break;
                case "Arrefecimento":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_arrefecimento);
                    manutencaoItemTitulo.setText(R.string.arrefecimento);
                    manutencaoItemDescricao.setText(R.string.descricao_arrefecimento);
                    manutencaoUltimaTrocaTextView.setText(R.string.legenda_arrefecimento);
                    mudancaEdit();
                    break;
                case "Balanceamento":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_balanceamento);
                    manutencaoItemTitulo.setText(R.string.balanceamento);
                    manutencaoItemDescricao.setText(R.string.descricao_balanceamento);
                    manutencaoUltimaTrocaTextView.setText(R.string.legenda_balanceamento);
                    mudancaEdit();
                    break;
                case "Bateria":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_bateria);
                    manutencaoItemTitulo.setText(R.string.bateria);
                    manutencaoItemDescricao.setText(R.string.descricao_bateria);
                    manutencaoUltimaTrocaTextView.setText(R.string.legenda_bateria);
                    mudancaEdit();
                    break;
                case "Cambagem":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_cambagem);
                    manutencaoItemTitulo.setText(R.string.cambagem);
                    manutencaoItemDescricao.setText(R.string.descricao_cambagem);
                    manutencaoUltimaTrocaTextView.setText(R.string.legenda_cambagem);
                    mudancaEdit();
                    break;
                case "Correia":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_correia);
                    manutencaoItemTitulo.setText(R.string.correia);
                    manutencaoItemDescricao.setText(R.string.descricao_correia);
                    manutencaoUltimaTrocaTextView.setText(R.string.legenda_correiadentada);
                    mudancaEdit();
                    break;
                case "Embreagem":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_embreagem);
                    manutencaoItemTitulo.setText(R.string.embreagem);
                    manutencaoItemDescricao.setText(R.string.descricao_embreagem);
                    manutencaoUltimaTrocaTextView.setText(R.string.legenda_embreagem);
                    mudancaEdit();
                    break;
                case "Filtro de Ar":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_filtro_de_ar);
                    manutencaoItemTitulo.setText(R.string.filtro_de_ar);
                    manutencaoItemDescricao.setText(R.string.descricao_filtro_de_ar);
                    manutencaoUltimaTrocaTextView.setText(R.string.legenda_filtroar);
                    mudancaEdit();
                    break;
                case "Filtro de Combustível":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_filtro_de_combustivel);
                    manutencaoItemTitulo.setText(R.string.filtro_de_combustivel);
                    manutencaoItemDescricao.setText(R.string.descricao_filtro_de_combustivel);
                    manutencaoUltimaTrocaTextView.setText(R.string.legenda_filtrocombustivel);
                    mudancaEdit();
                    break;
                case "Freios":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_freios);
                    manutencaoItemTitulo.setText(R.string.freios);
                    manutencaoItemDescricao.setText(R.string.descricao_freios);
                    manutencaoUltimaTrocaTextView.setText(R.string.legenda_freios);
                    mudancaEdit();
                    break;
                case "Óleo":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_oleo);
                    manutencaoItemTitulo.setText(R.string.oleo);
                    manutencaoItemDescricao.setText(R.string.descricao_oleo);
                    manutencaoUltimaTrocaTextView.setText(R.string.legenda_oleo);
                    mudancaEdit();
                    break;
                case "Pneus":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_pneus);
                    manutencaoItemTitulo.setText(R.string.pneus);
                    manutencaoItemDescricao.setText(R.string.descricao_pneus);
                    mudancaEdit();
                    break;
                case "Revisão":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_revisao);
                    manutencaoItemTitulo.setText(R.string.revisao);
                    manutencaoItemDescricao.setText(R.string.descricao_revisao);
                    manutencaoUltimaTrocaTextView.setText(R.string.legenda_revisaogeral);
                    mudancaEdit();
                    break;
                case "Rodízio de Pneus":
                    manutencaoItemImagem.setImageResource(R.drawable.ic_rodizio);
                    manutencaoItemTitulo.setText(R.string.rodizio_de_pneus);
                    manutencaoItemDescricao.setText(R.string.descricao_rodizio_de_pneus);
                    manutencaoUltimaTrocaTextView.setText(R.string.legenda_rodiziopneus);
                    mudancaEdit();
                    break;
            }
        }


        public void itemClick(View view){
            if(view.getId() == R.id.salvarOpcoesAvancadas){
                Bundle extras = getIntent().getExtras();
                switch (extras.getString("item")){
                    case "Revisão":
                        try{
                            FileOutputStream out = openFileOutput(ARQUIVOREVISAO, MODE_PRIVATE);
                            out.write(manutencaoEditText.getText().toString().getBytes());
                            out.close();
                        }
                        catch(FileNotFoundException e){
                            Log.e("Arquivo nao encontrado",e.getMessage(), e);
                        }
                        catch (IOException e){
                            Log.e("Erro Arquivo Exception", e.getMessage(), e);
                        }
                }
                finish();
            }
            else if(view.getId() == R.id.oabaixoButton){

                //Alteracao de posição dos componentes do XML below
                params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                params.addRule(RelativeLayout.BELOW,R.id.salvarOA);
                observacao.setLayoutParams(params);
//Alteracao de posição dos componentes do XML Margem

                ViewGroup.MarginLayoutParams margem = (ViewGroup.MarginLayoutParams)observacao.getLayoutParams();
                margem.topMargin = 34;
                observacao.setLayoutParams(margem);

//Fim das alterações das posições dos componentes

                oabaixo.setVisibility(View.INVISIBLE);
                oacima.setVisibility(View.VISIBLE);
                salvaroa.setVisibility(View.VISIBLE);
                servicoedit.setVisibility(View.VISIBLE);
                txtage.setVisibility(View.VISIBLE);
                servico.setVisibility(View.VISIBLE);

            }
            else if(view.getId() == R.id.oacimaButton){
                oabaixo.setVisibility(View.VISIBLE);
                oacima.setVisibility(View.INVISIBLE);
                salvaroa.setVisibility(View.INVISIBLE);
                servicoedit.setVisibility(View.INVISIBLE);
                txtage.setVisibility(View.INVISIBLE);
                servico.setVisibility(View.INVISIBLE);

                //Alteracao de posição dos componentes do XML below
                params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                params.addRule(RelativeLayout.BELOW,R.id.textopcoesavancadas);
                observacao.setLayoutParams(params);
//Alteracao de posição dos componentes do XML Margem

                ViewGroup.MarginLayoutParams margem = (ViewGroup.MarginLayoutParams)observacao.getLayoutParams();
                margem.topMargin = 34;
                observacao.setLayoutParams(margem);

//Fim das alterações das posições dos componentes
            }
        }

        // Escreve o valor de revisão geral para todos
        public void mudancaEdit(){
            r = getFileStreamPath(ARQUIVOREVISAO);
            try {
                if (r.exists()) {
                    FileInputStream in = openFileInput(ARQUIVOREVISAO);
                    int tamanho = in.available();
                    byte bytes[] = new byte[tamanho];
                    in.read(bytes);
                    String s = new String(bytes);
                    manutencaoEditText.setText("  "+s);
                    manutencaoEditText.setTextColor(getResources().getColor(R.color.colortext));
                }
            }
            catch(FileNotFoundException e){
                Log.e("Item", "Arquivo não encontrado" + e.getMessage(), e);
            }

            catch(IOException e){
                Log.e("Item", e.getMessage(), e);
            }
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


}
