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
    private TextView recommendTextView;
    private List<Udonya> udonyaList;
    private TextView commentTextView;

    public UdonshopInfowWndowViewer(Activity activity, List<Udonya> udonyaList){
        infoWindowView = activity.getLayoutInflater().inflate(R.layout.infowindow_udonshop,null);
        shopNameTextView = infoWindowView.findViewById(R.id.shopNameTextView);
        recommendTextView = infoWindowView.findViewById(R.id.recommendTextView);
        commentTextView = infoWindowView.findViewById(R.id.commentTextView);
        this.udonyaList = udonyaList;

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
        for(int i = 0;i < udonyaList.size();i++){
            Udonya udonya = udonyaList.get(i);
            Log.d("ろぐ",""+marker.getTitle()+". "+udonya.name);
            if(marker.getTitle().equals(udonya.name) ){
                commentTextView.setText(udonya.comment);
            }
        }

        return infoWindowView;
    }

}
