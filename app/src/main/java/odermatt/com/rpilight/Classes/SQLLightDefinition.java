package odermatt.com.rpilight.Classes;

import android.provider.BaseColumns;

/**
 * Created by roman on 25.12.17.
 */

public final class SQLLightDefinition {

        // To prevent someone from accidentally instantiating the contract class,
        // make the constructor private.
        private SQLLightDefinition() {}

        /* Inner class that defines the table contents */
        public static class LightDefinition implements BaseColumns {
            public static final String TABLE_NAME = "entry";
            public static final String HOST = "hostname";
            public static final String IP = "ip_addr";
            public static final String PORT = "port";

        }
}
