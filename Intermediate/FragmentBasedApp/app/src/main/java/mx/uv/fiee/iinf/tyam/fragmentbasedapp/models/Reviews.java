package mx.uv.fiee.iinf.tyam.fragmentbasedapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Reviews {

    @PrimaryKey
    @ColumnInfo (name = "article_id")
    public int ArticleId;

    @ColumnInfo(name = "review")
    public String Review;

}
