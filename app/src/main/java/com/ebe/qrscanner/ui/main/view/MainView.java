package com.ebe.qrscanner.ui.main.view;


public interface MainView {
    void onSqlError(Throwable e);

    void onInsertQRITem(Long qrItemDTO);
}
