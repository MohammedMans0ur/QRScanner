package com.ebe.qrscanner.ui.main.presenter;

import android.content.Context;

import com.ebe.qrscanner.model.DataManager;
import com.ebe.qrscanner.model.data.dto.QRItemDTO;
import com.ebe.qrscanner.ui.main.view.MainView;

import java.util.List;


import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter {
    private MainView mainView;
    private DataManager dataManager;

    public MainPresenter(MainView mainView, DataManager dataManager) {
        this.mainView = mainView;
        this.dataManager = dataManager;
    }



    public List<QRItemDTO> getScannedQRItems() {
        return dataManager.getScannedQRItems();
    }

    public void insertQRItem(QRItemDTO qrItemDTO) {
        dataManager.insertQRItem(qrItemDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(Long itemDTO) {
                        mainView.onInsertQRITem(itemDTO);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Insertion failed
                        mainView.onSqlError(e);
                    }
                });
    }

}
