package org.webian.shelltouch;

import android.content.Context;
import android.content.res.AssetManager;
import android.webkit.MimeTypeMap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;

/**
 * Shell Server.
 *
 * Serves local system UI and system services.
 */
public class ShellServer extends RouterNanoHTTPD {
    private final static int PORT = 8080;
    private static AssetManager assetManager;
    private static Context context;
    private static ShellDatabase database;

    public ShellServer(Context context) throws IOException {
        super(PORT);
        this.context = context;
        this.assetManager = this.context.getAssets();
        this.database = new ShellDatabase(this.context);

        addMappings();
        System.out.println("Shell server running on http://localhost:" + PORT + "/ \n");
    }

    /**
     * Home Screen Handler serves static resources from assets/home folder.
     */
    public static class HomeScreenHandler extends DefaultHandler {

        @Override
        public String getText() {
            return null;
        }

        @Override
        public String getMimeType() {
            return null;
        }

        @Override
        public NanoHTTPD.Response.IStatus getStatus() {
            return NanoHTTPD.Response.Status.OK;
        }

        /**
         * Respond to GET requests.
         */
        @Override
        public Response get(UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
            String path = normalizeUri(session.getUri());
            InputStream stream;
            System.out.println("Static file requested " + path);

            // Redirect root to index.html
            if (path.equals("home")) {
                path = "home/index.html";
            }
            //TODO: Redirect home to home/

            // Derive MIME type from file extension
            String type = "";
            String extension = MimeTypeMap.getFileExtensionFromUrl(path);
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            }
            System.out.println("Type of file requested " + type);

            // Try loading the file from the asset manager
            try {
                stream = assetManager.open(path);
            } catch (IOException e) {
                System.out.println("Static file not found");
                return new Error404UriHandler().get(uriResource, urlParams, session);
            }
            return NanoHTTPD.newChunkedResponse(getStatus(), type, stream);
        }
    }

    /**
     * App Service returns a collection of app manifests in JSON.
     */
    public static class AppService extends DefaultHandler {

        @Override
        public String getText() {
            database.getApps();
            return "apps";
        }

        @Override
        public String getMimeType() {
            return "application/json";
        }

        @Override
        public NanoHTTPD.Response.IStatus getStatus() {
            return NanoHTTPD.Response.Status.OK;
        }

        /**
         * Respond to GET requests.
         */
        @Override
        public Response get(UriResource uriResource, Map<String, String> urlParams, IHTTPSession session) {
            String text = getText();
            ByteArrayInputStream input = new ByteArrayInputStream(text.getBytes());
            int size = text.getBytes().length;
            return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), input, size);
        }
    }

    /**
     * Add routes to router.
     */
    @Override
    public void addMappings() {
        super.addMappings();
        addRoute("/home(.?)+", HomeScreenHandler.class);
        addRoute("/apps", AppService.class);
    }
}

