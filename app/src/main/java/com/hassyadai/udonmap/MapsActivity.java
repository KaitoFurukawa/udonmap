package com.hassyadai.udonmap;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
        udonyaList = new Gson().fromJson(jsonReader,type);

        //うどん屋の情報とマーカーをマップ上に表示
        for(int i = 0;i < udonyaList.size();i++){
            Udonya udonya =udonyaList.get(i);
            mMap.addMarker(new MarkerOptions().position(new LatLng(udonya.lat,udonya.lng)).title(udonya.name).snippet(udonya.recommend));
        }

        mMap.setInfoWindowAdapter(new UdonshopInfowWndowViewer(this,udonyaList));





        // Add a marker in Sydney and move the camera
       // for(int i=0;i<13;i++){
          //  mMap.addMarker(new MarkerOptions().position(LocationsConstant.TAMURA).title(getResources().getStringArray(R.array.shopname_array)));
     //   }
//        mMap.addMarker(new MarkerOptions().position(LocationsConstant.TAMURA).title("田村　うどん店"));
//        mMap.addMarker(new MarkerOptions().position(LocationsConstant.OKASEN).title("本格手打ちうどん　おか泉"));
//        mMap.addMarker(new MarkerOptions().position(LocationsConstant.MUGINOHESO).title("麦のへそ"));
//        mMap.addMarker(new MarkerOptions().position(LocationsConstant.WATAYA).title("麺処　綿谷"));
//        mMap.addMarker(new MarkerOptions().position(LocationsConstant.YAMAGOE).title("山越うどん"));
//        mMap.addMarker(new MarkerOptions().position(LocationsConstant.NEKKO).title("根ッ子うどん"));

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(Kagawa));
        mMap.setOnMapClickListener(this);
    }
}
