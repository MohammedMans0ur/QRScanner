package com.ebe.qrscanner.ui.scanneditemdetails.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;

import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.ebe.qrscanner.R;
import com.ebe.qrscanner.databinding.ActivityScannedItemDetailsBinding;
import com.ebe.qrscanner.model.DataManager;
import com.ebe.qrscanner.model.data.dto.QRItemDTO;
import com.ebe.qrscanner.model.data.source.database.DatabaseHelper;
import com.ebe.qrscanner.ui.base.view.BaseActivity;
import com.ebe.qrscanner.ui.scanneditemdetails.presenter.ScannedItemDetailsPresenter;
import com.ebe.qrscanner.utils.AppUtils;
import com.ebe.qrscanner.utils.Constants;

import java.nio.ByteBuffer;

public class ScannedItemDetailsActivity extends BaseActivity {
    private ScannedItemDetailsPresenter scannedItemDetailsPresenter;
    private ActivityScannedItemDetailsBinding binding;
    private Long qrItemId;
    private QRItemDTO qrItemDTO;
    private DataManager dataManager;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScannedItemDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        databaseHelper = DatabaseHelper.getDatabaseHelper(this);
        dataManager = new DataManager(databaseHelper);
        scannedItemDetailsPresenter = new ScannedItemDetailsPresenter(dataManager);
        initUI();
    }

    private void initUI() {
        try {
            binding.txtQrContent.setOnClickListener(v -> {
                if (AppUtils.validateWithRegex(qrItemDTO.getContent(), Constants.URL_REGEX)) {
                    openContentUrl();
                }
            });
            binding.txtOpenQrLink.setOnClickListener(v -> openContentUrl());
            binding.imgFavorite.setOnClickListener(v -> {
                if (qrItemDTO.getFavorite()) {
                    binding.imgFavorite.setImageResource(R.drawable.baseline_star_border_24);
                    qrItemDTO.setFavorite(false);
                } else {
                    binding.imgFavorite.setImageResource(R.drawable.baseline_star_24);
                    qrItemDTO.setFavorite(true);
                }
                scannedItemDetailsPresenter.setFavorite(qrItemId, qrItemDTO.getFavorite(), getApplicationContext());
            });
            qrItemId = getIntent().getLongExtra(Constants.QR_ITEM_ID, 0);
            if (qrItemId != 0) {
                qrItemDTO = scannedItemDetailsPresenter.getQRItem(qrItemId);
                if (qrItemDTO != null) {
                    binding.txtQrContent.setText(qrItemDTO.getContent());
                    binding.txtQrType.setText(qrItemDTO.getType());
                    binding.txtQrDate.setText(qrItemDTO.getDate());
                    binding.imgFavorite.setImageResource(qrItemDTO.getFavorite() ? R.drawable.baseline_star_24 : R.drawable.baseline_star_border_24);
                    if (AppUtils.validateWithRegex(qrItemDTO.getContent(), Constants.URL_REGEX)) {
                        binding.txtOpenQrLink.setVisibility(View.VISIBLE);
                        binding.txtQrContent.setTextColor(getColor(R.color.colorPrimary));
                        setQRLinkUnderline();
                    } else {
                        binding.txtOpenQrLink.setVisibility(View.GONE);
                    }

                    // Bitmap = null will check it later
               /*     Bitmap bmp = BitmapFactory.decodeByteArray(qrItemDTO.getImage(), 0, qrItemDTO.getImage().length);
                    binding.imgItemQrImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200, 200, false));
               */
                }
            } else {
                Toast.makeText(this, "Failed to Load Data", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
        }
    }

    private void openContentUrl() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(android.net.Uri.parse(qrItemDTO.getContent()));
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    private void initToolbar() {
        setSupportActionBar(binding.lytToolbar.toolbar);
        getSupportActionBar().setTitle(getString(R.string.title_details));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setQRLinkUnderline() {
        try {
            Spannable span = new SpannableString(qrItemDTO.getContent());
            span.setSpan(new UnderlineSpan(), 0, qrItemDTO.getContent().length(), 0);

            binding.txtQrContent.setText(span);


        } catch (Exception e) {
        }
    }
}