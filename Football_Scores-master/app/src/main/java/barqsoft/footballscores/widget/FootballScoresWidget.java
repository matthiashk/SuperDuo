package barqsoft.footballscores.widget;

/**
 * Created by matthiasko on 10/27/15.
 */

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.RemoteViews;

import barqsoft.footballscores.R;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;

public class FootballScoresWidget extends AppWidgetProvider {

    public static final String CLICK_ACTION = "barqsoft.footballscores.CLICK_ACTION";
    public static final String EXTRA_ITEM = "barqsoft.footballscores.EXTRA_ITEM";

    @Override
    public void onReceive(Context context, Intent intent) {

        // update ids in extras here?

        // launch app if a row on the widget was clicked
        if (intent.getAction().equals(CLICK_ACTION)) {

            PackageManager packageManager = context.getPackageManager();

            try {

                String packageName = "barqsoft.footballscores";
                Intent launchIntent = packageManager.getLaunchIntentForPackage(packageName);
                context.startActivity(launchIntent);
            }

            catch (Exception e1) {}
        }

        super.onReceive(context, intent);
    }

    /* called when the widget configure screen launches */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int mAppWidgetId : appWidgetIds) {

            Intent intent = new Intent(context, FootballScoresWidgetView.class);

            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_main);

            remoteViews.setRemoteAdapter(R.id.widgetListView, intent);

            Intent clickIntent = new Intent(context, FootballScoresWidget.class);

            clickIntent.setAction(FootballScoresWidget.CLICK_ACTION);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, mAppWidgetId, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews.setPendingIntentTemplate(R.id.widgetListView, pendingIntent);

            intent.putExtra(EXTRA_APPWIDGET_ID, mAppWidgetId);

            appWidgetManager.updateAppWidget(mAppWidgetId, remoteViews);

            context.startService(intent);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
