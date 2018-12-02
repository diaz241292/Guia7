package com.example.guia.ejsqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guia.ejsqlite.database.AdaptadorCategoria;
import com.example.guia.ejsqlite.database.AdaptadorProductos;
import com.example.guia.ejsqlite.database.Categoria;
import com.example.guia.ejsqlite.database.DB;
import com.example.guia.ejsqlite.database.Producto;

import java.util.ArrayList;

public class LstProductos extends AppCompatActivity {

    private DB db;
    private AdaptadorProductos adaptadorProducto;
    private ListView listView;
    private TextView lblId_prod,lblCategoria;
    private EditText txtNombre_prod,txtDescripcion;
    private Button btnGuardar,btnEliminar;
    //lista de datos (categoria)
    private ArrayList<Producto> lstProductos;
    //sirve para manejar la eliminacion
    private Producto producto_temp=null;

    String IDCATEGORIA;
    String CATEGORIA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_productos);
        //inicializando los controles
        lblId_prod               = (TextView) findViewById(R.id.lblId_prod);
        txtNombre_prod          = (EditText) findViewById(R.id.txtProducto);
        txtDescripcion         = (EditText) findViewById(R.id.txtDescripcion);
        btnGuardar              = (Button)   findViewById(R.id.btnGuardar);
        btnEliminar             = (Button)   findViewById(R.id.btnEliminar);
        listView                = (ListView) findViewById(R.id.lstProductos);
        lblCategoria            =   findViewById(R.id.lblCategoria);
         IDCATEGORIA = getIntent().getStringExtra("IDCATEGORIA");
         CATEGORIA = getIntent().getStringExtra("NOMBRECATEGORIA");
        lblCategoria.setText(CATEGORIA);
        //inicializando lista y db
        db                      = new DB(this);
        lstProductos            = db.getArrayProducto(
                db.getCursorProducto(IDCATEGORIA)
        );
        if(lstProductos==null)//si no hay datos
            lstProductos = new ArrayList<>();
        adaptadorProducto      = new AdaptadorProductos(this,lstProductos);
        listView.setAdapter(adaptadorProducto);
        //listeners
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seleccionar(lstProductos.get(position));
            }
        });
        //limpiando
        limpiar();
    }

    private void guardar(){
        Producto producto = new Producto(lblId_prod.getText().toString(),txtNombre_prod.getText().toString(),txtDescripcion.getText().toString(),IDCATEGORIA);
        producto_temp=null;
        if(db.guardar_O_ActualizarProducto(producto)){
            Toast.makeText(this,"Se guardo producto", Toast.LENGTH_SHORT).show();
            //TODO limpiar los que existen y agregar los nuevos
            lstProductos.clear();
            lstProductos.addAll(db.getArrayProducto(
                    db.getCursorProducto(IDCATEGORIA)
            ));

            adaptadorProducto.notifyDataSetChanged();
            limpiar();
        }else{
            Toast.makeText(this,"Ocurrio un error al guardar",Toast.LENGTH_SHORT).show();
        }
    }
    private void eliminar(){
        if(producto_temp!=null){
            db.borrarCategoria(producto_temp.getId_categoria());
            lstProductos.remove(producto_temp);
            adaptadorProducto.notifyDataSetChanged();
            producto_temp=null;
            Toast.makeText(this,"Se elimino categoria",Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }
    private void seleccionar(Producto producto){
        producto_temp = producto;
        lblId_prod.setText(producto_temp.getId_categoria());
        txtNombre_prod.setText(producto_temp.getNombre());
    }
    private void limpiar(){
        lblId_prod.setText(null);
        txtNombre_prod.setText(null);
    }

}

