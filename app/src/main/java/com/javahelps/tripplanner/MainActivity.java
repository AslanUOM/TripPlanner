package com.javahelps.tripplanner;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.txtStart)
    TextView txtStart;
    @Bind(R.id.txtEnd)
    TextView txtEnd;
    @Bind(R.id.lstItems)
    ListView lstItems;

    private List<Location> locations;

    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        this.locations = getLocations();
        CustomAdapter adapter = new CustomAdapter(this, locations);
        lstItems.setAdapter(adapter);

        txtStart.setText(dateFormat.format(startDate.getTime()));
        txtEnd.setText(dateFormat.format(endDate.getTime()));
    }

    @OnClick(R.id.txtStart)
    public void onStartDateClicked() {
        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        startDate.set(Calendar.YEAR, year);
                        startDate.set(Calendar.MONTH, monthOfYear);
                        startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                        // Display Selected date in textbox
                        txtStart.setText(dateFormat.format(startDate.getTime()));
                    }
                }, startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    @OnClick(R.id.txtEnd)
    public void onEndDateClicked() {
        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        endDate.set(Calendar.YEAR, year);
                        endDate.set(Calendar.MONTH, monthOfYear);
                        endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Display Selected date in textbox
                        txtEnd.setText(dateFormat.format(endDate.getTime()));
                    }
                }, endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    @OnClick(R.id.btnSuggest)
    public void onSuggestClicked() {
        for (Location location : locations) {
            if (location.isSelected()) {
                Toast.makeText(this, location.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class CustomAdapter extends ArrayAdapter<Location> {
        private List<Location> locations;

        public CustomAdapter(Context context, List<Location> objects) {
            super(context, 0, objects);
            this.locations = objects;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view != null) {
                holder = (ViewHolder) view.getTag();
            } else {
                view = getLayoutInflater().inflate(R.layout.item_place_for_selection, parent, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }

            holder.setLocation(locations.get(position));

            return view;
        }
    }


    private List<Location> getLocations() {
        List<Location> list = new ArrayList<>();
        String[] originalArray = getResources().getStringArray(R.array.locations);
        for (int i = 0; i < originalArray.length; i++) {
            String[] array = originalArray[i].split(":");
            Location location = new Location();
            location.setName(array[0]);
            location.setLongitude(Double.parseDouble(array[1]));
            location.setLatitude(Double.parseDouble(array[2]));
            location.setDescription(array[3]);

            list.add(location);
        }

        return list;
    }
}
