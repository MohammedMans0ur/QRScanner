package com.ebe.qrscanner.ui.scanneditemdetails.presenter;

import com.ebe.qrscanner.model.DataManager;
import com.ebe.qrscanner.model.data.dto.QRItemDTO;

public class ScannedItemDetailsPresenter {
    public ScannedItemDetailsPresenter() {
    }

    public QRItemDTO getQRItem(Long id) {
        return DataManager.getInstance().getQRItem(id);
    }

    public void setFavorite(Long qrItemId, boolean favorite) {
        DataManager.getInstance().setFavorite(qrItemId, favorite);
    }
}
