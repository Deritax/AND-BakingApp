package tk.itarusoft.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

public class BakingAppWidget extends AppWidgetProvider {

    static String widgetTitle;
    static String widgetList;

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        if (widgetList == null){
            widgetTitle = context.getString(R.string.bakingwidget);
            widgetList = context.getString(R.string.useApp);
        }

        views.setTextViewText(R.id.appwidget_title, widgetTitle);
        views.setTextViewText(R.id.appwidget_list, widgetList);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

