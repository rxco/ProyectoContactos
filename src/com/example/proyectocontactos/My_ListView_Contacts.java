package com.example.proyectocontactos;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class My_ListView_Contacts extends ArrayAdapter<String>{
	
	private ArrayList<String> telefonos;
	private ArrayList<String> nombre; 
	private ArrayList<Bitmap> imagenes;
	private SherlockActivity contexto;
	
	
	public My_ListView_Contacts(SherlockActivity contexto, ArrayList<String> telefonos,ArrayList<String> nombre, ArrayList<Bitmap> imagenes) {
		super(contexto, R.layout.personalizado, nombre);
		this.contexto = contexto;
		this.nombre = nombre; 
		this.telefonos = telefonos; 
		this.imagenes = imagenes; 
	}

	static class ViewHolder {
		public ImageView imagen;
		public TextView nombre;
		public TextView telefono;
		public CheckBox checkBox;
	
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View vista = convertView; 
		if(vista==null)
		{
			LayoutInflater inflater = contexto.getLayoutInflater(); 
			vista=inflater.inflate(R.layout.personalizado, null); 
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.imagen=(ImageView)vista.findViewById(R.id.imagen); 
			viewHolder.nombre=(TextView)vista.findViewById(R.id.nombre); 
			viewHolder.telefono=(TextView)vista.findViewById(R.id.telefono);
			viewHolder.checkBox=(CheckBox)vista.findViewById(R.id.checkBox); 
			
			vista.setTag(viewHolder); 
		}
		ViewHolder holder = (ViewHolder)vista.getTag(); 
		if (imagenes.get(position)!=null)
		{
			Toast.makeText(getContext(), "H:  "+imagenes.get(position).getHeight()+"w: "+imagenes.get(position).getWidth(), Toast.LENGTH_SHORT).show(); 
			holder.imagen.setImageBitmap(imagenes.get(position)); 
		}
		holder.nombre.setText("Nombre: "+nombre.get(position));
		holder.telefono.setText("Telefono: "+telefonos.get(position)); 
		return vista;
	}



}
