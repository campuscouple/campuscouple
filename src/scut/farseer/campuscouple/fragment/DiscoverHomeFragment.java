package scut.farseer.campuscouple.fragment;

import scut.farseer.campuscouple.R;
import scut.farseer.campuscouple.activity.LoginActivity;
import scut.farseer.campuscouple.activity.PostStateActivity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DiscoverHomeFragment extends Fragment implements OnClickListener
{
	ImageButton btn_contacts;
	ImageButton btn_chat;
	ImageButton btn_new_state;
	TextView discover;
	ImageView discover_updown;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.social_homepage_layout, container, false);
		
		btn_contacts = (ImageButton) view.findViewById(R.id.btn_contacts);
		btn_chat = (ImageButton) view.findViewById(R.id.btn_chat);
		btn_new_state = (ImageButton) view.findViewById(R.id.btn_new_state);
		discover = (TextView) view.findViewById(R.id.discover);
		discover_updown = (ImageView) view.findViewById(R.id.discover_updown);
		
		btn_new_state.setOnClickListener(this);
		
		return view;
	}

	public void onResume()
	{
		super.onResume();
	}

	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btn_new_state:
				onPostNewState();
				break;

			default:
				break;
		}
	}

	private void onPostNewState()
	{
		Intent intent = new Intent(getActivity(), PostStateActivity.class);
		getActivity().startActivity(intent);
	}
}
