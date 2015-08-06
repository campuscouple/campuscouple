package scut.farseer.campuscouple.fragment;

import scut.farseer.campuscouple.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DiscoverHomeFragment extends Fragment
{

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.social_homepage_layout, container, false);
		return view;
	}

	public void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
	}

}
