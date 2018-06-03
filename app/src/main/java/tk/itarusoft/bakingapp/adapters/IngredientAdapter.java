package tk.itarusoft.bakingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tk.itarusoft.bakingapp.R;
import tk.itarusoft.bakingapp.objects.Ingredient;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {

public IngredientAdapter(Context context, int textViewResourceId,
                         List<Ingredient> objects) {
        super(context, textViewResourceId, objects);
        }

    @BindView(R.id.tv_ingredient) TextView tvIngredient;

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.ingredient, null);

        ButterKnife.bind(this, convertView);

        Ingredient currentIngredient = getItem(position);

        tvIngredient.setText("- "+currentIngredient.getIngredient()+" ("+currentIngredient.getQuantity()+" - "+currentIngredient.getMeasure()+")");
        return convertView;
        }

}