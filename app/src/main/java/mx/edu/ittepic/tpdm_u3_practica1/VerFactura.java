package mx.edu.ittepic.tpdm_u3_practica1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class VerFactura extends AppCompatActivity {
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_factura);
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        list = (ListView)findViewById(R.id.listFact);

        if(b!=null){
            ArrayList<DetalleProducto> detalles = (ArrayList<DetalleProducto>) b.get("lista");
            DetalleAdapter adapter = new DetalleAdapter(this,detalles,false);
            list.setAdapter(adapter);

        }
    }
}
