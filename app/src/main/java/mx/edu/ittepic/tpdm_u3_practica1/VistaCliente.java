package mx.edu.ittepic.tpdm_u3_practica1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VistaCliente extends AppCompatActivity {
    EditText nombre,direccion;
    Button guardar,cancelar;
    ConexionBD conexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_cliente);

        conexion = new ConexionBD(this,"empresa",null,1);
        nombre = (EditText)findViewById(R.id.editText);
        direccion = (EditText)findViewById(R.id.editText2);
        guardar = (Button)findViewById(R.id.button);
        cancelar = (Button)findViewById(R.id.button2);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertaCliente();
            }
        });
    }

    private void insertaCliente(){

    }
}
