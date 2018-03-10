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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The Shell Database is used to persist apps, bookmarks, history and settings.
 */

public class ShellDatabase {

    private Database database;

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
        // The properties that will be saved on the document
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("type", "app");

        properties.put("name", "Twitter Lite");
        properties.put("short_name", "Twitter Lite");
        properties.put("description", "It's what's happening. From breaking news and "
                + "entertainment, sports and politics, to big events and everyday interests.");
        properties.put("display", "standalone");
        properties.put("orientation", "portrait");
        properties.put("start_url", "/");
        properties.put("theme_color", "#ffffff");
        properties.put("scope", "/");
        String scope = "https://mobile.twitter.com/";

        // Create a new document
        Document document = database.getDocument(scope);
        // Save the document to the database
        try {
            document.putProperties(properties);
            System.out.println("App saved " + properties.toString());
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        SavedRevision revision = document.getCurrentRevision();
        Map<String, Object> savedProperties = new HashMap<String, Object>();
        savedProperties = revision.getProperties();
        System.out.println("Retrieved app " + savedProperties.toString());
    }

    public void getApps() {
        QueryEnumerator result = null;
        Query query = database.createAllDocumentsQuery();
        try {
            result = query.run();
        } catch (CouchbaseLiteException e) {
            System.out.println("Error getting apps from database");
        }

        for (Iterator<QueryRow> it = result; it.hasNext(); ) {
            QueryRow row = it.next();
            Document document = row.getDocument();
            System.out.println("App: " + document.getProperty("name"));
        }
    }
}
