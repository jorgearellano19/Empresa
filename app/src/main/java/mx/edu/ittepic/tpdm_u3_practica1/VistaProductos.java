package mx.edu.ittepic.tpdm_u3_practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VistaProductos extends AppCompatActivity {
    EditText nombre,stock,precio;
    Button guardar,cancelar;
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_productos);
        nombre = (EditText)findViewById(R.id.editText3);
        precio = (EditText)findViewById(R.id.editText4);
        stock = (EditText)findViewById(R.id.editText5);
        guardar = (Button)findViewById(R.id.button3);
        cancelar = (Button)findViewById(R.id.button2);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            nombre.setText(b.getString("NOMBRE"));
            precio.setText(b.getString("PRECIO"));
            stock.setText(b.getString("STOCK"));
            id = b.getInt("ID");
        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizaProducto();
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
            b.putString("window","productos");
            i.putExtras(b);
            startActivity(i);
            finish();
        }

    private void actualizaProducto() {
        Intent i = new Intent(this,VistaTablas.class);
        Bundle b = new Bundle();
        Producto p = new Producto(id,nombre.getText().toString(),Float.parseFloat(precio.getText().toString()),Integer.parseInt(stock.getText().toString()));
        b.putString("window","productos");
        b.putSerializable("p",p);
        i.putExtras(b);
        startActivity(i);
        finish();
    }

}

