package com.example.proyectocontactos;

import java.util.ArrayList;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class MainActivity extends SherlockActivity {

	private ListView lista_contactos; 
	private My_ListView_Contacts contacts; 

	private ArrayList<String> nombre; 
	private ArrayList<Bitmap> imagenes;
	private ArrayList<String> telefonos;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lista_contactos = (ListView)findViewById(R.id.lista); 
		new Hilo_contactos(this).execute(); 
	}
	
	
	public void Crealista ()
	{
		contacts = new My_ListView_Contacts(this, telefonos, nombre, imagenes); 
		lista_contactos.setAdapter(contacts); 
 	}
	
	private class Hilo_contactos extends AsyncTask<Void, Void, Boolean>
	{

		private SherlockActivity contexto;
		
		public Hilo_contactos(SherlockActivity contexto) {
			nombre = new ArrayList<String>();
			telefonos = new ArrayList<String>();
			imagenes = new ArrayList<Bitmap>(); 
			this.contexto = contexto; 
		}
		
		
		
		@Override
		protected Boolean doInBackground(Void... params) {
			Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;

			Cursor contactsCursor = getContentResolver().query(contactsUri,
					null, null, null,
					ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
			
			if (contactsCursor.moveToFirst()) {
				do {
					long contactId = contactsCursor.getLong(contactsCursor
							.getColumnIndex("_ID"));
					
					Uri dataUri = ContactsContract.Data.CONTENT_URI;

					Cursor dataCursor = getContentResolver().query(dataUri,
							null,
							ContactsContract.Data.CONTACT_ID + "=" + contactId,
							null, null);
					
					String displayName = "";
					String mobilePhone = ""; 
					byte[] photoByte = null;
					Bitmap bitmap = null; 
					
					if (dataCursor.moveToFirst()) {
						displayName = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
					do {
						
						
						if (dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
							switch (dataCursor.getInt(dataCursor.getColumnIndex("data2"))) {
							case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
								mobilePhone = dataCursor
										.getString(dataCursor
												.getColumnIndex("data1"));
								break;
							}
						}
						
						
                        if(dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)){
                            photoByte = dataCursor.getBlob(dataCursor.getColumnIndex("data15"));

                            if(photoByte != null) {
                                bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);
                            }
                        }
						
					}while(dataCursor.moveToNext());
					
						nombre.add(displayName); 
						telefonos.add(mobilePhone); 
						imagenes.add(bitmap);
						
					}
					else
					{
						return false; 
					}
				}while(contactsCursor.moveToNext());
				
				return true; 
			}
			else {
				return false; 
			}
			
			
		}
		

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		
			if (result)
			{
				//Toast.makeText(contexto, "datos encontrados", Toast.LENGTH_SHORT).show();
				Crealista(); 
			}
			else
			{
				Toast.makeText(contexto, "No hay contactos", Toast.LENGTH_SHORT).show(); 
			}
		}
		
	}


	
	
}
