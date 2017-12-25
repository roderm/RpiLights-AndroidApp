package odermatt.com.rpilight.models;

import java.net.InetAddress;

import odermatt.com.rpilight.Classes.Grpc;

/**
 * Created by roman on 24.12.17.
 */

public class RpiLight {
    public long sql_id;
    public InetAddress IP;
    public String Hostname;
    public int Port;
    public boolean stored;
    public boolean reachable;
    public Grpc connection;
}
