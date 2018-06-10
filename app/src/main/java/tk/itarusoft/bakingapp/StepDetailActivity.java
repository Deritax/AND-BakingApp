package tk.itarusoft.bakingapp;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import static tk.itarusoft.bakingapp.StepDetailFragment.ARG_POSITION;
import static tk.itarusoft.bakingapp.StepDetailFragment.ARG_STEPS;


public class StepDetailActivity extends AppCompatActivity {

    static android.support.design.widget.AppBarLayout toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        toolbar = findViewById(R.id.app_bar);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();

            arguments.putParcelableArrayList(ARG_STEPS,
                    getIntent().getExtras().getParcelableArrayList(ARG_STEPS));

            arguments.putInt(ARG_POSITION,getIntent().getExtras().getInt(ARG_POSITION));

            StepDetailFragment fragment = new StepDetailFragment();

            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, fragment)
                    .commit();
        }
    }
}
