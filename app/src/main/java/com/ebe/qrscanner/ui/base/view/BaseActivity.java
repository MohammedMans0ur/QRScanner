package com.ebe.qrscanner.ui.base.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;

import com.ebe.qrscanner.model.data.source.preferences.SharedManager;
import com.ebe.qrscanner.utils.BetterActivityResult;
import com.ebe.qrscanner.utils.Constants;
import com.ebe.qrscanner.utils.LocalHelper;


public class BaseActivity extends AppCompatActivity {
    protected final BetterActivityResult<Intent, ActivityResult> activityLauncher =
            BetterActivityResult.registerActivityForResult(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        try {
            String lang = SharedManager.newInstance().getCashValue(Constants.LANGUAGE);
            if (lang == null || lang.isEmpty() || lang.contentEquals(Constants.ENGLISH)) {
                super.attachBaseContext(LocalHelper.onAttach(newBase, Constants.ENGLISH));
            } else if (lang.contentEquals(Constants.ARABIC)) {
                super.attachBaseContext(LocalHelper.onAttach(newBase, Constants.ARABIC));
            } else {
                super.attachBaseContext(LocalHelper.onAttach(newBase, Constants.ENGLISH));
            }
        } catch (Exception e) {
            super.attachBaseContext(LocalHelper.onAttach(newBase, Constants.ENGLISH));
        }

    }

}
