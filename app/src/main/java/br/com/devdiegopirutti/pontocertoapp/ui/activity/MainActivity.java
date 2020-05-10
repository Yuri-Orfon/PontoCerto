package br.com.devdiegopirutti.pontocertoapp.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import br.com.devdiegopirutti.pontocertoapp.R;
import br.com.devdiegopirutti.pontocertoapp.ViewModel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private TextView userName, userEmpresa;
    private FirebaseAuth firebaseAuth;
    private Button sair, historico, pontoEntrada, pontoSaida;
    private Intent intent;
    private AlertDialog alerta;
    private RecyclerView recyclerView;
    private MainActivityViewModel viewModel = new MainActivityViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeButtons();
        observerResult();
        pegarInformações();

    }


    public void irParaHistorico() {
        startActivity(intent);
    }


    public void initializeViews() {
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        userEmpresa = findViewById(R.id.empresa);
        userName = findViewById(R.id.txt_nome);
        historico = findViewById(R.id.hist_btn);
        sair = findViewById(R.id.sair_btn);
        pontoSaida = findViewById(R.id.btn_saida);
        pontoEntrada = findViewById(R.id.btn_entrada);
        recyclerView = findViewById(R.id.recyclerViewDataDay);

        viewModel.info
                .observe(this,
                        infoConta -> {
                            userEmpresa.setText(infoConta.getEmpresa());
                            userName.setText(infoConta.getName());
                        }
                );

    }

    public void initializeButtons() {

        intent = new Intent(this, HistoricoActivity.class);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        sair.setOnClickListener(v -> firebaseAuth.signOut());
        historico.setOnClickListener(v -> irParaHistorico());
        pontoEntrada.setOnClickListener(v -> builderConfirmacao(2, "Entrada"));
        pontoSaida.setOnClickListener(v -> builderConfirmacao(2, "Saida"));
    }

    private void builderConfirmacao(int id, String tipo) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (id == 1) {
            builder.setTitle("Bem vindo ao Ponto Certo");
            builder.setMessage(getString(R.string.TextoBASE));
            builder.setNeutralButton("Okay, entendi.",
                    (arg0, arg1) -> {
                    });
            alerta = builder.create();
            alerta.show();
        } else {
            builder.setTitle(getString(R.string.perguntaPonto, tipo));
            builder.setPositiveButton("Sim", ((dialog, which) -> marcarPonto(tipo, tipo.equals("Entrada"))));
            builder.setNegativeButton("Não", ((dialog, which) -> closeOptionsMenu()));
            alerta = builder.create();
            alerta.show();
        }
    }

    private void marcarPonto(String tipo, Boolean ponto) {
        viewModel.marcarPonto(tipo, ponto);
    }

    private void generateToastMessage(String string) {
        Toast.makeText(MainActivity.this, string,
                Toast.LENGTH_LONG).show();
    }

    private void observerResult() {
        viewModel.events.observe(this, events -> {
            switch (events) {
                case GRAVARPONTOENTRADA:
                    generateToastMessage(getString(R.string.pontoEntrada));
                    break;
                case GRAVARPONTOSAÍDA:
                    generateToastMessage(getString(R.string.pontoSaida));
                    break;
                default:
            }
        });
    }

    void pegarInformações() {
        viewModel.pegarInformaçõesDoUsuario();

    }
}



