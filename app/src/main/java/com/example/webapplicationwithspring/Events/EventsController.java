package com.example.webapplicationwithspring.Events;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EventsController {
    private static Map<Integer,Event> eventsMap= new HashMap<Integer,Event>();
    public static void addEvent(String eventName, String eventDate, String eventDescription, String eventPlace, int id){
        eventsMap.put(id, new Event(eventName,eventDate,eventDescription,eventPlace,id));
    }
    public static Event getEvent(int index){
        return eventsMap.get(index);
    }
    public static Collection<Event> getValue(){
        return eventsMap.values();
    }
    public static int getSize(){ return eventsMap.size(); }

}

