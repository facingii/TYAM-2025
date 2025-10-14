package mx.uv.fiee.iinf.tyam.starwarsapp.interfaces;

import mx.uv.fiee.iinf.tyam.starwarsapp.Utils;
import mx.uv.fiee.iinf.tyam.starwarsapp.models.PeopleHeader;
import mx.uv.fiee.iinf.tyam.starwarsapp.models.PlanetHeader;
import mx.uv.fiee.iinf.tyam.starwarsapp.models.VehicleHeader;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SWApiService {

    @GET(Utils.API_PEOPLE)
    Call<PeopleHeader> getPeople ();

    @GET(Utils.API_PLANETS)
    Call<PlanetHeader> getPlanets ();

    @GET(Utils.API_VEHICLES)
    Call<VehicleHeader> getVehicles ();

}