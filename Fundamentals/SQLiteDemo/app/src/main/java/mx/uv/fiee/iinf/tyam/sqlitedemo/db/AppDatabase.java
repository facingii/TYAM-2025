package mx.uv.fiee.iinf.tyam.sqlitedemo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import mx.uv.fiee.iinf.tyam.sqlitedemo.dao.UserProfileDao;
import mx.uv.fiee.iinf.tyam.sqlitedemo.entities.UserProfile;

@Database(entities = {UserProfile.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserProfileDao userProfileDao ();

}
