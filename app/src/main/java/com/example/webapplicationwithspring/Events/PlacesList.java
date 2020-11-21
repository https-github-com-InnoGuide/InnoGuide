package com.example.webapplicationwithspring.Events;

import java.util.ArrayList;
import java.util.List;

public class PlacesList {
    public static List<Place> places = new ArrayList<Place>(){
        {
            add(new Place("Bar 108", "55.74852740679568, 48.74187662098441", "Good place to sit with your friends and drink beer",
                    "13:00 - 02:00", "Sportivnaya street 108" , "@bar108", "+7(843)514-81-08", 0));
            add(new Place("Sport complex", "55.75149071098209, 48.74229488800128", "Good place for sport activities",
                    "07:00 - 23:00", "Sportivnaya street 107", "@sportInno", "+7(937)296-86-85", 1));
            add(new Place("Techno park", "55.75196407745037, 48.75216375438738", "Here are located modern buildings of business centers, specially designed to provide residents with the most comfortable working conditions.",
                    "10:00 - 20:00", "University Street 7 ", "@technopark", "+7(843)200-07-01", 2));
            add(new Place("Innopolis University", "55.753562696292434, 48.74349062437596", "Russian autonomous non-profit organization of highest education. It specializes in education, research and development in information technology and robotics.",
                    "08:00 - 21:00", "University Street 1", "@InnUni", "+7(843)203-92-53", 3));
            add(new Place("ArtSpace", "55.745132355186236, 48.748631580117085", "A place for relaxation, education and creativity.",
                    "10:00 - 22:00", "Park Street 1", "@ArtSpaceIn", "+7(843)203-92-53", 4));

        }
    };

    public static int getId(String name){
        for (Place p : places){
            if (p.getName().equals(name)){
                return p.getId();
            }
        }
        return -1;
    }

}
