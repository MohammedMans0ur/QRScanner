package com.ebe.qrscanner.ui.scanneditemdetails.presenter;

import android.content.Context;

import com.ebe.qrscanner.model.DataManager;
import com.ebe.qrscanner.model.data.dto.QRItemDTO;

public class ScannedItemDetailsPresenter {
    private DataManager dataManager;


    public ScannedItemDetailsPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public QRItemDTO getQRItem(Long id) {
        return dataManager.getQRItem(id);
    }

    public void setFavorite(Long qrItemId, boolean favorite, Context context) {
        dataManager.setFavorite(qrItemId, favorite);
    }
}
