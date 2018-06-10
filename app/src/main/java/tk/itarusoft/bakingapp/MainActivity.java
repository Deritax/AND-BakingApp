package tk.itarusoft.bakingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.itarusoft.bakingapp.adapters.RecipeAdapter;
import tk.itarusoft.bakingapp.objects.Recipe;
import tk.itarusoft.bakingapp.utils.ApiService;
import tk.itarusoft.bakingapp.utils.ItemClickListener;
import tk.itarusoft.bakingapp.utils.RetroClient;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    Context context = this;

    @BindView(R.id.rv_recipes)
    RecyclerView rvRecipes;

    private List<Recipe> recipes;
    private RecipeAdapter raRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        recipes = new ArrayList<>();

        rvRecipes.setHasFixedSize(true);

        int numberOfColumns;

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            numberOfColumns = 3;
        }else{
            numberOfColumns = 1;
        }

        GridLayoutManager glmRecipes = new GridLayoutManager(context,numberOfColumns);
        rvRecipes.setLayoutManager(glmRecipes);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo()!= null) {
            final ProgressDialog dialog;

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle(getString(R.string.loading));
            dialog.setMessage(getString(R.string.get_data));
            dialog.show();

            ApiService api = RetroClient.getApiService();

            Call<List<Recipe>> call = api.getJSON();

            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                    dialog.dismiss();

                    if(response.isSuccessful()) {

                        recipes = response.body();

                        raRecipe = new RecipeAdapter(MainActivity.this,recipes);
                        rvRecipes.setAdapter(raRecipe);
                        raRecipe.setClickListener(MainActivity.this);

                    } else {
                        Toast.makeText(context, R.string.error_data,
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    dialog.dismiss();

                    Toast.makeText(context, R.string.error_load,
                            Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(context, R.string.error_connect,
                    Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View view, int position) {
        final Recipe recipe = recipes.get(position);
        Intent i = new Intent(this, StepListActivity.class);
        i.putExtra("recipe", recipe);
        startActivity(i);
    }
}
