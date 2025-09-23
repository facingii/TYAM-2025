package mx.uv.fiee.iinf.tyam.sqlitedemo.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import mx.uv.fiee.iinf.tyam.sqlitedemo.entities.UserProfile;

@Dao
public interface UserProfileDao {

    @Insert
    void insertProfile (UserProfile userProfile);
    @Query("SELECT * FROM user_profile")
    List<UserProfile> getAllProfiles ();

}
