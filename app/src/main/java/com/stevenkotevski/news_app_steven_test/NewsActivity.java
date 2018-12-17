package com.stevenkotevski.news_app_steven_test;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import com.stevenkotevski.news_app_steven_test.Model.RSSObject;
import com.stevenkotevski.news_app_steven_test.Adapter.FeedAdapter;
import com.stevenkotevski.news_app_steven_test.Common.HTTPDataHandler;

/**
 * This app essentially takes the RSS Feed from the NY Times Technology section, parses it into a Json
 * through the use of the rss2json website, and then uses that json data to fetch things like article names,
 * article descriptions, and publication dates. With all of that information, the app will display all of that
 * into a recycler view. When an article item is clicked, it fetches the URL of the article and opens it in a WebView.
 * The articles are sorted based on what's trending at the current time, and shows recent articles.
 *
 *
 * After many failed attempts of not being able to use a News API and Json to display articles in a
 * RecyclerView, I decided to look up examples / tutorials of applications that show a news feed.
 * In the end, I found some examples that used RSS feeds, and I decided to use that instead because
 * it seemed more straightforward, and the NY Times used an RSS feed and they are a good news source.
 *
 *
 * Due to the time constraint, this app is relatively simple and only shows a stream of articles under the
 * Technology category. If I were to work on this further, I would make it so that it you can search specific articles
 * or have a dropdown menu / buttons that allow you to change the category.
 * ~ Steven
 */

public class NewsActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    RSSObject rssObject;

    //RSS link
    private final String RSS_link="http://rss.nytimes.com/services/xml/rss/nyt/Technology.xml";
    private final String RSS_to_Json_API = "https://api.rss2json.com/v1/api.json?rss_url=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity_main);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadRSS();
    }

    // I used AsyncTask because its very useful and efficient for what I want it to do (load articles when clicked)
    private void loadRSS() {
        AsyncTask<String,String,String> loadRSSAsync = new AsyncTask<String, String, String>() {

            ProgressDialog mDialog = new ProgressDialog(NewsActivity.this);

            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Please wait...");
                mDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(params[0]);
                return  result;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                rssObject = new Gson().fromJson(s,RSSObject.class);
                FeedAdapter adapter = new FeedAdapter(rssObject,getBaseContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };

        StringBuilder url_get_data = new StringBuilder(RSS_to_Json_API);
        url_get_data.append(RSS_link);
        loadRSSAsync.execute(url_get_data.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_refresh)
            loadRSS();
        return true;
    }
}
