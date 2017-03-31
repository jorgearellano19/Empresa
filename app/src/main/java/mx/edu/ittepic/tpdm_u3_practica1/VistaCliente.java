package mx.edu.ittepic.tpdm_u3_practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VistaCliente extends AppCompatActivity {
    EditText nombre,direccion;
    Button guardar,cancelar;
    ConexionBD conexion;
    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_cliente);
        nombre = (EditText)findViewById(R.id.editText);
        direccion = (EditText)findViewById(R.id.editText2);
        guardar = (Button)findViewById(R.id.button);
        cancelar = (Button)findViewById(R.id.button2);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            nombre.setText(b.getString("NOMBRE"));
            direccion.setText(b.getString("DIRECCION"));
            id = b.getInt("ID");
        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizaCliente();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();

            }
        });
    }

    private void cancelar() {
        Intent i = new Intent(this,VistaTablas.class);
        Bundle b = new Bundle();
        b.putString("window","clientes");
        i.putExtras(b);
        startActivity(i);
        finish();
    }

    private void actualizaCliente() {
        Intent i = new Intent(this,VistaTablas.class);
        Bundle b = new Bundle();
        Cliente c = new Cliente(id,nombre.getText().toString(),direccion.getText().toString());
        b.putString("window","clientes");
        b.putSerializable("c",c);
        i.putExtras(b);
        startActivity(i);
        finish();
    }

}
