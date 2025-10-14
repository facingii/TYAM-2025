package mx.uv.fiee.iinf.tyam.starwarsapp.models;

import com.google.gson.annotations.SerializedName;

public class People {

    public String name;
    public String height;

    @SerializedName("birth_year")
    public String birthyear;

}
