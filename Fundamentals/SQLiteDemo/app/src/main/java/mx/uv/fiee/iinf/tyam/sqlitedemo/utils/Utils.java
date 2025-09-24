package mx.uv.fiee.iinf.tyam.sqlitedemo.utils;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Utils {

    public static final Migration UpgradeV2 = new Migration (1, 2) {
        @Override
        public void migrate (@NonNull SupportSQLiteDatabase database) {
            database.execSQL ("ALTER TABLE user_profile ADD COLUMN picture TEXT");
        }
    };

}
