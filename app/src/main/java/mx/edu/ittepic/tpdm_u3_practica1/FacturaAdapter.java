package mx.edu.ittepic.tpdm_u3_practica1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jorgearellano on 30/03/17.
 */

public class FacturaAdapter  extends ArrayAdapter {
    private Context context;
    private List datos;

    public FacturaAdapter( Context context, List objects) {
        super(context,R.layout.listview_compra ,objects);
        this.context = context;
        this.datos = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.listview_compra,null);

        //Sacar todos los datos
        TextView tv1 = (TextView)item.findViewById(R.id.listview_item_compra_nombre);
        tv1.setText(((Factura)datos.get(position)).cliente);

        TextView tv2 = (TextView)item.findViewById(R.id.listview_item_compra_numero);
        tv2.setText(((Factura)datos.get(position)).num_fact);

        TextView tv3 = (TextView)item.findViewById(R.id.listview_item_compra_fecha);
        tv3.setText(((Factura)datos.get(position)).date);

        return item;

    }


}

