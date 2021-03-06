package com.louiswheeleriv.fithub.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.louiswheeleriv.fithub.R;
import com.louiswheeleriv.fithub.fragments.*;
import com.louiswheeleriv.fithub.objects.BodyExercise;
import com.louiswheeleriv.fithub.objects.CardioExercise;
import com.louiswheeleriv.fithub.objects.WeightExercise;
import com.louiswheeleriv.fithub.util.DatabaseHandler;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks,
                                                               HomeFragment.OnFragmentInteractionListener,
                                                               WorkoutFragment.OnFragmentInteractionListener,
                                                               CreateExerciseFragment.OnFragmentInteractionListener,
                                                               ExerciseDetailFragment.OnFragmentInteractionListener,
                                                               DatePickerFragment.DateSelectedListener,
                                                               AddWeightInstanceFragment.WeightInstanceCreatedListener,
                                                               AddCardioInstanceFragment.CardioInstanceCreatedListener,
                                                               AddBodyInstanceFragment.BodyInstanceCreatedListener,
                                                               AnalysisFragment.OnFragmentInteractionListener,
                                                               GoalsFragment.OnFragmentInteractionListener {

    // Fragment managing the behaviors, interactions and presentation of the navigation drawer.
    private NavigationDrawerFragment mNavigationDrawerFragment;

    // Used to store the last screen title. For use in {@link #restoreActionBar()}.
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new WorkoutFragment();
                break;
            case 2:
                fragment = new AnalysisFragment();
                break;
            case 3:
                fragment = new GoalsFragment();
                break;
            default:
                fragment = new HomeFragment();
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_home);
                break;
            case 2:
                mTitle = getString(R.string.title_workout);
                break;
            case 3:
                mTitle = getString(R.string.title_analysis);
                break;
            case 4:
                mTitle = getString(R.string.title_goals);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(Uri uri) {}

    public void onDateSelected(Date dateSelected, int exerciseId) {
        openExerciseDetailFragment(dateSelected, exerciseId);
    }

    public void onWeightInstanceCreated(WeightExercise we, Date dateSelected, int exerciseId) {
        openExerciseDetailFragment(dateSelected, exerciseId);
    }

    public void onCardioInstanceCreated(CardioExercise ce, Date dateSelected, int exerciseId) {
        openExerciseDetailFragment(dateSelected, exerciseId);
    }

    public void onBodyInstanceCreated(BodyExercise be, Date dateSelected, int exerciseId) {
        openExerciseDetailFragment(dateSelected, exerciseId);
    }

    public void openExerciseDetailFragment(Date dateSelected, int exerciseId) {
        Fragment detailFragment = new ExerciseDetailFragment();
        Bundle bundle = new Bundle();

        bundle.putInt("exerciseId", exerciseId);
        bundle.putSerializable("dateSelected", dateSelected);
        detailFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, detailFragment).commit();
    }

}
