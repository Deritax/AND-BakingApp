package tk.itarusoft.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import tk.itarusoft.bakingapp.objects.Step;

public class StepDetailFragment extends Fragment {

    public static final String ARG_STEPS = "steps";
    public static final String ARG_POSITION = "stepPosition";


    private ArrayList<Step> steps;

    private int stepPosition;

    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private BandwidthMeter bandwidthMeter;
    private Handler mainHandler;
    private FragmentTransaction ft;

    public StepDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();

        ft = getFragmentManager().beginTransaction();

        if(savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList(ARG_STEPS);
            stepPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        else {
            steps = getArguments().getParcelableArrayList(ARG_STEPS);
            stepPosition = getArguments().getInt(ARG_POSITION);

        }

        View rootView = inflater.inflate(R.layout.step_detail, container, false);

        simpleExoPlayerView = rootView.findViewById(R.id.playerView);
        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

        String videoURL = steps.get(stepPosition).getVideoURL();

        ((TextView) rootView.findViewById(R.id.step_detail)).setText(steps.get(stepPosition).getDescription());


        if (!videoURL.isEmpty()) {

            initializePlayer(Uri.parse(steps.get(stepPosition).getVideoURL()));

            if ((getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    && (StepListActivity.twoPanel == false)){
                rootView.findViewById(R.id.step_detail).setVisibility(View.GONE);
                rootView.findViewById(R.id.buttons_nextprev).setVisibility(View.GONE);
                StepDetailActivity.toolbar.setVisibility(View.GONE);
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN);
            }


        }
        else {
            player=null;
        }


        Button prevStep = rootView.findViewById(R.id.prev_step);
        Button nextStep = rootView.findViewById(R.id.next_step);

        prevStep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (steps.get(stepPosition).getId() > 0) {
                    if (player!=null){
                        player.stop();
                    }

                    getArguments().putInt(ARG_POSITION,stepPosition-1);
                    ft.detach(StepDetailFragment.this).attach(StepDetailFragment.this).commit();
                }
                else {
                    Toast.makeText(getActivity(), R.string.first_step, Toast.LENGTH_SHORT).show();

                }
            }});

        nextStep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                int lastIndex = steps.size()-1;
                if (steps.get(stepPosition).getId() < steps.get(lastIndex).getId()) {
                    if (player!=null){
                        player.stop();
                    }

                    getArguments().putInt(ARG_POSITION,stepPosition+1);
                    ft.detach(StepDetailFragment.this).attach(StepDetailFragment.this).commit();
                }
                else {
                    Toast.makeText(getContext(), R.string.last_step, Toast.LENGTH_SHORT).show();

                }
            }});

        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (player == null) {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(player);

            String userAgent = Util.getUserAgent(getContext(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(ARG_STEPS,steps);
        currentState.putInt(ARG_POSITION,stepPosition);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player!=null) {
            player.stop();
            player.release();
            player=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }
}
