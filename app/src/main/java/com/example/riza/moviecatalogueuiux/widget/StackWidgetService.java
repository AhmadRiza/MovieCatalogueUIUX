package com.example.riza.moviecatalogueuiux.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.riza.moviecatalogueuiux.R;
import com.example.riza.moviecatalogueuiux.data.model.Movie;
import com.example.riza.moviecatalogueuiux.utils.AppUtils;

import java.util.concurrent.ExecutionException;

import static com.example.riza.moviecatalogueuiux.data.db.DBContract.CONTENT_URI;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }


    private class StackRemoteViewsFactory implements
            RemoteViewsService.RemoteViewsFactory {

        private Cursor data;
        private Context mContext;
        private int mAppWidgetId;

        public StackRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        public void onCreate() {
            data = mContext.getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        public void onDataSetChanged() {
            final long identityToken = Binder.clearCallingIdentity();

            data = mContext.getContentResolver().query(CONTENT_URI,null,null,null,null);

            Binder.restoreCallingIdentity(identityToken);
        }

        @Override
        public void onDestroy() {
            if(data!=null)data.close();
        }

        public Movie getItemAt(int position){
            if (!data.moveToPosition(position)) {
                throw new IllegalStateException("Position invalid");
            }
            return new Movie(data);
        }

        @Override
        public int getCount() {
            if (data == null) return 0;
            return data.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

            Movie movie = getItemAt(position);
            Bitmap bmp = null;
            try {

                bmp = Glide.with(mContext).asBitmap()
                        .load("http://image.tmdb.org/t/p/w185" + movie.getImgLandscape())
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

            }catch (InterruptedException | ExecutionException e){
                Log.d("Widget Load Error","error");
            }

            rv.setImageViewBitmap(R.id.imageView, bmp);
            rv.setTextViewText(R.id.textView, AppUtils.formatDate(movie.getDate()));

            Bundle extras = new Bundle();
            extras.putInt(MovieStackWidget.EXTRA_ITEM, position);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);

            rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

}

