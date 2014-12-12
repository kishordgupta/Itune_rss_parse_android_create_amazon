package com.blundell.tut.ui.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.blundell.tut.ui.widget.UrlImageView;
import com.tvserial.plot.amazon.trailer.R;

/**
 * This adapter is used to show our Video objects in a ListView
 * It hasn't got many memory optimisations, if your list is getting bigger or more complex
 * you may want to look at better using your view resources: http://developer.android.com/resources/samples/ApiDemos/src/com/example/android/apis/view/List14.html
 * @author paul.blundell
 */
public class VideosAdapter extends BaseAdapter {
	// The list of videos to display
	List<com.blundell.tut.parser.Video> videos;
	Context con=null;
	// An inflator to use when creating rows
	private LayoutInflater mInflater;
	
	/**
	 * @param context this is the context that the list will be shown in - used to create new list rows
	 * @param list this is a list of videos to display
	 */
	public VideosAdapter(Context context, List<com.blundell.tut.parser.Video> list) {
		this.videos = list;
		this.mInflater = LayoutInflater.from(context);
		this.con=context;
	}

	@Override
	public int getCount() {
		return videos.size();
	}

	@Override
	public Object getItem(int position) {
		return videos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// If convertView wasn't null it means we have already set it to our list_item_user_video so no need to do it again
		if(convertView == null){
			// This is the layout we are using for each row in our list
			// anything you declare in this layout can then be referenced below
			convertView = mInflater.inflate(R.layout.list_item_user_video, null);
		}
		
		// We are using a custom imageview so that we can load images using urls
		// For further explanation see: http://blog.blundell-apps.com/imageview-with-loading-spinner/
		UrlImageView thumb = (UrlImageView) convertView.findViewById(R.id.userVideoThumbImageView);
		
		TextView title = (TextView) convertView.findViewById(R.id.userVideoTitleTextView); 
		// Get a single video from our list
		com.blundell.tut.parser.Video video = videos.get(position);
		// Set the image for the list item
		thumb.setImageDrawable(video.thumbnailUrl);
		// Set the title for the list item
		title.setText(video.title);
		convertView.setTag(position);
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
			com.blundell.tut.parser.Video video = videos.get((Integer) v.getTag());
			//Toast.makeText(con,  v.getTag()+""+video.videoid, Toast.LENGTH_SHORT).show();
				con.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(video.url)));
			}
		});
		
		return convertView;
	}
}