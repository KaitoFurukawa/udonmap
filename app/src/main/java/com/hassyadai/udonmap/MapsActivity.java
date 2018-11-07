package com.hassyadai.udonmap;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.hassyadai.udonmap.constant.LocationsConstant;
import com.hassyadai.udonmap.data.Udonya;

import org.w3c.dom.ls.LSException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener {

    private GoogleMap mMap;


    List<Udonya> udonyaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    //クリックした時の動作
    public void onMapClick(LatLng latLng) {
        Log.d("ろぐ","緯度："+ latLng.latitude);
        Log.d("ろぐ","経度："+ latLng.longitude);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        InputStream inputStream = null;

        //jsonに格納した情報を代入
        try{
            inputStream = getAssets().open("udon_shop.json");

        } catch (Exception e) {     //エラーが出えたときの行動

        }
        //Gsonを使ってjsonをjavaに読み込み
        JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));
        Type type = new TypeToken<List<Udonya>>(){}.getType();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

//        CollectionReference docRef = db.collection("udonshop");
        db.collection("udon")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("ろぐ", document.getId() + " => " + document.getData());
                   }
                } else {
                    Log.d("", "Error getting documents: ", task.getException());
                }
            }
        });


        udonyaList = new Gson().fromJson(jsonReader,type);

        for (int i = 0; i < udonyaList.size(); i++) {
            Udonya udonya =udonyaList.get(i);
            MarkerOptions o = new MarkerOptions();
            o.icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.mapmarker));
            o.position(new LatLng(udonya.lat, udonya.lng));
            // o.alpha(0.6f);
            o.title(udonya.name);
            o.snippet(udonya.url);
            o.anchor(0.5f, 0.5f);
            mMap.addMarker(o);
        }

        //うどん屋の情報とマーカーをマップ上に表示
       // for(int i = 0;i < udonyaList.size();i++){
//            Udonya udonya =udonyaList.get(i);
//            mMap.addMarker(new MarkerOptions().position(new LatLng(udonya.lat,udonya.lng)).title(udonya.name).snippet(udonya.url));
//        }

        mMap.setInfoWindowAdapter(new UdonshopInfowWndowViewer(this,udonyaList));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
//                Uri uri = Uri.parse(marker.getSnippet());
//                Intent i = new Intent(Intent.ACTION_VIEW,uri);
//                startActivity(i);
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                final CustomTabsIntent tabsIntent = builder
                        .setShowTitle(false)
                        .setToolbarColor(ContextCompat.getColor(MapsActivity.this, R.color.colorPrimary))
                        .enableUrlBarHiding().build();
                tabsIntent.launchUrl(MapsActivity.this, Uri.parse(marker.getSnippet()));
            }
        });
        mMap.setOnMapClickListener(this);
    }
}
