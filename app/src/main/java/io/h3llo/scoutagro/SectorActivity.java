package io.h3llo.scoutagro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.icu.number.Precision;
import android.icu.text.DecimalFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Layer;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonLineStringStyle;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SectorActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private FusedLocationProviderClient mFusedLocationClient;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean isContinue = false;

    GoogleMap mapa;

    LatLng ubicacionPeru, ubicacionAyacucho, ubicacionMoquegua, ubicacionPiura, ubicacionTacna;
    List<LatLng> latLngList= new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();

    String sect;
    String dist;
    String dep;
    String nom_file;

    Polygon polygon;

    int checkseguir=1;
    CheckBox checkpuntos;

    private static DecimalFormat df = new DecimalFormat("0.00");

    private void getLocation() {
        if (isContinue) {
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        // txtLocation.setText(String.format("%s - %s", wayLatitude, wayLongitude));
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //SeekBar seekazul, seekverde, seekrojo;
        Button btndibujar, btnlimpiar;


        int rojo=0,verde=0,azul=0;

        ubicacionPeru = new LatLng(-10.458852, -74.734769);
        ubicacionAyacucho= new LatLng(-13.1606481,-74.2262629);
        ubicacionMoquegua= new LatLng(-17.1938282,-70.9355075);
        ubicacionPiura= new LatLng(-5.1968859,-80.6301679);
        ubicacionTacna= new LatLng(-18.013766, -70.255331);

        Bundle extras = getIntent().getExtras();

        String departamento = extras.getString("departamento");
        String provincia = extras.getString("provincia");
        String distrito = extras.getString("distrito");
        String sectorEstadistico =  extras.getString("sectorEstadistico");
        String cultivo = extras.getString("cultivo");
        String supAsegHas = extras.getString("supAsegHas");
        String rendPromKgXHa = extras.getString("rendPromKgXHa");
        String rendDisp = extras.getString("rendDisp");
        String nomFile = extras.getString("nomFile");

        this.sect = sectorEstadistico.toString();
        this.dist = distrito.toString();
        this.dep = departamento.toString();
        this.nom_file = nomFile;

        // INICIO FUSED LOCATION CLIENT

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        if (!isContinue) {
                            // txtLocation.setText(String.format("%s - %s", wayLatitude, wayLongitude));
                        } else {
//                            stringBuilder.append(wayLatitude);
//                            stringBuilder.append("-");
//                            stringBuilder.append(wayLongitude);
//                            stringBuilder.append("\n\n");
//                            txtContinueLocation.setText(stringBuilder.toString());
                        }
                        if (!isContinue && mFusedLocationClient != null) {
                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                        }
                    }
                }
            }
        };

        // FIN FUSED LOCATION CLIENT



                super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sector);

        // Obtenemos el mapa de forma asíncrona (notificará cuando esté listo)
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);

        btndibujar=findViewById(R.id.btndibujar);
        btnlimpiar=findViewById(R.id.btnlimpiar);
        checkpuntos=findViewById(R.id.checkpuntos);


        mapFragment.getMapAsync(this);

        btndibujar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (polygon != null) polygon.remove();
                if(!latLngList.isEmpty()) {
                    PolygonOptions polygonOptions = new PolygonOptions()
                            .addAll(latLngList).clickable(true);
                    polygon = mapa.addPolygon(polygonOptions);
                    polygon.setStrokeColor(Color.rgb(200, 0, 0));
//                    if (checkpintar.isChecked())
//                        polygon.setFillColor(Color.rgb(rojo, verde, azul));

                    // CREAR GEOJSON NUEVO MAYBE HERE

                }
                // POP-UP




                // INTENT WHATS APP OR GMAIL
                double area = SphericalUtil.computeArea(latLngList) * 0.0001;
                String area_str = Double.toString(area);
                String textMessage = "Cultivo: " + "\n"+ "Departamento: " + dep + "\n" + "Distrito: " + dist + "\n" + "Sector Estadístico: " + sect + "\n" + "Area encontrada (Has): " + area_str + "\n";

                String geoJsonString = "\n\n" + "GeoJSON"+"\n\n";

                String subject = textMessage;
                textMessage += "Puntos: " + "\n";

                // INICIA CREACION DE GEOJSON

                geoJsonString += "{" + "\n" +
                        "\"type\"" + " : " + "\"FeatureCollection\"," + "\n" +
                        "\"crs\": { \"type\": \"name\", \"properties\": { \"name\": \"urn:ogc:def:crs:OGC:1.3:CRS84\" } }," + "\n" +
                        // "crs": { "type": "name", "properties": { "name": "urn:ogc:def:crs:OGC:1.3:CRS84" } },
                        "\"features\": [" + "\n" +
                        "{" + "\n" +
                        "\"type\"" + " : " + "\"Feature\"," + "\n" +
                        "\"properties\": {" +

                        // FEATURES
                        "\"Cultivo\": " +"\"" +dep+ "\"," +
                        "\"Departamento\": " +"\"" +dep+ "\"," +
                        "\"Distrito\": " +"\"" +dist+ "\"," +
                        "\"Sector Estadistico\": " +"\"" +sect+ "\"," +
                        "\"Area Encontrada(HAS)\": " +"\"" +area_str+ "\"," +

                        "},"+ "\n" +  // here come the properties !!!!
                        "\"geometry\": {" + "\n" +
                        "\"type\": \"Polygon\"," + "\n" +
                        "\"coordinates\": [" + "\n" +
                        "[" + "\n";

