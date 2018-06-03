package tk.itarusoft.bakingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

public class MainActivity extends AppCompatActivity implements ItemClickListener{

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

        LinearLayoutManager llmRecipes = new LinearLayoutManager(context);
        rvRecipes.setLayoutManager(llmRecipes);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo()!= null) {
            final ProgressDialog dialog;

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Getting data from json");
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
                        Toast.makeText(context,"Error Load Data",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    dialog.dismiss();

                    Toast.makeText(context,"Error Load Json",
                            Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(context,"Error Connection",
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
