package com.example.dola.guardiannews;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GuardianNewAdapter  extends ArrayAdapter<GuardianNew> {
    /**
     * Constructs a new {@link GuardianNew}/
     * @param context  of the app
     * @param guardianNews is the list if Guardian, which
     */
    public GuardianNewAdapter(@NonNull Context context, ArrayList<GuardianNew> guardianNews) {
        super(context, 0, guardianNews);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        GuardianNew guardianNew = getItem(position);

        // Find the TextView in the list_item.xml layout with ID web_title_textview
        TextView titleTextview = convertView.findViewById(R.id.web_title_textview);
        titleTextview.setText(String.format("%s", guardianNew.getWebTitle()));

        // Find the TextView in the list_item.xml layout with ID web_title_textview
        TextView pillarNameTextview = convertView.findViewById(R.id.pillar_name_textview);
        pillarNameTextview.setText(String.format("%s", guardianNew.getPillarName()));

        GradientDrawable pillarCircle = (GradientDrawable) pillarNameTextview.getBackground();
        int pillarColor = getGuardianPillarColor(guardianNew.getPillarName());

        pillarCircle.setColor(pillarColor);

        // Find the TextView in the list_item.xml layout with ID section_name_textview
        TextView sectionNameTextview = convertView.findViewById(R.id.section_name_textview);
        sectionNameTextview.setText(String.format("%s", guardianNew.getSectionName()));

        // Find the TextView in the list_item.xml layout with ID publication_date_textview
        TextView dateTextview = convertView.findViewById(R.id.publication_date_textview);
        String[] dateStr = guardianNew.getWebPublicationDate().split("T");
        if (dateStr.length > 1){
            dateTextview.setText(String.format("%s", dateStr[0]));
        } else {
            dateTextview.setText(String.format("%s", dateStr[0]));
        }
        return  convertView;
    }

    private int getGuardianPillarColor(String pillarName){
        int magnitudeColorResourceId;

        switch (pillarName){
            case MainActivity.SPORT:
                magnitudeColorResourceId = R.color.sport;
                break;
            case MainActivity.NEWS:
                magnitudeColorResourceId = R.color.news;
                break;
            case MainActivity.ARTS:
                magnitudeColorResourceId = R.color.arts;
                break;
            case MainActivity.LIFESTYLE:
                magnitudeColorResourceId = R.color.lifestyle;
                break;

            default:
                magnitudeColorResourceId = R.color.others;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
