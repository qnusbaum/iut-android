package android.iut.tp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO 1 - inflate du fichier de layout dans l'activité
        setContentView(R.layout.activity_main);
        // TODO 16 - Récupérer une référence au bouton et au champ de texte
        final Button button = findViewById(R.id.search);
        final EditText name = findViewById(R.id.name);
        // TODO 17 - Ajouter un événement onClick sur le bouton
        // TODO 18 - Dedans, afficher un Toast avec un message contenant le contenu du champ saisi
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, "Recherche de " + name.getText(), Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
