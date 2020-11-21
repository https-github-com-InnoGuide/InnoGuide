package com.example.webapplicationwithspring;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.webapplicationwithspring.Events.EventsController;

// class for the event fragment click
public class EventFragment extends Fragment {
// static class creation with parameter index, index is needed to display particular event from eventController
    public static EventFragment newInstance(int index) {
        EventFragment f = new EventFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    // here we initialize all the data to eventFragment layout and return the View
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View event = layoutInflater.inflate(R.layout.event, null, false);
        TextView name = event.findViewById(R.id.textView_name);
        TextView date = event.findViewById(R.id.textView_date);
        TextView description = event.findViewById(R.id.textView_description);
        TextView place = event.findViewById(R.id.textView_place);

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        name.setText(EventsController.getEvent(getArguments().getInt("index",0)).getEventName());
        date.setText(EventsController.getEvent(getArguments().getInt("index",0)).getEventDate());
        description.setText(EventsController.getEvent(getArguments().getInt("index",0)).getEventDescription());
        place.setText(EventsController.getEvent(getArguments().getInt("index",0)).getEventPlace());

        return event;
    }
}

