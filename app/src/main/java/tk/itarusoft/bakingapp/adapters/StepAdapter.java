package tk.itarusoft.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tk.itarusoft.bakingapp.R;
import tk.itarusoft.bakingapp.StepDetailActivity;
import tk.itarusoft.bakingapp.StepDetailFragment;
import tk.itarusoft.bakingapp.StepListActivity;
import tk.itarusoft.bakingapp.objects.Step;

import static tk.itarusoft.bakingapp.StepDetailFragment.ARG_POSITION;
import static tk.itarusoft.bakingapp.StepDetailFragment.ARG_STEPS;


public class StepAdapter extends  RecyclerView.Adapter<StepAdapter.StepViewHolder> {

        private final StepListActivity parentActivity;
        private final ArrayList<Step> steps;
        private final boolean twoPane;

        private final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int stepPosition = (int) view.getTag();
                if (twoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelableArrayList(ARG_STEPS, steps);
                    arguments.putInt(ARG_POSITION,stepPosition);
                    StepDetailFragment fragment = new StepDetailFragment();
                    fragment.setArguments(arguments);
                    parentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.step_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, StepDetailActivity.class);
                    intent.putParcelableArrayListExtra(ARG_STEPS, steps);
                    intent.putExtra(ARG_POSITION, stepPosition);


                    context.startActivity(intent);
                }
            }
        };

        public StepAdapter(StepListActivity parent,
                           ArrayList<Step> steps,
                           boolean twoPane) {
            this.steps = steps;
            this.parentActivity = parent;
            this.twoPane = twoPane;
        }

        @Override
        public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_list_content, parent, false);
            return new StepViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final StepViewHolder holder, int position) {
            holder.tvStepNumber.setText("Step "+(steps.get(position).getId()));
            holder.tvStep.setText(steps.get(position).getShortDescription());

            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(onClickListener);
        }

        @Override
        public int getItemCount() {
            return steps.size();
        }

        class StepViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_step_number) TextView tvStepNumber;
            @BindView(R.id.tv_step) TextView tvStep;

            public StepViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

}
