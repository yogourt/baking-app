package com.example.jagoda.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;


public class ListRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewFactory(this.getApplicationContext());
    }
}
