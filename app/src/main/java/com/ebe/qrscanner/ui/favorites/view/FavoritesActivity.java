package com.ebe.qrscanner.ui.favorites.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ebe.qrscanner.R;
import com.ebe.qrscanner.databinding.ActivityFavoritesBinding;
import com.ebe.qrscanner.ui.base.view.BaseActivity;
import com.ebe.qrscanner.ui.favorites.presenter.FavoritesPresenter;
import com.ebe.qrscanner.ui.scanneditemdetails.view.ScannedItemDetailsActivity;
import com.ebe.qrscanner.utils.Constants;

public class FavoritesActivity extends BaseActivity {
    private final FavoritesPresenter favoritesPresenter = new FavoritesPresenter();
    private ActivityFavoritesBinding binding;
    private ScannedItemsAdapter scannedItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();


    }

    @Override
    protected void onResume() {
        super.onResume();
        initScannedQrAdapter();
        initUI();
    }

    private void initUI() {
        try {
            binding.swipeFavorites.setOnRefreshListener(() -> {
                scannedItemsAdapter.clearAdapter();
                initScannedQrAdapter();
                binding.swipeFavorites.setRefreshing(false);
            });
            if (scannedItemsAdapter.getItemCount() == 0) {
                binding.recyclerScannedQr.setVisibility(View.GONE);
                binding.txtNoFavoritesAvailable.setVisibility(View.VISIBLE);
            } else {
                binding.recyclerScannedQr.setVisibility(View.VISIBLE);
                binding.txtNoFavoritesAvailable.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }
    }

    private void initScannedQrAdapter() {
        scannedItemsAdapter = new ScannedItemsAdapter(favoritesPresenter.getFavoriteQRItems(), this);
        binding.recyclerScannedQr.setAdapter(scannedItemsAdapter);
        binding.recyclerScannedQr.setLayoutManager(new LinearLayoutManager(this));
        scannedItemsAdapter.setOnScannedItemClickListener(qrItemDTO -> {
            Intent intent = new Intent(FavoritesActivity.this, ScannedItemDetailsActivity.class);
            intent.putExtra(Constants.QR_ITEM_ID, qrItemDTO.getId());
            startActivity(intent);
        });

    }

    private void initToolbar() {
        setSupportActionBar(binding.lytToolbar.toolbar);
        getSupportActionBar().setTitle(getString(R.string.title_favorites));
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
}