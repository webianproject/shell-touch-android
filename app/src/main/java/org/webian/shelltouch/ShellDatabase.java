package org.webian.shelltouch;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.SavedRevision;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The Shell Database is used to persist apps, bookmarks, history and settings.
 */

public class ShellDatabase {

    private Database database;

    /**
     * Constructor.
     *
     * @param context Android application context.
     */
    public ShellDatabase(Context context) {
        System.out.println("Starting Shell database...");
        // Create a manager
        Manager manager = null;
        try {
            manager = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Create or open the database named db
        try {
            database = manager.getDatabase("database");
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save App.
     *
     * @param scope Scope URL of app.
     * @param properties An array of properties of the app to save.
     */
    public void saveApp(String scope, Map<String, Object> properties) {
        // Create a new document
        Document document = database.getDocument(scope);
        // Save the document to the database
        try {
            document.putProperties(properties);
            System.out.println("App saved " + properties.toString());
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a list of all apps.
     *
     * @return ArrayList<Object> A list of maps of app properties.
     */
    public ArrayList getApps() {
        QueryEnumerator result = null;
        Query query = database.createAllDocumentsQuery();
        try {
            result = query.run();
        } catch (CouchbaseLiteException e) {
            System.out.println("Error getting apps from database");
        }

        ArrayList apps = new ArrayList();

        for (Iterator<QueryRow> it = result; it.hasNext(); ) {
            QueryRow row = it.next();
            Document document = row.getDocument();
            apps.add(document.getProperties());
        }
        return apps;
    }

    /**
     * Get an app by its scope.
     *
     * @param scope Scope URL of app to get.
     * @return map of app properties.
     */
    public Map<String, Object> getApp(String scope) {
        Document document = database.getDocument(scope);
        SavedRevision revision = document.getCurrentRevision();
        Map<String, Object> properties = new HashMap<String, Object>();
        properties = revision.getProperties();
        System.out.println("Retrieved app " + properties.toString());
        return properties;
    }
}