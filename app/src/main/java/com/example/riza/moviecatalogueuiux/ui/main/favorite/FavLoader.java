package com.example.riza.moviecatalogueuiux.ui.main.favorite;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import static com.example.riza.moviecatalogueuiux.data.db.DBContract.CONTENT_URI;

/**
 * Created by riza on 13/09/18.
 */
public class FavLoader extends AsyncTaskLoader<Cursor> {

    private Cursor mData;
    private boolean mHasResult = false;

    public FavLoader(final Context context) {
        super(context);

        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final Cursor data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    @Override
    public Cursor loadInBackground() {
        return getContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
    }

    protected void onReleaseResources(Cursor data) {
        if (data != null)
            data.close();
    }

}

