package com.example.spazaadmin.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spazaadmin.Adapters.ViewPagerAdapter;
import com.example.spazaadmin.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private TabLayout tabHost;
    private ViewPager viewpager;

    private int[] tabIcons = {R.drawable.ic_restaurant_menu,
            R.drawable.ic_order};

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        //set fragment context
        Context context = view.getContext();

        //call methods here
        //init(view);
        SetViewrPager(view);

        return view;
    }

    private void init(View view)
    {
        //initialize variables
        tabHost = view.findViewById(R.id.TabHost);
        viewpager = view.findViewById(R.id.viewpager);
    }

    private void SetViewrPager(View view)
    {
        tabHost = view.findViewById(R.id.TabHost);
        viewpager = view.findViewById(R.id.viewpager);

        PagerHolderAdapter adapter = new PagerHolderAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.LoadFragments(new OrderFragmentTab(), "ORDERS");
        adapter.LoadFragments(new PrepareOrderFragmentTab(), "PREPARED");


        tabHost.setupWithViewPager(viewpager);
        viewpager.setAdapter(adapter);
        viewpager.getAdapter().notifyDataSetChanged();

        //call method to set tab icons here
        setupTabIcons();
    }

    private void setupTabIcons()
    {
        tabHost.getTabAt(0).setIcon(tabIcons[0]);
        tabHost.getTabAt(1).setIcon(tabIcons[1]);
    }
}

class PagerHolderAdapter extends FragmentPagerAdapter{
    List<Fragment> frags = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    public PagerHolderAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    public void LoadFragments(Fragment f, String t){
        frags.add(f);
        titles.add(t);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return frags.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return frags.size();
    }
}