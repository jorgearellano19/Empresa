package mx.edu.ittepic.tpdm_u3_practica1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;

public class VistaTablas extends AppCompatActivity {
    String window;
    ConexionBD conexion;
    FloatingActionButton fab;
    ListView list;
    ArrayList<DetalleProducto> productos = new ArrayList<DetalleProducto>();
    Button button;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_tablas);


        list = (ListView)findViewById(R.id.list);
        registerForContextMenu(list);
        conexion = new ConexionBD(this,"empresa",null,1);
        Bundle b = getIntent().getExtras();
        fab = (FloatingActionButton)findViewById(R.id.fab);
        button = (Button)findViewById(R.id.button5);
        if(b!=null){
            window = b.getString("window");
            if(!window.equals("compras"))
                button.setEnabled(false);
           switch (window){
               case "clientes":
                   listClients();
                   break;
               case "productos":
                   listProducts();
                   //Llenar con productos
                   break;
               case "compras":
                   listBuyList();
                   //Llenar con compras por hacer
                   break;
               case "ver":
                   flag = true;
                   listFacturas();
                   //Llenar con facturas hechas.
                   break;
           }
        }

        fab.setOnClickListener(new View.OnClickListener(){

            Intent i;
            @Override
            public void onClick(View v) {
                switch (window){
                    case "clientes":
                        showAlertClient();
                        break;
                    case "productos":
                         showAlertProduct();
                        break;
                    case "compras":
                        showNewProduct();
                        break;
                    case "ver":
                        Intent i = new Intent(VistaTablas.this,VistaCompras.class);
                        startActivity(i);
                        break;
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmaCompra();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(flag){
                    try {
                        SQLiteDatabase db = conexion.getReadableDatabase();
                        String sql = "SELECT P.PRO_NOMBRE,DP.CANTIDAD FROM DETALLE DP " +
                                "INNER JOIN PRODUCTO P ON (P.PRO_ID = DP.PRO_ID) " +
                                "INNER JOIN FACTURA F ON (F.FAC_ID = "+position+1+")";
                        Cursor c = db.rawQuery(sql,null);
                        ArrayList<DetalleProducto> detalles = new ArrayList<DetalleProducto>();
                        while (c.moveToNext()){
                            DetalleProducto temp = new DetalleProducto();
                            temp.producto = c.getString(0);
                            temp.cantidad = c.getInt(1);
                            detalles.add(temp);
                        }
                        Intent i = new Intent(VistaTablas.this,VerFactura.class);
                        Bundle b = new Bundle();
                        b.putSerializable("lista",detalles);
                        i.putExtras(b);
                        startActivity(i);
                    }catch (SQLiteException e){
                        Toast.makeText(VistaTablas.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }

    private void listFacturas() {
        try {
            SQLiteDatabase db = conexion.getReadableDatabase();
            String SQL = "SELECT C.CLI_NOMBRE, F.FAC_ID, F.FAC_FECHA FROM FACTURA F INNER JOIN CLIENTE C ON (C.CLI_ID = F.CLI_ID)";
            Cursor c = db.rawQuery(SQL,null);
            ArrayList<Factura> facturas = new ArrayList<Factura>();
            while(c.moveToNext()){
                Factura temp = new Factura(c.getString(0),c.getInt(1)+"",c.getString(2));
                facturas.add(temp);
            }
            if(facturas.size()==0)
                return;
            FacturaAdapter adapter = new FacturaAdapter(this,facturas);
            list.setAdapter(adapter);

        }catch (SQLiteException e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void confirmaCompra() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Spinner clients = new Spinner(this);
        Cursor c = fillClients();
        ArrayList<String> array = new ArrayList<String>();
        while (c.moveToNext())
            array.add(c.getString(0));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,array);
        clients.setAdapter(adapter);
        clients.setPrompt("Seleccione un cliente a vender");
        alert.setTitle("Cliente")
                .setView(clients)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //factura e inserta detalles.
                        insertaVenta(clients.getSelectedItem().toString());
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                listBuyList();
            }
        }).show();

    }

    private void insertaVenta(String nombreC) {
        try{

            SQLiteDatabase db = conexion.getWritableDatabase();
            String id_c = "SELECT CLI_ID from CLIENTE WHERE CLI_NOMBRE = '"+nombreC+"'";
            Cursor c = db.rawQuery(id_c,null);
            String ID="";
            String pro_id="";
            if(c.moveToFirst())
                 ID = c.getString(0);
            else
                ID = "0";
            //Insertar factura
            String factura = "INSERT INTO Factura VALUES (NULL,<CLI_ID>,'<FAC_FECHA>')";
            factura = factura.replace("<CLI_ID>",ID);
            Date d = new Date();
            Log.e("Fecha 0",d.toString());
            CharSequence s = DateFormat.format("yyyy-MM-dd HH:mm:ss",d.getTime());
            Log.e("Fecha",s.toString());
            factura = factura.replace("<FAC_FECHA>",s.toString());
            db.execSQL(factura);

            String idf = "SELECT FAC_ID FROM Factura ORDER BY FAC_ID DESC LIMIT 1";
            c = db.rawQuery(idf,null);
            if(c.moveToFirst())
                idf = c.getString(0);
            else
                idf = "";

            //Insertar detalles
            for(int i=0;i<productos.size();i++){
                String p = "SELECT PRO_ID FROM Producto WHERE PRO_NOMBRE = '"+productos.get(i).producto+"'";
                c = db.rawQuery(p,null);
                if(c.moveToFirst())
                    pro_id = c.getString(0);
                else
                    pro_id="";
                String detalle = "INSERT INTO DETALLE VALUES(NULL,<FAC_ID>,<PRO_ID>,<CANTIDAD>)";
                detalle = detalle.replace("<FAC_ID>",idf);
                detalle = detalle.replace("<PRO_ID>",pro_id);
                detalle = detalle.replace("<CANTIDAD>",productos.get(i).cantidad+"");
                db.execSQL(detalle);
            }

            Toast.makeText(this,"Compra registrada satisfactoriamente",Toast.LENGTH_LONG).show();
            productos = new ArrayList<DetalleProducto>();
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();

        }
    }

    private Cursor fillClients() {
        try{
            SQLiteDatabase db = conexion.getReadableDatabase();
            String SQL = "SELECT CLI_NOMBRE FROM Cliente";
            return db.rawQuery(SQL,null);
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private void showNewProduct() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Añadir producto");
        final Spinner product = new Spinner(this);
        final EditText cantidad = new EditText(this);
        cantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
        ArrayList<String> array = new ArrayList<String>();
        Cursor c = fillProducts();
        while (c.moveToNext())
            array.add(c.getString(0));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,array);
        product.setAdapter(adapter);
        product.setPrompt("Seleccione un producto");
        cantidad.setHint("Cantidad");

        LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(product);
        ll.addView(cantidad);
        alertDialog.setView(ll);

        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DetalleProducto temp = new DetalleProducto();
                temp.producto = product.getSelectedItem().toString();
                temp.cantidad = Integer.parseInt(cantidad.getText().toString());
                productos.add(temp);
                listBuyList();
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    private Cursor fillProducts() {
        try{
            SQLiteDatabase db = conexion.getReadableDatabase();
            String SQL = "SELECT PRO_NOMBRE FROM PRODUCTO";
            return db.rawQuery(SQL,null);
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private void listBuyList() {
        if(productos.size()==0){
            Toast.makeText(this,"Añade productos en el botón",Toast.LENGTH_LONG).show();
            return;
        }
        DetalleAdapter adapter = new DetalleAdapter(this,productos,true);
        list.setAdapter(adapter);
    }

    private void showAlertProduct() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Insertar producto");
        final EditText nombre = new EditText(this);
        final EditText stack = new EditText(this);
        final EditText precio = new EditText(this);
        nombre.setInputType(InputType.TYPE_CLASS_TEXT);
        stack.setInputType(InputType.TYPE_CLASS_NUMBER);
        precio.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

        nombre.setHint("Nombre del producto");
        stack.setHint("Cantidad en stock");
        precio.setHint("Precio al público");


        LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(nombre);
        ll.addView(stack);
        ll.addView(precio);
        alertDialog.setView(ll);

        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertaProducto(nombre.getText().toString(),precio.getText().toString(),stack.getText().toString());
                dialog.dismiss();
                listProducts();
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();

    }

    private void insertaProducto(String s, String s1, String s2) {
        try {
            SQLiteDatabase db = conexion.getWritableDatabase();
            String SQL = "INSERT INTO PRODUCTO VALUES (NULL,'<NOMBRE>',<PRECIO>,<STACK>)";
            SQL = SQL.replace("<NOMBRE>",s);
            SQL = SQL.replace("<PRECIO>",s1);
            SQL = SQL.replace("<STACK>",s2);
            db.execSQL(SQL);
            db.close();
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void listProducts() {
        try {
            SQLiteDatabase db = conexion.getReadableDatabase();
            String SQL = "SELECT PRO_ID,PRO_NOMBRE, PRO_PRECIO,PRO_STACK FROM PRODUCTO";
            Cursor cursor = db.rawQuery(SQL,null);
            ArrayList<Producto> productos = new ArrayList<Producto>();
            while (cursor.moveToNext()){
                Producto temp = new Producto(cursor.getInt(0),cursor.getString(1),cursor.getFloat(2),cursor.getInt(3));
                productos.add(temp);
            }
            if(productos.size()==0)
                return;
            ProductoAdapter adapter = new ProductoAdapter(this,productos);
            list.setAdapter(adapter);

        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    private void showAlertClient() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Insertar cliente");
        final EditText nombre = new EditText(this);
        final EditText direccion = new EditText(this);
        nombre.setInputType(InputType.TYPE_CLASS_TEXT);
        direccion.setInputType(InputType.TYPE_CLASS_TEXT);
        nombre.setHint("Nombre del cliente");
        direccion.setHint("Dirección");


        LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(nombre);
        ll.addView(direccion);
        alertDialog.setView(ll);

        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertaCliente(nombre.getText().toString(),direccion.getText().toString());
                dialog.dismiss();
                listClients();
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    private void insertaCliente(String s, String s1) {
        try {
            SQLiteDatabase db = conexion.getWritableDatabase();
            String SQL = "INSERT INTO CLIENTE VALUES (NULL,'<NOMBRE>','<DIRECCION>')";
            SQL = SQL.replace("<NOMBRE>",s);
            SQL = SQL.replace("<DIRECCION>",s1);
            db.execSQL(SQL);
            db.close();
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void listClients() {
        try {

            SQLiteDatabase db = conexion.getReadableDatabase();
            String SQL = "SELECT CLI_ID, CLI_NOMBRE,CLI_DIRECCION FROM CLIENTE";
            Cursor cursor = db.rawQuery(SQL,null);
            ArrayList<Cliente> clientes = new ArrayList<Cliente>();
            while (cursor.moveToNext()){
                Cliente temp = new Cliente(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
                clientes.add(temp);
            }
            if(clientes.size()==0)
                return;
            ClienteAdapter adapter = new ClienteAdapter(this,clientes);
            list.setAdapter(adapter);

        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    //Menu
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.list) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.edit_menu, menu);
        }
    }

    public boolean onContextItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.menu_editar:
                checkWindowEditar();
                break;
            case R.id.menu_eliminar:
                checkWindowEliminar();
                break;
            default:
                Toast.makeText(this,"ENTRAAAAA",Toast.LENGTH_LONG).show();
                break;

        }
        return true;
    }

    private void checkWindowEliminar() {
        switch (window){
            
        }
    }

    private void checkWindowEditar(){

    }

}
