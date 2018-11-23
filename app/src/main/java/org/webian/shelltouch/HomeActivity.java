package org.webian.shelltouch;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;


/**
 * Webian Shell Touch.
 */
public class HomeActivity extends Activity {

    private ShellDatabase database;
    private View mContentView;
    private HomeScreenWindow homeScreenWindow;
    private TaskManager taskManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        database = new ShellDatabase(getApplicationContext());
        ArrayList apps = database.getApps();
        System.out.println("Apps: " + apps);
        mContentView = findViewById(R.id.home_content);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Create home screen window.
        homeScreenWindow = new HomeScreenWindow();
        transaction.add(R.id.windows, homeScreenWindow);

        // Create and hide task manager.
        taskManager = new TaskManager();
        transaction.add(R.id.windows, taskManager);
        transaction.hide(taskManager);

        transaction.commit();


    }

    /**
     * Show the home screen.
     *
     * @param view
     */
    public void home(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(taskManager);
        transaction.show(homeScreenWindow);
        transaction.commit();
    }

    /**
     * Navigate back in session history.
     *
     * @param view
     */
    public void back(View view) {
        homeScreenWindow.goBack();
    }

    /**
     * Show the task manager.
     *
     * @param view
     */
    public void showTaskManager(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(homeScreenWindow);
        transaction.show(taskManager);
        transaction.commit();
    }

    /**
     * Reload the current page.
     *
     * @param view
     */
    public void reload(View view) {
        homeScreenWindow.reload();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mContentView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
}
