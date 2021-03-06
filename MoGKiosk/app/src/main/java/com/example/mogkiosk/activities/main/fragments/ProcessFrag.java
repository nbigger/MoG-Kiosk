package com.example.mogkiosk.activities.main.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mogkiosk.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProcessFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProcessFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProcessFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String YOUTUBE_API_KEY = "AIzaSyC4N2Q4nQxCv6Pm_wZt-QCNqgDq-fe27UI";
    private String processYoutube;
    private  YouTubePlayerSupportFragment youTubePlayerFragment;
    private FragmentTransaction transaction;
    private SharedPreferences prefs;

    private TextView title;

    public ProcessFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProcessFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ProcessFrag newInstance(String param1, String param2) {
        ProcessFrag fragment = new ProcessFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
        prefs =  PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        processYoutube = prefs.getString(getString(R.string.a_pyoutube), "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set youtube api
        transaction = getChildFragmentManager().beginTransaction();
        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        transaction.add(R.id.youtube_view, youTubePlayerFragment).commit();
        processYoutube = prefs.getString(getString(R.string.a_pyoutube), "");

        youTubePlayerFragment.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    //player.cueVideo("_zX5Uki421c");
                    player.cueVideo(processYoutube);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                String errorMessage = error.toString();
                Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });

        View rootView = inflater.inflate(R.layout.fragment_process, container, false);

        title = rootView.findViewById(R.id.video_title);
        // Inflate the layout for this fragment
        return rootView;
    }

    public void onResume() {
        super.onResume();
        Bundle extras = getArguments();
        if(extras != null) {
            String processTitle = prefs.getString(getString(R.string.a_ptitle), "");
            String processDescription = prefs.getString(getString(R.string.a_pdescription), "");
            processYoutube = prefs.getString(getString(R.string.a_pyoutube), "");
            //String youtubeLink = prefs.getStr
            if(processTitle != null && !processTitle.isEmpty()) {
                //set the text of the process title
                title.setText(processTitle);

            } else if(!processYoutube.isEmpty()) {
                transaction.replace(R.id.youtube_view, youTubePlayerFragment);
                youTubePlayerFragment.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                        if (!wasRestored) {
                            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                            //player.cueVideo("_zX5Uki421c");
                            player.cueVideo(processYoutube);
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                        String errorMessage = error.toString();
                        Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), errorMessage, Toast.LENGTH_LONG).show();
                        Log.d("errorMessage:", errorMessage);
                    }
                });
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
        }
//        else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    private interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
