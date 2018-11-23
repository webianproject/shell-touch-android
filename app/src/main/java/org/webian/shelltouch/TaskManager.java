package org.webian.shelltouch;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Task Manager.
 *
 * Manages open app and browser windows.
 */
public class TaskManager extends Fragment {
    public TaskManager() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_manager, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
