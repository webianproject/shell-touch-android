package org.webian.shelltouch;

import java.io.IOException;

import fi.iki.elonen.router.RouterNanoHTTPD;

/**
 * Shell Server.
 *
 * Serves system services.
 */
public class ShellServer extends RouterNanoHTTPD {
    private final static int PORT = 8080;

    public ShellServer() throws IOException {
        super(PORT);
        addMappings();
        System.out.println("Shell server running on http://localhost:" + PORT + "/ \n");
    }

    @Override
    public void addMappings() {
        super.addMappings();
    }
}

