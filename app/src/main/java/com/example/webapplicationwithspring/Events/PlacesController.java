package com.example.webapplicationwithspring.Events;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlacesController {
    public static Map<Integer, Place> placesMap = new HashMap<Integer, Place>();
    public static void addEvent(String name, String location, String description, String workHours,String address, String telegram, String phoneNumbe, int id){
        placesMap.put(id, new Place(name, location,description,workHours,address,telegram,phoneNumbe,id));
    }
    public static Place getPlace(int index){
        return placesMap.get(index);
    }
    public static Collection<Place> getValue(){
        return placesMap.values();
    }
    public static int getSize(){ return placesMap.size(); }

}
