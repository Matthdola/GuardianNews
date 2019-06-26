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
        GuardianNewViewHolder guardianNewViewHolder;

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            guardianNewViewHolder = new GuardianNewViewHolder(convertView);

            convertView.setTag(guardianNewViewHolder);

        } else {
            guardianNewViewHolder = (GuardianNewViewHolder)convertView.getTag();
        }

        GuardianNew guardianNew = getItem(position);

        guardianNewViewHolder.titleTextview.setText(String.format("%s", guardianNew.getWebTitle()));

        guardianNewViewHolder.pillarNameTextview.setText(String.format("%s", guardianNew.getPillarName()));

        GradientDrawable pillarCircle = (GradientDrawable) guardianNewViewHolder.pillarNameTextview.getBackground();
        int pillarColor = getGuardianPillarColor(guardianNew.getPillarName());

        pillarCircle.setColor(pillarColor);

        guardianNewViewHolder.sectionNameTextview.setText(String.format("%s", guardianNew.getSectionName()));

        guardianNewViewHolder.authorNameTextview.setText(String.format("%s: %s", getContext().getString(R.string.authors),guardianNew.getAuthorName()));

        String[] dateStr = guardianNew.getWebPublicationDate().split("T");
        if (dateStr.length > 1){
            guardianNewViewHolder.dateTextview.setText(String.format("%s", dateStr[0]));
        } else {
            guardianNewViewHolder.dateTextview.setText(String.format("%s", dateStr[0]));
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

    static class GuardianNewViewHolder{
        public TextView titleTextview;
        public TextView pillarNameTextview;
        public TextView sectionNameTextview;
        public TextView authorNameTextview;
        public TextView dateTextview;

        public GuardianNewViewHolder(View itemView){

            // Find the TextView in the list_item.xml layout with ID web_title_textview
            titleTextview = itemView.findViewById(R.id.web_title_textview);

            // Find the TextView in the list_item.xml layout with ID web_title_textview
            pillarNameTextview = itemView.findViewById(R.id.pillar_name_textview);

            // Find the TextView in the list_item.xml layout with ID section_name_textview
            sectionNameTextview = itemView.findViewById(R.id.section_name_textview);

            // Find the TextView in the list_item.xml layout with ID author_textview
            authorNameTextview = itemView.findViewById(R.id.author_textview);

            // Find the TextView in the list_item.xml layout with ID publication_date_textview
            dateTextview = itemView.findViewById(R.id.publication_date_textview);

        }
    }
}
