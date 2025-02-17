package com.ebe.qrscanner.model;

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

    private DataManager() {

        sharedManager = SharedManager.newInstance();
        databaseHelper = DatabaseHelper.getDatabaseHelper(App.mContext);
    }

    public static DataManager getInstance() {
        if (mInstance == null) {
            synchronized (DataManager.class) {

                mInstance = new DataManager();

            }
        } else if (mInstance.sharedManager == null || mInstance.databaseHelper == null) {
            mInstance = new DataManager();
        }

        return mInstance;
    }


    public QRItemDTO getQRItem(Long id) {
        return databaseHelper.metaDataDao().getQRItem(id);
    }

    public void setFavorite(Long qrItemId, boolean favorite) {
        databaseHelper.metaDataDao().setFavorite(qrItemId, favorite);
    }

    public List<QRItemDTO> getFavoriteQRItems() {
        return databaseHelper.metaDataDao().getFavoriteQRItems();
    }

    public List<QRItemDTO> getScannedQRItems() {
        return databaseHelper.metaDataDao().getAllItems();
    }

    public Single<Long> insertQRItem(QRItemDTO qrItemDTO) {
        return databaseHelper.metaDataDao().insertItem(qrItemDTO);
    }
}
