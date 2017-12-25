package odermatt.com.rpilight.Classes;

import com.github.druk.rxdnssd.BonjourService;
import com.github.druk.rxdnssd.RxDnssd;
import com.github.druk.rxdnssd.RxDnssdEmbedded;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by roman on 24.12.17.
 */

public class BonjourDiscover {

    public static String TAG = BonjourDiscover.class.getName();
    public static String SERVICE_TYPE = "_rpilight._tcp";

    private RxDnssd rxdnssd;
    private Observable<BonjourService> observable;

    public BonjourDiscover(){

        this.rxdnssd = new RxDnssdEmbedded();
        observable = this.rxdnssd.browse(SERVICE_TYPE, "local.")
                .compose(this.rxdnssd.resolve())
                .compose(this.rxdnssd.queryRecords()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Subscription Subscribe(Action1<BonjourService> listn){
        return observable.subscribe(listn);
    }

}
