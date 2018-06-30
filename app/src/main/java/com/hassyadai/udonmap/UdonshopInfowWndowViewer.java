package com.hassyadai.udonmap;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class UdonshopInfowWndowViewer implements GoogleMap.InfoWindowAdapter {
    private View infoWindowView;
    private TextView shopNameTextView;
    private TextView recommendTextView;

    public UdonshopInfowWndowViewer(Activity activity){
        infoWindowView = activity.getLayoutInflater().inflate(R.layout.infowindow_udonshop,null);
        shopNameTextView = infoWindowView.findViewById(R.id.shopNameTextView);
        recommendTextView = infoWindowView.findViewById(R.id.recommendTextView);

    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Log.d("ろぐ","" + marker.getTitle());
        shopNameTextView.setText(marker.getTitle());
        recommendTextView.setText(marker.getSnippet());

        return infoWindowView;
    }

}
