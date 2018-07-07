package com.hassyadai.udonmap;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.hassyadai.udonmap.data.Udonya;

import java.util.List;

public class UdonshopInfowWndowViewer implements GoogleMap.InfoWindowAdapter {
    private View infoWindowView;
    private TextView shopNameTextView;
    private List<Udonya> udonyaList;
    private TextView commentTextView;

    public UdonshopInfowWndowViewer(Activity activity, List<Udonya> udonyaList){
        infoWindowView = activity.getLayoutInflater().inflate(R.layout.infowindow_udonshop,null);
        shopNameTextView = infoWindowView.findViewById(R.id.shopNameTextView);
        commentTextView = infoWindowView.findViewById(R.id.commentTextView);
        this.udonyaList = udonyaList;

    }
    @Override
    public View getInfoWindow(Marker marker) {
        shopNameTextView.setText(marker.getTitle());
        for(int i = 0;i < udonyaList.size();i++){
            Udonya udonya = udonyaList.get(i);
            if(marker.getTitle().equals(udonya.name) ){
                commentTextView.setText(udonya.comment);
            }
        }

        return infoWindowView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

}
