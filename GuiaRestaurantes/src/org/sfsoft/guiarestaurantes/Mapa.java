package org.sfsoft.guiarestaurantes;

import org.sfsoft.guiarestaurantes.util.Util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Activity donde se muestra el mapa
 * @author Santiago Faci
 *
 */
public class Mapa extends FragmentActivity {

	private GoogleMap mapa;
	
	private double latitud;
	private double longitud;
	private String nombre;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        
        // Recoge los datos enviados por la Activity que la invoca
        Intent i = getIntent();
        latitud = i.getFloatExtra("latitud", 0);
        longitud = i.getFloatExtra("longitud", 0);
        nombre = i.getStringExtra("nombre");
        
        // Transforma las coordenadas al sistema LatLng y las almacena
        uk.me.jstott.jcoord.LatLng ubicacion = Util.DeUMTSaLatLng(latitud, longitud, 'N', 30);
        this.latitud = ubicacion.getLat();
        this.longitud = ubicacion.getLng();
        this.nombre = nombre;
        
        // Inicializa el sistema de mapas de Google
        try {
            MapsInitializer.initialize(this);
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        
        // Obtiene una referencia al objeto que permite "manejar" el mapa
        mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	ubicarRestaurante();
    }
    
    /**
     * Marca el restaurante elegido en el mapa
     */
    private void ubicarRestaurante() {
    	
    	// Obtiene una vista de c�mara
    	CameraUpdate camara =
    			CameraUpdateFactory.newLatLng(new LatLng(latitud, longitud));
        	 
    	// Coloca la vista del mapa sobre la posici�n del restaurante 
    	// y activa el zoom para verlo de cerca
    	mapa.moveCamera(camara);
    	mapa.animateCamera(CameraUpdateFactory.zoomTo(17.0f)); 
        	
        Toast.makeText(this, latitud + ":" + longitud, Toast.LENGTH_SHORT).show();
    	
        // A�ade una marca en la posici�n del restaurante con el nombre de �ste
    	mapa.addMarker(new MarkerOptions()
    		.position(new LatLng(latitud, longitud))
    		.title(nombre));
    }
}
