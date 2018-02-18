package com.example.ravarich.currencyconverterbycamera.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ravarich.currencyconverterbycamera.R;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class TutorialMainFragment extends Fragment {

    ViewPager viewPager;
    CirclePageIndicator viewIndicator;



    public TutorialMainFragment() {
        super();
    }

    public static TutorialMainFragment newInstance() {
        TutorialMainFragment fragment = new TutorialMainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //เปลี่ยน layout ให้ตรงกับ fragment

        View rootView = inflater.inflate(R.layout.fragment_tutorial_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here


        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return Tutorial1Fragment.newInstance();
                    case 1:
                        return Tutorial2Fragment.newInstance();
                    case 2:
                        return Tutorial3Fragment.newInstance();
                    case 3:
                        return Tutorial4Fragment.newInstance();
                    case 4:
                        return Tutorial5Fragment.newInstance();
                    default:
                        return null;
                }

            }

            @Override
            public int getCount() {
                //จำนวนหน้า
                return 5;
            }


        });
        viewIndicator = (CirclePageIndicator) rootView.findViewById(R.id.viewIndicator);
        viewIndicator.setViewPager(viewPager);


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
}
