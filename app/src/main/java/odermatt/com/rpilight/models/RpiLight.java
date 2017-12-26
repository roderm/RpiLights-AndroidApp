package odermatt.com.rpilight.models;


import java.util.Observable;

import odermatt.com.rpilight.Classes.Grpc;
import odermatt.com.rpilight.State;

/**
 * Created by roman on 24.12.17.
 */

public class RpiLight extends Observable{
    public long sql_id;
    public String IP;
    public String Hostname;
    public int Port;
    public boolean stored;
    public boolean reachable;
    public State lightstate;
    public Grpc connection;
    public Grpc GrpcStateChannel;
}
