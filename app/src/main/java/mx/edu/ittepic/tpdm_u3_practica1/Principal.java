package mx.edu.ittepic.tpdm_u3_practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Principal extends AppCompatActivity {
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        list = (ListView)findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        openMenu("clientes");
                        break;
                    case 1:
                        openMenu("productos");
                        break;
                    case 2:
                        openMenu("compras");
                        break;
                    case 3:
                        openMenu("ver");
                        break;
                }
            }
        });
    }

    private void openMenu(String s) {
        Intent i = new Intent(this, VistaTablas.class);
        Bundle b = new Bundle();
        b.putString("window",s);
        i.putExtras(b);
        startActivity(i);
    }
}
