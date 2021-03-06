package br.com.devdiegopirutti.pontocertoapp.Manager;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import br.com.devdiegopirutti.pontocertoapp.Historico.AdapterHistorico;
import br.com.devdiegopirutti.pontocertoapp.Historico.HistViewModel;
import br.com.devdiegopirutti.pontocertoapp.Historico.HistoricoActivity;
import br.com.devdiegopirutti.pontocertoapp.Login.LoginActivity;
import br.com.devdiegopirutti.pontocertoapp.R;

public class HistoryManager extends AppCompatActivity {

    AdapterHistorico adapterHistorico = new AdapterHistorico();
    HistViewModel viewModel = new HistViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_gestor);

        initializeView();
        receiveModelList();
    }

    private void initializeView() {

        RecyclerView recyclerView = findViewById(R.id.recycler_hist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterHistorico);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_historico);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_add:
                    startActivity(new Intent(getApplicationContext()
                            , AddUserActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_colaboradores:
                    startActivity(new Intent(getApplicationContext()
                            , ManagerActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.sign_out:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                    finishAffinity();
                    return true;
            }
            return false;
        });
    }


    public void receiveModelList() {
        viewModel.pontoLiveData.observe(this, resultDay -> {
            adapterHistorico.adicionarItens(resultDay);
        });
        viewModel.getData(getIntent().getStringExtra("userId"));
    }
}
