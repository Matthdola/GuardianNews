package com.example.dola.guardiannews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<GuardianNew>> {
    private static final int GUARDIAN_LOADER_ID = 1;
    public static final String LOG_TAG = "Gardian News Feed";
    public static final String SPORT = "Sport";
    public static final String NEWS = "News";
    public static final String LIFESTYLE = "Lifestyle";
    public static final String ARTS = "Arts";

    private static final String API_URL =
          "https://content.guardianapis.com/search?";

    private static final String API_KEY =
            "api-key=85720023-f4bd-4c08-ac14-9be02908ed0c";

    private GuardianNewAdapter adapter;

    private TextView mEmptyStateTextView;
    private LinearLayout indicatorContainer;

    NetworkInfo networkInfo;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = String.format("%s%s", API_URL, API_KEY);
        ListView guardianItemsListView = findViewById(R.id.list);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        indicatorContainer = findViewById(R.id.indicator_container);

        adapter = new GuardianNewAdapter(this, new ArrayList<GuardianNew>());
        guardianItemsListView.setAdapter(adapter);
        guardianItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GuardianNew guardianItem = adapter.getItem(position);

                Uri guardianUrl = Uri.parse(guardianItem.getWebUrl());
                Intent webSiteIntent = new Intent(Intent.ACTION_VIEW, guardianUrl);
                startActivity(webSiteIntent);
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = connMgr.getActiveNetworkInfo();

        //If there us a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()){
            // Get a reference to the LoaderManager, in order to interact with loaders
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the in ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter(which is valid
            // because this activity implements the LoaderCallbacks interface)
            loaderManager.initLoader(GUARDIAN_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                if (networkInfo != null && networkInfo.isConnected()){
                    url = String.format("%s%s", API_URL, API_KEY);
                    // Get a reference to the LoaderManager, in order to interact with loaders
                    LoaderManager loaderManager = getLoaderManager();
                    // Initialize the loader. Pass in the in ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter(which is valid
                    // because this activity implements the LoaderCallbacks interface)
                    loaderManager.restartLoader(GUARDIAN_LOADER_ID, null, this);
                }
                return true;
            case R.id.action_business:
                if (networkInfo != null && networkInfo.isConnected()){
                    url = String.format("%s%s%s", API_URL, "section=business&", API_KEY);
                    // Get a reference to the LoaderManager, in order to interact with loaders
                    LoaderManager loaderManager = getLoaderManager();
                    // Initialize the loader. Pass in the in ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter(which is valid
                    // because this activity implements the LoaderCallbacks interface)
                    loaderManager.restartLoader(GUARDIAN_LOADER_ID, null, this);
                }
                return true;
            case R.id.action_sport:
                if (networkInfo != null && networkInfo.isConnected()){
                    url = String.format("%s%s%s", API_URL, "section=sport&", API_KEY);
                    // Get a reference to the LoaderManager, in order to interact with loaders
                    LoaderManager loaderManager = getLoaderManager();
                    // Initialize the loader. Pass in the in ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter(which is valid
                    // because this activity implements the LoaderCallbacks interface)
                    loaderManager.restartLoader(GUARDIAN_LOADER_ID, null, this);
                }
                return true;
            case R.id.action_news:
                if (networkInfo != null && networkInfo.isConnected()){
                    url = String.format("%s%s%s", API_URL, "section=news&", API_KEY);
                    // Get a reference to the LoaderManager, in order to interact with loaders
                    LoaderManager loaderManager = getLoaderManager();
                    // Initialize the loader. Pass in the in ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter(which is valid
                    // because this activity implements the LoaderCallbacks interface)
                    loaderManager.restartLoader(GUARDIAN_LOADER_ID, null, this);

                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<GuardianNew>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(url);
        Uri.Builder builder = baseUri.buildUpon();

        return new GuardianNewLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<GuardianNew>> loader, List<GuardianNew> data) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        adapter.clear();

        if (data != null && !data.isEmpty()){
            adapter.addAll(data);
            mEmptyStateTextView.setVisibility(View.GONE);
            indicatorContainer.setVisibility(View.GONE);
        } else {
            // Set empty with no connection error message
            mEmptyStateTextView.setText(R.string.no_guardian_items);
        }
    }


    @Override
    public void onLoaderReset(Loader<List<GuardianNew>> loader) {
        adapter.clear();
    }


}
