package com.ebe.qrscanner.model;

import com.ebe.qrscanner.model.data.dto.QRItemDTO;
import com.ebe.qrscanner.model.data.source.database.DatabaseHelper;

import java.util.List;

import io.reactivex.Single;

public class DataManager {
    private DatabaseHelper databaseHelper;

    public DataManager(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
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

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }
}