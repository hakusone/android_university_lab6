package com.codepath.nytimes;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.codepath.nytimes.ui.books.BestSellerBooksFragment;
import com.codepath.nytimes.ui.home.HomeFragment;
import com.codepath.nytimes.ui.search.ArticleResultFragment;
import com.codepath.nytimes.ui.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private static String HOME_TAG = "home";
    private static String BOOKS_TAG = "books";
    private static String ARTICLES_TAG = "articles";
    private static String SETTINGS_TAG = "settings";
    private static String SELECTED_ITEM_ID_KEY = "selected_id";
    HomeFragment homeFragment;
    ArticleResultFragment articleResultFragment;
    BestSellerBooksFragment bestSellerBooksFragment;
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState != null) {
            Log.i("MainActivity", "Logging that it's applying to savedInstanceState");
        }

        homeFragment = (HomeFragment) fm.findFragmentByTag(HOME_TAG);
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
        }

        bestSellerBooksFragment = (BestSellerBooksFragment) fm.findFragmentByTag(BOOKS_TAG);
        if (bestSellerBooksFragment == null) {
            bestSellerBooksFragment = BestSellerBooksFragment.newInstance();
        }

        articleResultFragment = (ArticleResultFragment) fm.findFragmentByTag(ARTICLES_TAG);
        if (articleResultFragment == null) {
            articleResultFragment = ArticleResultFragment.newInstance();
        }

        settingsFragment = (SettingsFragment) fm.findFragmentByTag(SETTINGS_TAG);
        if (settingsFragment == null) {
            settingsFragment = SettingsFragment.newInstance();
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // handle navigation selection
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                ft.replace(R.id.flContainer, homeFragment, HOME_TAG);
                                break;
                            case R.id.action_books:
                                ft.replace(R.id.flContainer, bestSellerBooksFragment, BOOKS_TAG);
                                break;
                            case R.id.action_articles:
                                ft.replace(R.id.flContainer, articleResultFragment, ARTICLES_TAG);
                                break;
                            case R.id.action_settings:
                            default:
                                ft.replace(R.id.flContainer, settingsFragment, SETTINGS_TAG);
                                break;
                        }
                        ft.commit();
                        return true;
                    }
                });

        if (savedInstanceState != null) {
            int selected_bottom_item = savedInstanceState.getInt(SELECTED_ITEM_ID_KEY);
            bottomNavigationView.setSelectedItemId(selected_bottom_item);
        }
        else {
            bottomNavigationView.setSelectedItemId(R.id.action_home);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.i("MainActivity", "Logging that it's saving to savedInstanceState");
        outState.putInt(SELECTED_ITEM_ID_KEY, bottomNavigationView.getSelectedItemId());
        super.onSaveInstanceState(outState);
    }
}
