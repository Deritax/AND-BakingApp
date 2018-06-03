package tk.itarusoft.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static tk.itarusoft.bakingapp.StepDetailFragment.ARG_POSITION;
import static tk.itarusoft.bakingapp.StepDetailFragment.ARG_STEPS;


public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        */
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
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            NavUtils.navigateUpTo(this, new Intent(this, StepListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
}
