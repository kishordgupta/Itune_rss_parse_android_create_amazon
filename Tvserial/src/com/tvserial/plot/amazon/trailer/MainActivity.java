/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tvserial.plot.amazon.trailer;
import java.io.IOException;
import java.io.InputStream;

import hu.portalsoft.itunestopmovies.model.ITunesMovie;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.tvserial.plot.amazon.trailer.R;


public class MainActivity extends FragmentActivity {

	

	private final Handler handler = new Handler();
    public static Context context=null;
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;

	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	AllListFragment AllFragment ;
	ActionListFragment Action_adventure ;
	AnimationlistFragment Animation ;
	ClassicListFragment Classic ;
	ComedyListFragment Comedy ;
	DramaListFragment Drama ;
	KidsListFragment Kids ;
	LatinolListFragment LAtino ;
	NonfictionlListFragment Nonfiction ;
	RealityListFragment Reality_URL ;
	ScifilListFragment Scifi ;
	SportsListFragment Sports ;
	TeenListFragment Teen_URL ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
	     getActionBar().setLogo(getResources().getDrawable(R.drawable.app_icon));
		context=this;
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());

		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		tabs.setViewPager(pager);

		
		tabs.setViewPager(pager);
		tabs.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		
		Fileread();
		AllFragment = new AllListFragment();
		 Action_adventure = new ActionListFragment();
		 Animation = new AnimationlistFragment();
		 Classic = new ClassicListFragment();
		 Comedy = new ComedyListFragment();
		 Drama = new DramaListFragment();
		 Kids = new KidsListFragment();
		 LAtino = new LatinolListFragment();
		 Nonfiction = new NonfictionlListFragment();
		 Reality_URL = new RealityListFragment();
		 Scifi = new ScifilListFragment();
		 Sports = new SportsListFragment();
		 Teen_URL = new TeenListFragment();
		//changeColor(currentColor);
		 
		 
		 AdView adView = new AdView(this);
		    adView.setAdSize(AdSize.BANNER);
		    adView.setAdUnitId("ca-app-pub-2884314377778347/8122550310");

		    // Add the AdView to the view hierarchy. The view will have no size
		    // until the ad is loaded.
		    LinearLayout layout = (LinearLayout) findViewById(R.id.a);
		    layout.addView(adView);

		    // Create an ad request. Check logcat output for the hashed device ID to
		    // get test ads on a physical device.
		    AdRequest adRequest = new AdRequest.Builder()
		        .build();

		    // Start loading the ad in the background.
		    adView.loadAd(adRequest);
	}

	public void Fileread()
	{
		String line="";
		AssetManager assetManager = MainActivity.context.getAssets();
		InputStream input = null;
		try {
			input = assetManager.open( "amazon.html");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if (input != null) {
			/*	BufferedReader br = new BufferedReader(new InputStreamReader(
						input));*/
				 int size = input.available();
				 byte[] buffer = new byte[size];
				 input.read(buffer);

				// byte buffer into a string
				 String text = new String(buffer);
				 line=text;
				// String line;

				// read every line of the file into the line-variable, on line
				// at the time
				/*do {
					line = line + "\n" + br.readLine();

					// do something with the line
				} while (br.readLine() != null);*/

			}
		} catch (Exception ex) {
			// print stack trace.
		} finally {
			// close the file.
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		URLlist.Sdata= line;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	//	getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_contact:
			//QuickContactFragment dialog = new QuickContactFragment();
			//dialog.show(getSupportFragmentManager(), "QuickContactFragment");
			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//outState.putInt("currentColor", currentColor);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		//currentColor = savedInstanceState.getInt("currentColor");
		//changeColor(currentColor);
	}


	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "All", "Action" , "Animation", "Classic", "Comedy", "Drama",
		"KIDS", "Latino"	,"Non-fiction", "Reality" ,	"Sci-fi", "Sports" ,"Teen"};//

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			Log.d("kd", position+"");
		/*	AllFragment = new AllListFragment();
			 Action_adventure = new ActionListFragment();
			 Animation = new AnimationlistFragment();
			 Classic = new ClassicListFragment();
			 Comedy = new ComedyListFragment();
			 Drama = new DramaListFragment();
			 Kids = new KidsListFragment();
			 LAtino = new LatinolListFragment();
			 Nonfiction = new NonfictionlListFragment();
			 Reality_URL = new RealityListFragment();
			 Scifi = new ScifilListFragment();
			 Sports = new SportsListFragment();
			 Teen_URL = new TeenListFragment();*/
		     setProgressBarIndeterminateVisibility(true);
			switch (position) {
			case 0:
				
				return AllFragment;
			case 1:
			
				return Action_adventure;
			case 2:
				
				return Animation;
			case 3:

				return Classic;
			case 4:

				return Comedy;
			case 5:

				return Drama;
			case 6:

				return Kids;
			case 7:

				return LAtino;
			case 8:

				return Nonfiction;
			case 9:

				return Reality_URL;
			case 10:

				return Scifi;
			case 11:

				return Sports;
		
             default:
				
				return 	 Teen_URL;
			}
			
		
		}

	}

}