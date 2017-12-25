package odermatt.com.rpilight.Classes;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by roman on 24.12.17.
 */

public class Grpc {

    ManagedChannel mChannel;

    public Grpc(String host, int port){
        mChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
    }
}
