package com.ebe.qrscanner.ui.main.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ebe.qrscanner.R;
import com.ebe.qrscanner.databinding.ActivityMainBinding;
import com.ebe.qrscanner.model.data.dto.QRItemDTO;
import com.ebe.qrscanner.ui.base.view.BaseActivity;
import com.ebe.qrscanner.ui.favorites.view.FavoritesActivity;
import com.ebe.qrscanner.ui.favorites.view.ScannedItemsAdapter;
import com.ebe.qrscanner.ui.main.presenter.MainPresenter;
import com.ebe.qrscanner.ui.scanneditemdetails.view.ScannedItemDetailsActivity;
import com.ebe.qrscanner.utils.AppUtils;
import com.ebe.qrscanner.utils.Constants;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends BaseActivity implements MainView {
    private final MainPresenter mainPresenter = new MainPresenter(this);
    private ActivityMainBinding binding;
    private ScannedItemsAdapter scannedItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        if (!isCameraPermissionAllowed())
            requestCameraPermissionForFirstTime();


    }

    @Override
    protected void onResume() {
        super.onResume();
        initUI();
    }

    private void initUI() {
        try {
            binding.scanButton.setOnClickListener(v -> {
                if (isCameraPermissionAllowed()) {
                    scanQRCode();
                } else {
                    requestCameraPermission();
                }
            });
            initScannedQrAdapter();

            if (scannedItemsAdapter.getItemCount() == 0) {
                binding.recyclerHistoryScannedQr.setVisibility(View.GONE);
                binding.txtHistoryTitle.setVisibility(View.GONE);
            } else {
                binding.recyclerHistoryScannedQr.setVisibility(View.VISIBLE);
                binding.txtHistoryTitle.setVisibility(View.VISIBLE);

            }
        } catch (Exception e) {
        }
    }

    private void initToolbar() {
        setSupportActionBar(binding.lytToolbar.toolbar);
        getSupportActionBar().setTitle(getString(R.string.title_scan_qr));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.title_favorites) {
            startActivity(new Intent(this, FavoritesActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    boolean isCameraPermissionAllowed() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }


    private void requestCameraPermissionForFirstTime() {
        if (shouldShowRequestPermissionRationale(
                Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.title_permission_denied))
                    .setMessage(getResources().getString(R.string.msg_camera_permission_denied))
                    .setPositiveButton(getResources().getString(R.string.title_ok),
                            (dialog, which) -> requestPermissions(
                                    new String[]{Manifest.permission.CAMERA},
                                    Constants.CAMERA_PERMISSION_REQUEST_CODE))
                    .setNegativeButton(getResources().getString(R.string.title_cancel), null)
                    .create()
                    .show();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, Constants.CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    private void requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(
                Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.title_permission_denied))
                    .setMessage(getResources().getString(R.string.msg_camera_permission_denied))
                    .setPositiveButton(getResources().getString(R.string.title_grant),
                            (dialog, which) -> requestPermissions(
                                    new String[]{Manifest.permission.CAMERA},
                                    Constants.CAMERA_PERMISSION_REQUEST_CODE))
                    .setNegativeButton(getResources().getString(R.string.title_cancel), null)
                    .create()
                    .show();
        } else {
            openAppSettings();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                scanQRCode();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.title_permission_denied))
                        .setMessage(getResources().getString(R.string.msg_camera_permission_denied))
                        .setPositiveButton(getString(R.string.title_ok), (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
            }
        }
    }

    private void openAppSettings() {

        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.title_permission_denied))
                .setMessage(getResources().getString(R.string.msg_camera_permission_denied))
                .setPositiveButton(getString(R.string.title_grant), (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton(getResources().getString(R.string.title_cancel), null)
                .create()
                .show();

    }

    private void scanQRCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a QR Code");
        options.setBeepEnabled(true);
        options.setBarcodeImageEnabled(true);
        qrScannerLauncher.launch(options);
    }

    private void initScannedQrAdapter() {
        scannedItemsAdapter = new ScannedItemsAdapter(mainPresenter.getScannedQRItems(), this);
        binding.recyclerHistoryScannedQr.setAdapter(scannedItemsAdapter);
        binding.recyclerHistoryScannedQr.setLayoutManager(new LinearLayoutManager(this));
        scannedItemsAdapter.setOnScannedItemClickListener(qrItemDTO -> {
            Intent intent = new Intent(MainActivity.this, ScannedItemDetailsActivity.class);
            intent.putExtra(Constants.QR_ITEM_ID, qrItemDTO.getId());
            startActivity(intent);
        });

    }

    private final androidx.activity.result.ActivityResultLauncher<ScanOptions> qrScannerLauncher = registerForActivityResult(
            new ScanContract(), result -> {
                if (result.getContents() != null) {
                    QRItemDTO QRItemDTO = new QRItemDTO(result.getContents(), AppUtils.getCurrentDateTime("dd/MM/yy HH:mm a"), result.getFormatName(), false, result.getRawBytes());
                    mainPresenter.insertQRItem(QRItemDTO);

                }
            }
    );

    @Override
    public void onSqlError(Throwable e) {

    }

    @Override
    public void onInsertQRITem(Long qrItemDTO) {
        Intent intent = new Intent(MainActivity.this, ScannedItemDetailsActivity.class);
        intent.putExtra(Constants.QR_ITEM_ID, qrItemDTO);
        startActivity(intent);
    }
}