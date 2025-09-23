package mx.uv.fiee.iinf.tyam.sqlitedemo.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_profile")
public class UserProfile implements Comparable<UserProfile> {

    @PrimaryKey (autoGenerate = true)
    public Integer id;
    public String firstName;
    public String lastName;
    public int age;
    public String phone;

    @Override
    public int compareTo (UserProfile o) {
        return Integer.compare (o.id, this.id);
    }
}
