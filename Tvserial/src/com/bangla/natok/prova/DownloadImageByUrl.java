package com.bangla.natok.prova;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class DownloadImageByUrl {
	public static int  ss=0;
	public Context context=null;
	public void downloadImage(String URL,int stat, Context c) {
		ss=stat;
		context=c;
		new ParseSite().execute(URL);
		
		
	
	}
	
	private InputStream OpenHttpConnection(String urlString) throws IOException {
		InputStream in = null;
		int response = -1;

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();

		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not an HTTP connection");

		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			response = httpConn.getResponseCode();
			if (response == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();
			}
		} catch (Exception ex) {
			throw new IOException("Error connecting"+ex.toString());
		}
		return in;
	}
	
	  private class ParseSite extends AsyncTask<String, Void, Bitmap> {

	        protected Bitmap doInBackground(String... arg) {
	        	
	        	Bitmap bitmap = null;
	    		InputStream in = null;
	    	
	    		try {
	    			URL  url = new URL(arg[0]);
	    			in = OpenHttpConnection(url.toString());
	    			bitmap = BitmapFactory.decodeStream(in);
	    			in.close();
	    		} catch (IOException e1) {
	    			// TODO Auto-generated catch block
	    			e1.printStackTrace();
	    			//Log.d("sfsdf",e1.toString());
	    		}
		        Calendar cal=Calendar.getInstance();
			       
							 		File dir = Environment.getExternalStorageDirectory();
							 		 File output = new File(dir, cal.getTime().toString().replace(".", "")+"lilait.jpg");
							 		 FileOutputStream os = null;
							 		
							 		try {
							 			os = new FileOutputStream(output);
							 		} catch (FileNotFoundException e) {
							 			// TODO Auto-generated catch block
							 			e.printStackTrace();
							 		}
							 		 if(os!=null&&bitmap!=null)
							 		  {
							 		 bitmap.compress(CompressFormat.JPEG, 100, os);
							 		  }
							 		    try {
							 				os.flush();
							 			} catch (IOException e) {
							 				// TODO Auto-generated catch block
							 				e.printStackTrace();
							 			}
							 		    try {
							 				os.close();
							 			} catch (IOException e) {
							 				// TODO Auto-generated catch block
							 				e.printStackTrace();
							 			}
					if(ss==2)
					{Wallpaper.wall(context, output);
					}
					if(ss==3)
					{Share.share(output, context,"");}
							 		   
	    		return bitmap;
	           


	        }

	        protected void onPostExecute(Bitmap output) {

	        //	MainActivity.finalload=output;
	        //	MainActivity.Startload=false;
	       //     pd.dismiss();
	            //Imageurl.newyearsvalues=(ArrayList<String>) output;
	              
	        }
	        
	
/*
	class DownloadImageTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... urls) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Toast.makeText(getApplicationContext(), "doInBackground", Toast.LENGTH_LONG).show();
			Bitmap bitmap = DownloadImage("http://powerpointfx.net/test_wp/2.jpg");
			Log.v("DEBUG_LOG", "In DoInBackground");
			return bitmap.toString();

		}

		@Override
		protected void onPostExecute(String result) {
			//Toast.makeText(getApplicationContext(), "onPostExecute", Toast.LENGTH_LONG).show();
			ImageView img = (ImageView) findViewById(R.id.img);
			img.setImageBitmap(bitmap);
			Log.v("DEBUG_LOG", "In onPostExecute");
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			//Toast.makeText(getApplicationContext(), "onProgressUpdate", Toast.LENGTH_LONG).show();
			//setProgress(progress[0]);
			Log.v("DEBUG_LOG", "In onProgressUpdate");
			super.onProgressUpdate(progress);
		}

	}*/
}
	  
}
