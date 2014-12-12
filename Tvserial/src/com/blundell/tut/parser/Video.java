package com.blundell.tut.parser;



import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.Time;

public class Video implements Serializable{
	public String title, description;
	public  String url;
	public String thumbnailUrl;
	public int duration;
	public Time timestamp;
	public String uploader;
	public  String videoid;

	public Video(JSONObject json) throws JSONException {
		this.title = json.getString("title");
		this.description = json.getString("description");
		this.url = json.getJSONObject("player").getString("default");
		this.thumbnailUrl = json.getJSONObject("thumbnail").getString(
				"hqDefault");
		this.duration = json.getInt("duration");
		this.uploader = json.getString("uploader");
		this.videoid = json.getString("id");

		String updated = json.getString("updated");
		this.timestamp = new Time();
		this.timestamp.parse3339(updated);
	}

	public String formatDuration() {
		int minutes = duration / 60;
		int seconds = duration % 60;
		return leadingZeroes(minutes) + ":" + leadingZeroes(seconds);
	}

	public String leadingZeroes(int number) {
		if (number < 10)
			return "0" + number;
		else
			return "" + number;
	}

	public  String getUrl() {
		return url;
	}

	public  String getId(int position) {
		return videoid;
	}

}
