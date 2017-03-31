package mx.edu.ittepic.tpdm_u3_practica1;

import java.io.Serializable;

/**
 * Created by jorgearellano on 29/03/17.
 */

public class Cliente implements Serializable{
    int cli_id;
    String cli_nombre;
    String cli_direccion;

    public Cliente(int cli_id, String cli_nombre, String cli_direccion) {
        this.cli_id = cli_id;
        this.cli_nombre = cli_nombre;
        this.cli_direccion = cli_direccion;
    }
}
