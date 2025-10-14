package mx.uv.fiee.iinf.tyam.starwarsapp;

import mx.uv.fiee.iinf.tyam.starwarsapp.interfaces.SWApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    public static final String API_PLANETS = "api/planets/";
    public static final String API_PEOPLE = "api/people/";
    public static final String API_VEHICLES = "api/vehicles/";
    public static final String DATA_FORMAT = "%s: %s";
    public static final String DATA_HEIGHT = "Height: ";
    public static final String DATA_BIRTHYEAR = "Birthyear: ";
    public static final String TITLE = "Star Wars Elements";
    public static final String TAB_PLANETS = "Planets";
    public static final String TAB_PEOPLE = "People";
    public static final String TAB_VEHICLES = "Vehicles";
    public static final String TAG = "PKAT";

    private static final String BASE_URL = "https://swapi.dev/";

    // para mantener solo una referencia al servicio a través de toda la aplicación
    // utilizamos el patrón singleton
    private static SWApiService service;

    public static SWApiService createService () {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl (BASE_URL)
                    .build();

            service = retrofit.create (SWApiService.class);
        }

        return service;
    }
}