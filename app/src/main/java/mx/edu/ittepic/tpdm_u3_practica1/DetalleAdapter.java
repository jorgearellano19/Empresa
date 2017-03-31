package mx.edu.ittepic.tpdm_u3_practica1;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jorgearellano on 29/03/17.
 */

public class DetalleAdapter extends ArrayAdapter {
    private Context context;
    private List datos;
    private boolean compra;
    public DetalleAdapter( Context context, List objects,boolean compra) {
        super(context, R.layout.listview_detalle, objects);
        this.context = context;
        this.datos=objects;
        this.compra = compra;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(compra) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View item = inflater.inflate(R.layout.listview_detalle, null);

            //Sacar todos los datos
            TextView tv1 = (TextView) item.findViewById(R.id.listview_item_producto_nombre);
            tv1.setText(((DetalleProducto) datos.get(position)).producto);

            TextView tv2 = (TextView) item.findViewById(R.id.listview_item_producto_cantidad);
            tv2.setText(((DetalleProducto) datos.get(position)).cantidad + "");

            return item;
        }
        else {
            LayoutInflater inflater = LayoutInflater.from(context);
            View item = inflater.inflate(R.layout.listview_ver_factura, null);

            //Sacar todos los datos
            TextView tv1 = (TextView) item.findViewById(R.id.listview_item_verFactura_nombre);
            tv1.setText("Nombre del producto: "+((DetalleProducto) datos.get(position)).producto);

            TextView tv2 = (TextView) item.findViewById(R.id.listview_item_verFactura_cantidad);
            tv2.setText("Cantidad a comprar: "+((DetalleProducto) datos.get(position)).cantidad + "");

            return item;
        }

    }

}
