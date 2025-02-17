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

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    public List<QRItemDTO> getScannedQRItems(Context context) {
        return DataManager.getInstance(context).getScannedQRItems();
    }

    public void insertQRItem(QRItemDTO qrItemDTO,Context context) {
        DataManager.getInstance(context).insertQRItem(qrItemDTO)
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
