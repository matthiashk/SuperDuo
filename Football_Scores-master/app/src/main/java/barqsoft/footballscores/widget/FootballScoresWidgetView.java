package barqsoft.footballscores.widget;

/**
 * Created by matthiasko on 10/27/15.
 */

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;

public class FootballScoresWidgetView extends RemoteViewsService {

    private static final String[] FOOTBALL_COLUMNS = {
            DatabaseContract.scores_table._ID,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.DATE_COL,
            DatabaseContract.scores_table.TIME_COL,
            DatabaseContract.scores_table.MATCH_DAY
    };

    static final int FOOTBALL_ID = 0;
    static final int FOOTBALL_HOME_COL = 1;
    static final int FOOTBALL_AWAY_COL = 2;
    static final int FOOTBALL_DATE = 3;
    static final int FOOTBALL_TIME = 4;
    static final int FOOTBALL_MATCH_DAY = 5;



    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {

                System.out.println("FootballScoresWidgetView - onCreate *****");
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission

                String matchDay = "15";

                final long identityToken = Binder.clearCallingIdentity();
                //String location = Utility.getPreferredLocation(DetailWidgetRemoteViewsService.this);
                Uri footballScoresForDateUri = DatabaseContract.scores_table.buildScoreWithDate();

                //String QUERY = "SELECT * FROM "+DatabaseContract.SCORES_TABLE;

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date todayDate = new Date();
                String matchDate = df.format(todayDate);



                System.out.println("footballScoresForDateUri.toString() = " + footballScoresForDateUri.toString());

                data = getContentResolver().query(footballScoresForDateUri,
                        FOOTBALL_COLUMNS,
                        null,
                        new String[] { matchDate },
                        DatabaseContract.scores_table.HOME_GOALS_COL + " ASC");
                Binder.restoreCallingIdentity(identityToken);

                System.out.println("data.getCount() = " + data.getCount());

                //System.out.println("data = " + data); // not null, means query is incorrect...

            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_row);
                /*
                int weatherId = data.getInt(INDEX_WEATHER_CONDITION_ID);
                int weatherArtResourceId = Utility.getIconResourceForWeatherCondition(weatherId);
                Bitmap weatherArtImage = null;
                if ( !Utility.usingLocalGraphics(DetailWidgetRemoteViewsService.this) ) {
                    String weatherArtResourceUrl = Utility.getArtUrlForWeatherCondition(
                            DetailWidgetRemoteViewsService.this, weatherId);
                    try {
                        weatherArtImage = Glide.with(DetailWidgetRemoteViewsService.this)
                                .load(weatherArtResourceUrl)
                                .asBitmap()
                                .error(weatherArtResourceId)
                                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                    } catch (InterruptedException | ExecutionException e) {
                        Log.e(LOG_TAG, "Error retrieving large icon from " + weatherArtResourceUrl, e);
                    }
                }*/

                /*
                String description = data.getString(INDEX_WEATHER_DESC);
                long dateInMillis = data.getLong(INDEX_WEATHER_DATE);
                String formattedDate = Utility.getFriendlyDayString(
                        DetailWidgetRemoteViewsService.this, dateInMillis, false);
                double maxTemp = data.getDouble(INDEX_WEATHER_MAX_TEMP);
                double minTemp = data.getDouble(INDEX_WEATHER_MIN_TEMP);
                String formattedMaxTemperature =
                        Utility.formatTemperature(DetailWidgetRemoteViewsService.this, maxTemp);
                String formattedMinTemperature =
                        Utility.formatTemperature(DetailWidgetRemoteViewsService.this, minTemp);
                if (weatherArtImage != null) {
                    views.setImageViewBitmap(R.id.widget_icon, weatherArtImage);
                } else {
                    views.setImageViewResource(R.id.widget_icon, weatherArtResourceId);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    setRemoteContentDescription(views, description);
                }
                */

                String homeTeam = data.getString(FOOTBALL_HOME_COL);
                String awayTeam = data.getString(FOOTBALL_AWAY_COL);
                String gameTime = data.getString(FOOTBALL_TIME);

                System.out.println("homeTeam = " + homeTeam);

                /*
                views.setTextViewText(R.id.widget_date, formattedDate);
                views.setTextViewText(R.id.widget_description, description);
                views.setTextViewText(R.id.widget_high_temperature, formattedMaxTemperature);
                views.setTextViewText(R.id.widget_low_temperature, formattedMinTemperature);

                final Intent fillInIntent = new Intent();
                String locationSetting =
                        Utility.getPreferredLocation(DetailWidgetRemoteViewsService.this);
                Uri weatherUri = WeatherContract.WeatherEntry.buildWeatherLocationWithDate(
                        locationSetting,
                        dateInMillis);
                fillInIntent.setData(weatherUri);
                views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
                */

                views.setTextViewText(R.id.text1, homeTeam);
                views.setTextViewText(R.id.text2, gameTime);
                views.setTextViewText(R.id.text3, awayTeam);


                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_row);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(FOOTBALL_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}