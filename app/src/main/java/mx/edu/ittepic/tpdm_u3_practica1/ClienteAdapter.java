package mx.edu.ittepic.tpdm_u3_practica1;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorgearellano on 29/03/17.
 */

public class ClienteAdapter extends ArrayAdapter{
    private Context context;
    private List datos;

    public ClienteAdapter( Context context, List objects) {
        super(context,R.layout.listview_cliente ,objects);
        this.context = context;
        this.datos = objects;
    }

    public View getView(int position,View convertView,ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.listview_cliente,null);

        //Sacar todos los datos
        TextView tv1 = (TextView)item.findViewById(R.id.listview_cliente_nombre);
        tv1.setText("Nombre del cliente: "+((Cliente)datos.get(position)).cli_nombre);

        TextView tv2 = (TextView)item.findViewById(R.id.listview_cliente_direccion);
        tv2.setText("Dirección: "+((Cliente)datos.get(position)).cli_direccion);

        return item;

    }


}
