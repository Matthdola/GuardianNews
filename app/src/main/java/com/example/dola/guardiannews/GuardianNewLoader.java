package com.example.dola.guardiannews;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;

public class GuardianNewLoader  extends AsyncTaskLoader<List<GuardianNew>> {
    private String url;

    public GuardianNewLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<GuardianNew> loadInBackground() {
        try {
            List<GuardianNew> news = QueryUtils.fetchGuardianNewData(url);
            return news;
        } catch (Exception e){

        }
        return null;
    }
}
