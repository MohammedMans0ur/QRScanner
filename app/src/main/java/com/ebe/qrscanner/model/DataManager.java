package com.ebe.qrscanner.model;

import android.content.Context;

import com.ebe.qrscanner.App;
import com.ebe.qrscanner.model.data.dto.QRItemDTO;
import com.ebe.qrscanner.model.data.source.database.DatabaseHelper;
import com.ebe.qrscanner.model.data.source.preferences.SharedManager;

import java.util.List;

import io.reactivex.Single;

public class DataManager {
    private static DataManager mInstance = null;
    private SharedManager sharedManager;
    private DatabaseHelper databaseHelper;
    private Context context;

    private DataManager(Context context) {
        this.context = context;
        sharedManager = SharedManager.newInstance();
        databaseHelper = DatabaseHelper.getDatabaseHelper(App.mContext);
    }

    public static DataManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DataManager.class) {

                mInstance = new DataManager(context);

            }
        } else if (mInstance.sharedManager == null || mInstance.databaseHelper == null) {
            mInstance = new DataManager(context);
        }

        return mInstance;
    }

    public static void setInstance(DataManager dataManager) {
        mInstance= dataManager;
    }


    public QRItemDTO getQRItem(Long id) {
        return databaseHelper.qrItemDao().getQRItem(id);
    }

    public void setFavorite(Long qrItemId, boolean favorite) {
        databaseHelper.qrItemDao().setFavorite(qrItemId, favorite);
    }

    public List<QRItemDTO> getFavoriteQRItems() {
        return databaseHelper.qrItemDao().getFavoriteQRItems();
    }

    public List<QRItemDTO> getScannedQRItems() {
        return databaseHelper.qrItemDao().getAllItems();
    }

    public Single<Long> insertQRItem(QRItemDTO qrItemDTO) {
        return databaseHelper.qrItemDao().insertItem(qrItemDTO);
    }

    public SharedManager getSharedManager() {
        return sharedManager;
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public Context getContext() {
        return context;
    }
}
