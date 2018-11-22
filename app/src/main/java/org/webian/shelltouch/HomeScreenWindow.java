package org.webian.shelltouch;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoRuntimeSettings;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;

/**
 * Home Screen Window.
 *
 * Window in which to load the home page.
 */
public class HomeScreenWindow extends Fragment {

    private GeckoView geckoview;
    private GeckoSession session;
    private static GeckoRuntime runtime;
    private static final String HOME_PAGE = "https://duckduckgo.com";

    public HomeScreenWindow() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen_window, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        // Find the GeckoView in our layout
        geckoview = (GeckoView) view.findViewById(R.id.geckoview);

        // Create a runtime settings builder
        final GeckoRuntimeSettings.Builder runtimeSettingsBuilder =
                new GeckoRuntimeSettings.Builder();

        // Manually set display density for Raspberry Pi display
        runtimeSettingsBuilder.displayDensityOverride(1);

        session = new GeckoSession();
        runtime = GeckoRuntime.create(getActivity(), runtimeSettingsBuilder.build());
        session.open(runtime);
        geckoview.setSession(session);
        session.loadUri(HOME_PAGE);
    }

    /**
     * Navigate to the home page.
     */
    public void goHome() {
        session.loadUri(HOME_PAGE);
    }

    /**
     * Navigate back in session history.
     */
    public void goBack() {
        session.goBack();
    }

    /**
     * Reload the current page.
     */
    public void reload() {
        session.reload();
    }


}