/*
                {
                    "type": "FeatureCollection",
                        "features": [
                    {
                        "type": "Feature",
                            "properties": {},
                        "geometry": {
                        "type": "Polygon",
                                "coordinates": [
          [
            [
                        -36.9140625,
                                10.833305983642491
            ],
            [
                        -15.1171875,
                                51.6180165487737
            ],
            [
                        -36.9140625,
                                10.833305983642491
            ]
          ]
        ]
                    }
                    }
  ]
                }

                */


                // FIN CREACION DE GEOJSON

                for(int i = 0; i< latLngList.size(); i++){
                    textMessage += "Punto " + Integer.toString(i) + "\n" + "Latitud: " + latLngList.get(i).latitude + "\n" + "Longitud: " + latLngList.get(i).longitude + "\n";
                    geoJsonString += "[ \n" + latLngList.get(i).longitude + ",\n" + latLngList.get(i).latitude + "\n" + "]," + "\n" ;
                }

                geoJsonString = geoJsonString.substring(0, geoJsonString.length() - 2);
                geoJsonString = geoJsonString + "\n" + "]\n" + "]\n" + "}\n" + "}\n" + "]\n" + "}\n";

                textMessage += geoJsonString;

                Toast.makeText(getApplicationContext(), "Area: " + df.format(area)   + " hectáreas.", Toast.LENGTH_SHORT).show();

                // Create the text message with a string
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
                sendIntent.setType("text/plain");

                // Verify that the intent will resolve to an activity
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(sendIntent);
                }

            }
        });

        btnlimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(polygon!=null) polygon.remove();
                for(Marker marker: markerList) marker.remove();
                latLngList.clear();
                markerList.clear();
