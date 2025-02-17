package com.ebe.qrscanner.ui.favorites.presenter;

import android.content.Context;

import com.ebe.qrscanner.model.DataManager;
import com.ebe.qrscanner.model.data.dto.QRItemDTO;

import java.util.List;

public class FavoritesPresenter {
    public List<QRItemDTO> getFavoriteQRItems(Context context) {
        return DataManager.getInstance(context).getFavoriteQRItems();
    }
}
