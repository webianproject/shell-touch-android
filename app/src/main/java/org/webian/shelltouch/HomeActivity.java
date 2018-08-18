package org.webian.shelltouch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoRuntimeSettings;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Webian Shell Touch.
 */
public class HomeActivity extends Activity {

    private ShellDatabase database;
    private View mContentView;
    private HomeScreenWindow homeScreenWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        database = new ShellDatabase(getApplicationContext());
        ArrayList apps = database.getApps();
        System.out.println("Apps: " + apps);
        mContentView = findViewById(R.id.home_content);
        homeScreenWindow = (HomeScreenWindow) getFragmentManager().findFragmentById(R.id.homescreen_window);
    }

    /**
     * Navigate to the home page.
     *
     * @param view
     */
    public void home(View view) {
      homeScreenWindow.goHome();
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
