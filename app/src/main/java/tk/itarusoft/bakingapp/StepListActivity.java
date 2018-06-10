package tk.itarusoft.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tk.itarusoft.bakingapp.adapters.IngredientAdapter;
import tk.itarusoft.bakingapp.adapters.StepAdapter;
import tk.itarusoft.bakingapp.objects.Ingredient;
import tk.itarusoft.bakingapp.objects.Recipe;
import tk.itarusoft.bakingapp.utils.ListViewNoScroll;

public class StepListActivity extends AppCompatActivity {

    public static boolean twoPanel;
    private Recipe recipe;

    @BindView(R.id.lv_ingredients_list)
    ListViewNoScroll lvIngredientsList;

    @BindView(R.id.step_list)
    RecyclerView rvStepList;

    @BindView(R.id.b_add_widget)
    Button bAddWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        recipe = data.getParcelable("recipe");

        if (findViewById(R.id.step_detail_container) != null) {
            twoPanel = true;
        }

        IngredientAdapter aIngredients = new IngredientAdapter(this, R.layout.ingredient, recipe.getIngredients());
        lvIngredientsList.setAdapter(aIngredients);

        assert rvStepList != null;
        setupRecyclerView(rvStepList);
        rvStepList.setNestedScrollingEnabled(false);

        bAddWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StepListActivity.this, BakingAppWidget.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                int[] ids = AppWidgetManager.getInstance(getApplication())
                        .getAppWidgetIds(new ComponentName(getApplication(), BakingAppWidget.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

                List<Ingredient> ingredients = recipe.getIngredients();

                StringBuilder sb = new StringBuilder();

                for (Ingredient ingredient : ingredients) {
                    sb.append("- "+ingredient.getIngredient()+" ("+ingredient.getQuantity()+" - "+ingredient.getMeasure()+")");
                    sb.append("\n");
                }

                BakingAppWidget.widgetTitle = recipe.getName();
                BakingAppWidget.widgetList = sb.toString();
                sendBroadcast(intent);
            }
        });

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new StepAdapter(this, recipe.getSteps(), twoPanel));
    }
}
