package scut.farseer.campuscouple.fragment;

import scut.farseer.campuscouple.AppConsts;
import scut.farseer.campuscouple.R;
import scut.farseer.campuscouple.activity.LoginActivity;
import scut.farseer.campuscouple.activity.PostStateActivity;
import scut.farseer.campuscouple.dialog.DiscoverDropDownDialog;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DiscoverHomeFragment extends Fragment implements OnClickListener
{
	public static final int SEARCH_MODE_CAMPUS = 0;
	public static final int SEARCH_MODE_FOLLOWED = 1;
	public static final int SEARCH_MODE_ALL_GENDER = 2;
	public static final int SEARCH_MODE_MALE = 3;
	public static final int SEARCH_MODE_FEMALE = 4;
	
	ImageButton btn_contacts;
	ImageButton btn_chat;
	ImageButton btn_new_state;
	TextView discover;
	ImageView discover_updown;
	
	DiscoverDropDownDialog discoverDropdownDialog;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	
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
		discover.setOnClickListener(this);
		discover_updown.setOnClickListener(this);
		
		
		discoverDropdownDialog = new DiscoverDropDownDialog(getActivity(), new OnClickListener() {
			
			public void onClick(View v)
			{
				switch (v.getId())
				{
					case DiscoverDropDownDialog.ID_FOR_BTN_WHOLE_SCHOOL:
						Toast.makeText(getActivity(), "全校", Toast.LENGTH_SHORT).show();
						break;
						
					case DiscoverDropDownDialog.ID_FOR_BTN_I_FOLLOWED:
						Toast.makeText(getActivity(), "我关注的人", Toast.LENGTH_SHORT).show();
						break;
						
					case DiscoverDropDownDialog.ID_FOR_BTN_ALL_GENDER:
						Toast.makeText(getActivity(), "全部", Toast.LENGTH_SHORT).show();
						break;
						
					case DiscoverDropDownDialog.ID_FOR_BTN_MALE:
						Toast.makeText(getActivity(), "男生", Toast.LENGTH_SHORT).show();
						break;
						
					case DiscoverDropDownDialog.ID_FOR_BTN_FEMALE:
						Toast.makeText(getActivity(), "女生", Toast.LENGTH_SHORT).show();
						break;

					default:
						break;
				}
			}
		});
		
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
				
			case R.id.discover:
				showDiscoverDropdownDialog();
				break;
			
			case R.id.discover_updown:
				showDiscoverDropdownDialog();
				break;

			default:
				break;
		}
	}
	
	private void showDiscoverDropdownDialog()
	{
		if(!discoverDropdownDialog.isShowing())
			discoverDropdownDialog.show();
	}

	private void onPostNewState()
	{
		Intent intent = new Intent(getActivity(), PostStateActivity.class);
		getActivity().startActivity(intent);
	}

	//得到SharedPreferences与SharedPreferences.Editor对象
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		preferences = activity.getSharedPreferences(AppConsts.PREFERENCE_NAME, Activity.MODE_PRIVATE);
		editor = preferences.edit();
	}
	
	//刷新文章列表
	private void refreshStateList()
	{
		switch (preferences.getInt("discover_search_mode", SEARCH_MODE_CAMPUS))
		{
			case SEARCH_MODE_ALL_GENDER:
				break;
				
			case SEARCH_MODE_FOLLOWED:
				break;

			default:
				break;
		}
	}
}
