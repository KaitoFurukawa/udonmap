package com.hassyadai.udonmap.data;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Udonya {
    public String name;
    public GeoPoint location;
    public double lat;
    public double lng;
    public boolean jimoto_flag;
    public String url;
    public String comment;
}
