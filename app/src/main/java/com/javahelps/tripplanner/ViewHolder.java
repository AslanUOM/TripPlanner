package com.javahelps.tripplanner;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gobinath on 1/29/16.
 */
public class ViewHolder {
    @Bind(R.id.txtName)
    TextView txtName;
    @Bind(R.id.txtDescription)
    TextView txtDescription;
    @Bind(R.id.imgIcon)
    ImageView imgIcon;
    @Bind(R.id.btnState)
    ToggleButton btnState;

    private Location location;

    public ViewHolder(View view) {
        ButterKnife.bind(this, view);

        btnState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (location != null) {
                    location.setSelected(isChecked);
                }
            }
        });
    }

    public void setLocation(Location location) {
        this.location = location;
        txtName.setText(location.getName());
        txtDescription.setText(location.getDescription());
        btnState.setChecked(location.isSelected());
    }
}
