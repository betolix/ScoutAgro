package io.h3llo.scoutagro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    GoogleMap mapa;

    LatLng ubicacionPeru, ubicacionAyacucho, ubicacionMoquegua, ubicacionPiura, ubicacionTacna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtenemos el mapa de forma asíncrona (notificará cuando esté listo)
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }

    public void moveCamera(View view) {
        mapa.moveCamera(CameraUpdateFactory.newLatLng(ubicacionPeru));
    }
    public void addMarker(View view) {
        mapa.addMarker(new MarkerOptions().position(
                mapa.getCameraPosition().target));
    }
    @Override public void onMapClick(LatLng puntoPulsado) {
//        mapa.addMarker(new MarkerOptions().position(puntoPulsado)
//                .icon(BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mapa.setInfoWindowAdapter(new CustomInfoViewAdapter(LayoutInflater.from(this)));
        mapa.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener(){

            @Override
            public void onInfoWindowClick(Marker marker) {
                switch(marker.getTitle()) {
                    case "Ayacucho":
                    {
                        Toast.makeText(getApplicationContext(),"Info Window Ayacucho Clicked", Toast.LENGTH_SHORT).show();
                        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionAyacucho, 7));
                        try {
                            GeoJsonLayer layerAyacucho_se = new GeoJsonLayer(mapa, R.raw.y_ayacucho_se, getApplicationContext());
                            GeoJsonPolygonStyle polyStyleAyacuchoSE = layerAyacucho_se.getDefaultPolygonStyle();
                            polyStyleAyacuchoSE.setStrokeColor(Color.RED);
                            polyStyleAyacuchoSE.setStrokeWidth(4);
                            //layerAyacucho_se.getFeatures()
                            layerAyacucho_se.addLayerToMap();
                        }
                        catch (IOException e){
                            // String mLogTag;
                            // Log.e(mLogTag, "GeoJSON file could not be read");
                        } catch (JSONException e){
                            // String mLogTag;
                            // Log.e(mLogTag, "GeoJSON file could not be converted to JSONObject");
                        }

                    }
                        break;
                    case "Moquegua":
                    {
                        Toast.makeText(getApplicationContext(),"Info Window Moquegua Clicked", Toast.LENGTH_SHORT).show();
                        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionMoquegua, 7));
                        try {
                            GeoJsonLayer layerAyacucho_se = new GeoJsonLayer(mapa, R.raw.y_moquegua_se, getApplicationContext());
                            GeoJsonPolygonStyle polyStyleAyacuchoSE = layerAyacucho_se.getDefaultPolygonStyle();
                            polyStyleAyacuchoSE.setStrokeColor(Color.RED);
                            polyStyleAyacuchoSE.setStrokeWidth(4);
                            layerAyacucho_se.addLayerToMap();
                        }
                        catch (IOException e){
                            // String mLogTag;
                            // Log.e(mLogTag, "GeoJSON file could not be read");
                        } catch (JSONException e){
                            // String mLogTag;
                            // Log.e(mLogTag, "GeoJSON file could not be converted to JSONObject");
                        }
                    }
                        break;
                    case "Piura":
                    {
                        Toast.makeText(getApplicationContext(),"Info Window Piura Clicked", Toast.LENGTH_SHORT).show();
                        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionPiura, 7));
                        try {
                            GeoJsonLayer layerAyacucho_se = new GeoJsonLayer(mapa, R.raw.y_piura_se, getApplicationContext());
                            GeoJsonPolygonStyle polyStyleAyacuchoSE = layerAyacucho_se.getDefaultPolygonStyle();
                            polyStyleAyacuchoSE.setStrokeColor(Color.RED);
                            polyStyleAyacuchoSE.setStrokeWidth(4);
                            layerAyacucho_se.addLayerToMap();
                        }
                        catch (IOException e){
                            // String mLogTag;
                            // Log.e(mLogTag, "GeoJSON file could not be read");
                        } catch (JSONException e){
                            // String mLogTag;
                            // Log.e(mLogTag, "GeoJSON file could not be converted to JSONObject");
                        }
                    }
                        break;
                    case "Tacna":
                    {
                        Toast.makeText(getApplicationContext(),"Info Window Tacna Clicked", Toast.LENGTH_SHORT).show();
                        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionTacna, 7));
                        try {
                            GeoJsonLayer layerAyacucho_se = new GeoJsonLayer(mapa, R.raw.y_tacna_se, getApplicationContext());
                            GeoJsonPolygonStyle polyStyleAyacuchoSE = layerAyacucho_se.getDefaultPolygonStyle();
                            polyStyleAyacuchoSE.setStrokeColor(Color.RED);
                            polyStyleAyacuchoSE.setStrokeWidth(4);
                            layerAyacucho_se.addLayerToMap();
                        }
                        catch (IOException e){
                            // String mLogTag;
                            // Log.e(mLogTag, "GeoJSON file could not be read");
                        } catch (JSONException e){
                            // String mLogTag;
                            // Log.e(mLogTag, "GeoJSON file could not be converted to JSONObject");
                        }
                    }
                        break;
                    default:
                        // code block
                }

            }
        });

        mapa.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(), DepDetailActivity.class);
                startActivity(intent);

            }
        });
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        ubicacionPeru = new LatLng(-10.458852, -74.734769);
        ubicacionAyacucho= new LatLng(-13.1606481,-74.2262629);
        ubicacionMoquegua= new LatLng(-17.1938282,-70.9355075);
        ubicacionPiura= new LatLng(-5.1968859,-80.6301679);
        ubicacionTacna= new LatLng(-18.013766, -70.255331);
        mapa.setInfoWindowAdapter(new CustomInfoViewAdapter(LayoutInflater.from(this)));


        /// GEOJSON START
        try{
            GeoJsonLayer layerPeru = new GeoJsonLayer(mapa, R.raw.a0_peru_outline_geojson, this);
            GeoJsonLayer layerAyacucho = new GeoJsonLayer(mapa, R.raw.a_ayacucho, this);
            GeoJsonLayer layerMoquegua = new GeoJsonLayer(mapa, R.raw.b_moquegua, this);
            GeoJsonLayer layerPiura = new GeoJsonLayer(mapa, R.raw.d_piura, this);
            GeoJsonLayer layerTacna = new GeoJsonLayer(mapa, R.raw.c_tacna, this);




            GeoJsonPolygonStyle polyStylePeru = layerPeru.getDefaultPolygonStyle();
            GeoJsonPolygonStyle polyStyleAyacucho = layerAyacucho.getDefaultPolygonStyle();
            GeoJsonPolygonStyle polyStyleMoquegua = layerMoquegua.getDefaultPolygonStyle();
            GeoJsonPolygonStyle polyStylePiura = layerPiura.getDefaultPolygonStyle();
            GeoJsonPolygonStyle polyStyleTacna = layerTacna.getDefaultPolygonStyle();


            polyStylePeru.setStrokeColor(Color.YELLOW);
            polyStyleAyacucho.setStrokeColor(Color.GREEN);
            polyStyleMoquegua.setStrokeColor(Color.GREEN);
            polyStylePiura.setStrokeColor(Color.GREEN);
            polyStyleTacna.setStrokeColor(Color.GREEN);



            polyStylePeru.setStrokeWidth(4);
            polyStyleAyacucho.setStrokeWidth(4);
            polyStyleMoquegua.setStrokeWidth(4);
            polyStylePiura.setStrokeWidth(4);
            polyStyleTacna.setStrokeWidth(4);

            layerPeru.addLayerToMap();
            layerAyacucho.addLayerToMap();
            layerMoquegua.addLayerToMap();
            layerPiura.addLayerToMap();
            layerTacna.addLayerToMap();



        } catch (IOException e){
            // String mLogTag;
            // Log.e(mLogTag, "GeoJSON file could not be read");
        } catch (JSONException e){
            // String mLogTag;
            // Log.e(mLogTag, "GeoJSON file could not be converted to JSONObject");
        }




        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);



        mapa.addMarker(new MarkerOptions().position(ubicacionPeru).title("Peru")
                .snippet("Area Km2 = 1 285 216.60 Km2 " + System.lineSeparator() + "Area Hás = 128 521 660 Hectáreas"));

        mapa.addMarker(new MarkerOptions().position(ubicacionAyacucho).title("Ayacucho")
                .snippet("Area Total Km2 = 43 814.80 Km2 " + System.lineSeparator()
                        + "Area Total Hás = 4 381 480 Hectáreas" + System.lineSeparator()
                        + " # Sectores Estadísticos 413" + System.lineSeparator()
                        + "Hectáreas Aseguradas 97 242 .91 Hás"));

        mapa.addMarker(new MarkerOptions().position(ubicacionMoquegua).title("Moquegua")
                .snippet("Area Km2 = 15 734.80 Km2 " + System.lineSeparator()
                        + "Area Hás = 1 573 400 Hectáreas"+ System.lineSeparator()
                        + " # Sectores Estadísticos 108" + System.lineSeparator()
                        + "Hectáreas Aseguradas 12 566 .25 Hás"));

        mapa.addMarker(new MarkerOptions().position(ubicacionPiura).title("Piura")
                .snippet("Area Km2 = 35 892.49 Km2"  + System.lineSeparator()
                        + "Area Hás = 3 589 249 Hectáreas"+ System.lineSeparator()
                        + " # Sectores Estadísticos 318" + System.lineSeparator()
                        + "Hectáreas Aseguradas 113 112 .70 Hás"));

        mapa.addMarker(new MarkerOptions().position(ubicacionTacna).title("Tacna")
                .snippet("Area Km2 = 16 075.89 Km2 " + System.lineSeparator()
                        + "Area Hás = 1 607 589 Hectáreas"+ System.lineSeparator()
                        + " # Sectores Estadísticos 52" + System.lineSeparator()
                        + "Hectáreas Aseguradas 17 724 .55 Hás"));

        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionPeru,5));



        mapa.setOnMapClickListener(this);






    }


}