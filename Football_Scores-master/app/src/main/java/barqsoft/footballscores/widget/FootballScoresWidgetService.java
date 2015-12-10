package barqsoft.footballscores.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by matthiasko on 10/27/15.
 */
public class FootballScoresWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new FootballScoresWidgetView(this.getApplicationContext(), intent));
    }
}
