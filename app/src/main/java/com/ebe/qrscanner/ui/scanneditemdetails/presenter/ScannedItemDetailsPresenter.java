package com.ebe.qrscanner.ui.scanneditemdetails.presenter;

import android.content.Context;

import com.ebe.qrscanner.model.DataManager;
import com.ebe.qrscanner.model.data.dto.QRItemDTO;

public class ScannedItemDetailsPresenter {
    public ScannedItemDetailsPresenter() {
    }

    public QRItemDTO getQRItem(Long id, Context context) {
        return DataManager.getInstance(context).getQRItem(id);
    }

    public void setFavorite(Long qrItemId, boolean favorite,Context context) {
        DataManager.getInstance(context).setFavorite(qrItemId, favorite);
    }
}
