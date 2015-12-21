package barqsoft.footballscores.widget;

/**
 * Created by matthiasko on 10/27/15.
 */

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
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

                //System.out.println("FootballScoresWidgetView - onCreate *****");
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }

                /* get data from the database based on the date */

                final long identityToken = Binder.clearCallingIdentity();
                Uri footballScoresForDateUri = DatabaseContract.scores_table.buildScoreWithDate();

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date todayDate = new Date();
                String matchDate = df.format(todayDate);

                //System.out.println("footballScoresForDateUri.toString() = " + footballScoresForDateUri.toString());

                data = getContentResolver().query(footballScoresForDateUri,
                        FOOTBALL_COLUMNS,
                        null,
                        new String[] { matchDate },
                        DatabaseContract.scores_table.HOME_GOALS_COL + " ASC");
                Binder.restoreCallingIdentity(identityToken);

                //System.out.println("data.getCount() = " + data.getCount());

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

                String homeTeam = data.getString(FOOTBALL_HOME_COL);
                String awayTeam = data.getString(FOOTBALL_AWAY_COL);
                String gameTime = data.getString(FOOTBALL_TIME);

                //System.out.println("homeTeam = " + homeTeam);

                Bundle extras = new Bundle();
                extras.putInt(FootballScoresWidget.EXTRA_ITEM, position);
                Intent fillInIntent = new Intent();
                fillInIntent.putExtras(extras);

                // make widget launch app when the textviews are clicked
                views.setOnClickFillInIntent(R.id.home_textview, fillInIntent);
                views.setOnClickFillInIntent(R.id.time_textview, fillInIntent);
                views.setOnClickFillInIntent(R.id.away_textview, fillInIntent);

                views.setTextViewText(R.id.home_textview, homeTeam);
                views.setTextViewText(R.id.time_textview, gameTime);
                views.setTextViewText(R.id.away_textview, awayTeam);

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