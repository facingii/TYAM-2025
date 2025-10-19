package mx.uv.fiee.iinf.tyam.fragmentbasedapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import mx.uv.fiee.iinf.tyam.fragmentbasedapp.models.Reviews;
import mx.uv.fiee.iinf.tyam.fragmentbasedapp.dao.ReviewDAO;

@Database(entities = {Reviews.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ReviewDAO reviewDAO ();
}