//                seekazul.setProgress(0);
//                seekrojo.setProgress(0);
//                seekverde.setProgress(0);
            }
        });



    }

    @Override
    public void onMapClick(LatLng latLng) {
        // Toast.makeText(getApplicationContext(), "Alohaa OnMapclick",Toast.LENGTH_SHORT).show();
        if(checkpuntos.isChecked()){
            Marker marker = mapa.addMarker(new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            latLngList.add(latLng);
            markerList.add(marker);
        }


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {


        // String nom_se = ("nom_se_"+this.sect.toLowerCase());
        //String nom_se = ("nom_file_4_"+this.dep.toLowerCase()+"_"+this.dist.toLowerCase()+"_"+this.sect.toLowerCase());
        //String nom_se = ("nom_file_4_"+this.dep.toLowerCase()+"_"+this.dist.toLowerCase()+"_"+this.sect.toLowerCase());
        String nom_se = ("nom_file_4_"+this.dep.toLowerCase()+"_"+this.nom_file);

        Resources res = getResources();
        int se = res.getIdentifier(nom_se, "raw", getPackageName());
        //int se = res.getIdentifier("nom_se_acco", "raw", getPackageName());


        mapa = googleMap;
        ubicacionPeru = new LatLng(-10.458852, -74.734769);
        ubicacionAyacucho= new LatLng(-13.1606481,-74.2262629);
        ubicacionMoquegua= new LatLng(-17.1938282,-70.9355075);
        ubicacionPiura= new LatLng(-5.1968859,-80.6301679);
        ubicacionTacna= new LatLng(-18.013766, -70.255331);
        mapa.setInfoWindowAdapter(new CustomInfoViewAdapter(LayoutInflater.from(this)));

        // HABILITA LA UBICACION DEL DISPOSITIVO EN EL MAPA
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(true);
            mapa.getUiSettings().setCompassEnabled(true);
        }
//        else {
//            Button btnMiPos=(Button) findViewById(R.id.btnmiubi);
//            btnMiPos.setEnabled(false);
//        }

        /// GEOJSON START
        try{
            GeoJsonLayer layerPeru = new GeoJsonLayer(mapa, R.raw.a0_peru_outlayer, this);
            GeoJsonLayer layerAyacucho = new GeoJsonLayer(mapa, R.raw.a0_ayacucho_outlayer, this);
            GeoJsonLayer layerMoquegua = new GeoJsonLayer(mapa, R.raw.a0_moquegua_outlayer, this);
            GeoJsonLayer layerPiura = new GeoJsonLayer(mapa, R.raw.a0_piura_outlayer, this);
            GeoJsonLayer layerTacna = new GeoJsonLayer(mapa, R.raw.a0_tacna_outlayer, this);
            GeoJsonLayer layerSector = new GeoJsonLayer(mapa, se, this);


            GeoJsonLineStringStyle polyStylePeru = layerPeru.getDefaultLineStringStyle();
            GeoJsonLineStringStyle polyStyleAyacucho = layerAyacucho.getDefaultLineStringStyle();
            GeoJsonLineStringStyle polyStyleMoquegua = layerMoquegua.getDefaultLineStringStyle();
            GeoJsonLineStringStyle polyStylePiura = layerPiura.getDefaultLineStringStyle();
            GeoJsonLineStringStyle polyStyleTacna = layerTacna.getDefaultLineStringStyle();
            GeoJsonLineStringStyle polyStyleSector = layerSector.getDefaultLineStringStyle();


            //polyStylePeru.setStrokeColor(Color.BLACK);
            polyStylePeru.setColor(Color.CYAN);
            polyStyleAyacucho.setColor(Color.GREEN);
            polyStyleMoquegua.setColor(Color.GREEN);
            polyStylePiura.setColor(Color.GREEN);
            polyStyleTacna.setColor(Color.GREEN);
            polyStyleSector.setColor(Color.YELLOW);



            //polyStylePeru.setStrokeWidth(4);
            polyStyleAyacucho.setWidth(4);
            polyStyleMoquegua.setWidth(4);
            polyStylePiura.setWidth(4);
            polyStyleTacna.setWidth(4);
            polyStyleSector.setWidth(4);
            // polyStyleSector.setClickable(true);

            layerPeru.addLayerToMap();
            layerAyacucho.addLayerToMap();
            layerMoquegua.addLayerToMap();
            layerPiura.addLayerToMap();
            layerTacna.addLayerToMap();
            layerSector.addLayerToMap();


            switch(dep.toLowerCase()) {
                case "ayacucho":
                    mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionAyacucho,7));
                    break;
                case "moquegua":
                    mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionMoquegua,7));
                    break;
                case "piura":
                    mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionPiura,7));
                    break;
                case "tacna":
                    mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionTacna,7));
                    break;
                default:
                    mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionTacna,5));
            }

            layerSector.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    Toast.makeText(getApplicationContext(), "Alohaa pinhaaaa",Toast.LENGTH_LONG).show();

                }
            });







        } catch (IOException e){
            // String mLogTag;
            // Log.e(mLogTag, "GeoJSON file could not be read");
        } catch (JSONException e){
            // String mLogTag;
            // Log.e(mLogTag, "GeoJSON file could not be converted to JSONObject");
        }


        mapa.setOnMapClickListener(this);

// INICIO LOCATION MANAGER

//        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
//            Toast.makeText(this, "GPS available", Toast.LENGTH_LONG).show();
//        else Toast.makeText(this, "GPS not available", Toast.LENGTH_LONG).show();
//        LocationListener locationListener = new LocationListener() {
//            public void onLocationChanged(Location location) {
//                if (checkseguir == 1) {
//                    Toast.makeText(getApplicationContext(), "Se cambio de posicion", Toast.LENGTH_SHORT).show();
//                    Double latitude = location.getLatitude();
//                    Double longitude = location.getLongitude();
//                    Toast.makeText(getApplicationContext(), "latitud: " + latitude.toString() +
//                            " longitud: " + longitude.toString(), Toast.LENGTH_SHORT).show();
//                    mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
//                }
//            }
//            public void onStatusChanged(String provider, int status, Bundle extras) { }
//            public void onProviderEnabled(String provider) { }
//            public void onProviderDisabled(String provider) { }
//        };
//
//        // Enable the location layer. Request the location permission if needed.
//        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mapa.setMyLocationEnabled(true);
//        }




        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //mapa.moveCamera(CameraUpdateFactory.newLatLng(ubicacionPeru));
        //mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionPeru,5));





    }


}