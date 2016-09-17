package com.exercises.scott.minesweeper;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.exercises.scott.minesweeper.util.ActivityUtils;

public class MinesweeperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minesweeper_act);

        MinesweeperFragment fragment = MinesweeperFragment.newInstance();
        new MinesweeperPresenter(fragment);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                fragment, R.id.contentView);

    }
}
