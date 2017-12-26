package odermatt.com.rpilight.Classes;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Observable;
import java.util.Observer;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import odermatt.com.rpilight.Empty;
import odermatt.com.rpilight.RpiLightGrpc;
import odermatt.com.rpilight.RpiLightProto;
import odermatt.com.rpilight.State;
import odermatt.com.rpilight.models.RpiLight;
import rx.Subscription;

/**
 * Created by roman on 24.12.17.
 */

public class Grpc {

    ManagedChannel mChannel;

    public abstract static class OnStateChangeReceive {
        public abstract void run(RpiLight light);
    }
    public Grpc(String host, int port) {
        mChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
        Log.d("ServerCon", host +":" + port + " - " +mChannel.getState(true).toString());
    }

    public void On() {
        /*RpiLightGrpc.RpiLightBlockingStub stub = RpiLightGrpc.newBlockingStub(mChannel);
        Empty req = Empty.newBuilder().build();
        Empty res = stub.on(null);
        Log.d("On", "Light is set on");*/
        new GrpcTask(new SwitchOnRunnable()).execute();
    }

    public void Off() {
        new GrpcTask(new SwitchOffRunnable()).execute();
    }

    public State getState(){
        RpiLightGrpc.RpiLightBlockingStub stub = RpiLightGrpc.newBlockingStub(mChannel);
        State res = stub.getInfo(null);
        return res;
    }

    public void ObservableForLight(RpiLight l, OnStateChangeReceive listnr){
        RpiLightGrpc.RpiLightStub stub = RpiLightGrpc.newStub(mChannel);
        stub.subscribeStateChange(null, new StreamObserver<State>() {
            @Override
            public void onNext(State value) {
                Log.d("StateChange", "New State received:" + value.toString());
                l.lightstate = value;
                listnr.run(l);
            }

            @Override
            public void onError(Throwable t) {
                Log.e("StateChange", t.getMessage());
            }

            @Override
            public void onCompleted() {
                Log.e("StateChange", "Connection closed");
            }
        });
    }

    private class GrpcTask extends AsyncTask<Void, Void, String> {
        private final GrpcRunnable mGrpc;

        GrpcTask(GrpcRunnable grpc) {
            this.mGrpc = grpc;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... nothing) {
            try {
                String logs = mGrpc.run(RpiLightGrpc.newBlockingStub(mChannel),
                        RpiLightGrpc.newStub(mChannel));
                return "Success!" + System.lineSeparator() + logs;
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.flush();
                return "Failed... : " + System.lineSeparator() + sw;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("GRPCTask", result);
        }
    }

    private interface GrpcRunnable {
        /**
         * Perform a grpc and return all the logs.
         */
        String run(RpiLightGrpc.RpiLightBlockingStub blockingStub, RpiLightGrpc.RpiLightStub asyncStub) throws Exception;
    }

    private class SwitchOnRunnable implements GrpcRunnable{
        @Override
        public String run(RpiLightGrpc.RpiLightBlockingStub blockingStub, RpiLightGrpc.RpiLightStub asyncStub)
                throws Exception {
            return SwitchOn(blockingStub);
        }

        private String SwitchOn(RpiLightGrpc.RpiLightBlockingStub stub){
            Empty req = Empty.newBuilder().build();
            Empty res = stub.on(req);
            return "success";
        }
    }

    private class SwitchOffRunnable implements GrpcRunnable{
        @Override
        public String run(RpiLightGrpc.RpiLightBlockingStub blockingStub, RpiLightGrpc.RpiLightStub asyncStub)
                throws Exception {
            return SwitchOn(blockingStub);
        }

        private String SwitchOn(RpiLightGrpc.RpiLightBlockingStub stub){
            Empty req = Empty.newBuilder().build();
            Empty res = stub.off(req);
            return "success";
        }
    }
}
