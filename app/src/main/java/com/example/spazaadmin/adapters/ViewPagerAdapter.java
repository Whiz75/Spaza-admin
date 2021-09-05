package com.example.spazaadmin.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.spazaadmin.fragments.OrderFragmentTab;
import com.example.spazaadmin.fragments.PrepareOrderFragmentTab;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> fragmentsTitle;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void add(Fragment fragment, String title)
    {
        fragments.add(fragment);
        fragmentsTitle.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position)
        {
            case 0:
                fragment = new OrderFragmentTab();
                break;
            case 1:
                fragment = new PrepareOrderFragmentTab();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position)
    {
        String title = " ";

        switch (position)
        {
            case 0:
                title = "Orders";
                break;
            case 1:
                title = "Prepared Orders";
                break;
        }
        return title;
    }
}
