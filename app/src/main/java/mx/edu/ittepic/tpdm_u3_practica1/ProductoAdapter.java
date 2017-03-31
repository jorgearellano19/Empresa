package mx.edu.ittepic.tpdm_u3_practica1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jorgearellano on 29/03/17.
 */

public class ProductoAdapter extends ArrayAdapter {
    private Context context;
    private List datos;

    public ProductoAdapter( Context context, List objects) {
        super(context,R.layout.listview_producto ,objects);
        this.context = context;
        this.datos = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.listview_producto,null);

        //Sacar todos los datos
        TextView tv1 = (TextView)item.findViewById(R.id.listview_item_producto_nombre);
        tv1.setText("Nombre del producto: "+((Producto)datos.get(position)).nombre);

        TextView tv2 = (TextView)item.findViewById(R.id.listview_item_producto_cantidad);
        tv2.setText("Cantidad en stock: "+((Producto)datos.get(position)).stack+"");

        TextView tv3 = (TextView)item.findViewById(R.id.listview_item_producto_precio);
        tv3.setText("Precio unitario del producto: $"+((Producto)datos.get(position)).precio+"");

        return item;

    }


}

