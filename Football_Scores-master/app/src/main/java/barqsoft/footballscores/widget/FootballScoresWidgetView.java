package barqsoft.footballscores.widget;

/**
 * Created by matthiasko on 10/27/15.
 */

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import barqsoft.footballscores.R;

public class FootballScoresWidgetView implements RemoteViewsService.RemoteViewsFactory {

    ArrayList<String> arrayList = new ArrayList<>();

    private int appWidgetId;
    private Context mContext;

    public FootballScoresWidgetView(Context context, Intent intent) {

        mContext = context;

        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {

        // populate arraylist here...

        arrayList.add("Paris FC");
        arrayList.add("Getafe CF");
        arrayList.add("Ajaccio AC");
        arrayList.add("AJ Auxerre");










    }

    @Override
    public int getCount() {
        return(arrayList.size());
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.widget_row);

        /* setup rows...

           row.setTextViewText(android.R.id.text1, arrayList.get(position).getName());

        */

        row.setTextViewText(android.R.id.text1, arrayList.get(position));

        Bundle extras = new Bundle();
        extras.putInt(FootballScoresWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        // Make it possible to distinguish the individual on-click
        // action of a given item

        row.setOnClickFillInIntent(android.R.id.text1, fillInIntent);

        return(row);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public RemoteViews getLoadingView() {
        return(null);
    }

    @Override
    public int getViewTypeCount() {
        return(1);
    }

    @Override
    public long getItemId(int position) {
        return(position);
    }

    @Override
    public boolean hasStableIds() {
        return(true);
    }

    @Override
    public void onDataSetChanged() {

        // update data...
    }
}
