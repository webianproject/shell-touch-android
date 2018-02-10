package org.webian.shelltouch;

import java.io.IOException;
import fi.iki.elonen.NanoHTTPD;

/**
 * Shell Server.
 *
 * Serves system services.
 */
public class ShellServer extends NanoHTTPD {
    private final static int PORT = 8080;

    public ShellServer() throws IOException {
        super(PORT);
        start();
        System.out.println("Shell server running on http://localhost:8080");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String msg = "<html><body><h1>Webian Shell</h1></body></html>\n";
        return newFixedLengthResponse(msg);
    }
}
