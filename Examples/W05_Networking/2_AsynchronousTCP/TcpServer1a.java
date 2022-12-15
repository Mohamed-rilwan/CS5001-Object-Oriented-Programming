import java.io.*;
import java.net.*;

/**
 * Simple, non-blocking TCP server, checks before reading
 *
 * Saleem Bhatti, http://www.cs.st-andrews.ac.uk/~saleem/ February 2007
 */
public class TcpServer1a {

    static int port_ = 55501; // You need to change this!
    static ServerSocket server_;

    public static void main(String[] args) {
        startServer();

        while (true) // forever
        {
            try {
                Socket connection;
                OutputStream tx;
                InputStream rx;
                byte[] buffer;
                String s;

                connection = server_.accept(); // blocks waiting for connection
                tx = connection.getOutputStream();
                rx = connection.getInputStream();

                System.out.println(
                        "New connection ... "
                                + connection.getInetAddress().getHostName()
                                + ":"
                                + connection.getPort());

                buffer = null;
                while (buffer == null) // nothing from client
                {
                    System.out.println("Having a snooze ...");
                    Thread.sleep(2000); // 2 seconds

                    buffer = ByteReader.readBytes(rx, 80);
                    if (buffer != null) {
                        s = new String(buffer);
                        System.out.println("Received " + buffer.length + " bytes --> " + s);

                        System.out.println("Sending " + buffer.length + " bytes");
                        ByteWriter.writeBytes(
                                tx, buffer, buffer.length); // send data back to client
                    }
                } // while (buffer == null)

                connection.close();
            } catch (IOException e) {
                System.err.println("IO Exception: " + e.getMessage());
            } catch (InterruptedException e) {
                System.err.println("Interrupted Exception: " + e.getMessage());
            }
        } // main()
    }

    public static void startServer() {
        try {
            InetAddress address;
            String hostname;

            address = InetAddress.getLocalHost(); // not needed
            hostname = address.getHostName(); // not needed

            server_ = new ServerSocket(port_); // make a socket

            System.out.println("--* Starting server " + hostname + ":" + port_ + " -> " + server_);
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }

    protected void finalize() // tidy up when program ends
            {
        try {
            server_.close();
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }
}
