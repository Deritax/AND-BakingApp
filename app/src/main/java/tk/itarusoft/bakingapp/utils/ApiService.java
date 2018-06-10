package tk.itarusoft.bakingapp.utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import tk.itarusoft.bakingapp.objects.Recipe;

public interface ApiService {
        @GET("baking.json")
        Call<List<Recipe>> getJSON();
}
