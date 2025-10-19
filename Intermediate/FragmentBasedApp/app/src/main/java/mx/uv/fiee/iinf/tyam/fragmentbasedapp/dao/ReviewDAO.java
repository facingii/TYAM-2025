package mx.uv.fiee.iinf.tyam.fragmentbasedapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import mx.uv.fiee.iinf.tyam.fragmentbasedapp.models.Reviews;

@Dao
public interface ReviewDAO {

    @Query("SELECT * FROM Reviews")
    List<Reviews> getAll ();

    @Query ("SELECT * FROM Reviews WHERE article_id = :id")
    Reviews findReviewById (int id);

    @Insert
    void insertAll (Reviews...reviews);

}