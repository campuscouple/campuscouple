package scut.farseer.campuscouple.model;

import org.json.JSONException;
import org.json.JSONObject;

public class School
{
	public int id;
	public String name;
	
	public void fromJson(JSONObject jo) throws JSONException
	{
		if(jo == null)
			throw new JSONException("");
		id = jo.getInt("id");
		name = jo.getString("name");
	}
}
