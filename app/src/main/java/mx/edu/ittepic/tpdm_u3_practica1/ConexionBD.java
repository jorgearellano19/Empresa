package mx.edu.ittepic.tpdm_u3_practica1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jorgearellano on 29/03/17.
 */

public class ConexionBD extends SQLiteOpenHelper{


    public ConexionBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CLIENTE (\n" +
                "    CLI_ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    CLI_NOMBRE VARCHAR(200),\n" +
                "    CLI_DIRECCION VARCHAR(100)\n" +
                ")");
        db.execSQL("CREATE TABLE PRODUCTO (\n" +
                "    PRO_ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    PRO_NOMBRE VARCHAR(200),\n" +
                "    PRO_PRECIO DECIMAL,\n" +
                "    PRO_STACK DECIMAL\n" +
                ")");
        db.execSQL("CREATE TABLE FACTURA (\n" +
                "    FAC_ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    CLI_ID INTEGER,\n" +
                "    FAC_FECHA DATE,\n" +
                "    FOREIGN KEY (CLI_ID) REFERENCES CLIENTE(CLI_ID)\n" +
                ")");
        db.execSQL("CREATE TABLE DETALLE (\n" +
                "    DET_ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    FAC_ID INTEGER,\n" +
                "    PRO_ID INTEGER,\n" +
                "    CANTIDAD DECIMAL,\n" +
                "    FOREIGN KEY (FAC_ID) REFERENCES FACTURA(FAC_ID),\n" +
                "    FOREIGN KEY (PRO_ID) REFERENCES CLIENTE(PRO_ID)\n" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
