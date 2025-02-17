package com.ebe.qrscanner.ui.favorites.presenter;

import com.ebe.qrscanner.model.DataManager;
import com.ebe.qrscanner.model.data.dto.QRItemDTO;

import java.util.List;

public class FavoritesPresenter {
    public List<QRItemDTO> getFavoriteQRItems() {
        return DataManager.getInstance().getFavoriteQRItems();
    }
}
