package tk.itarusoft.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import tk.itarusoft.bakingapp.ItemClickListener;
import tk.itarusoft.bakingapp.R;
import tk.itarusoft.bakingapp.objects.Recipe;


public class RecipeAdapter extends  RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    List<Recipe> recipes;
    Context context;
    private ItemClickListener clickListener;

    public RecipeAdapter(Context context, List<Recipe> recipes){
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe, parent, false);
        RecipeViewHolder rvhRecipes = new RecipeViewHolder(layoutView);
        return rvhRecipes;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.tvName.setText(recipes.get(position).getName());
        holder.tvServings.setText(recipes.get(position).getServings().toString());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.iv_recipe) ImageView ivRecipe;
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_servings) TextView tvServings;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
