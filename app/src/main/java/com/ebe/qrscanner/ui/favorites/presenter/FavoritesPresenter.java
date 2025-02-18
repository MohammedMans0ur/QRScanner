package com.ebe.qrscanner.ui.favorites.presenter;

import com.ebe.qrscanner.model.DataManager;
import com.ebe.qrscanner.model.data.dto.QRItemDTO;

import java.util.List;

public class FavoritesPresenter {
    private DataManager dataManager;

    public FavoritesPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<QRItemDTO> getFavoriteQRItems() {
        return dataManager.getFavoriteQRItems();
    }
}