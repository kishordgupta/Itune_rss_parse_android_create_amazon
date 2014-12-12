package com.bangla.natok.prova;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Share {

	public static void share(File wish, Context context)
	{
		 Intent sharingIntent = new Intent(Intent.ACTION_SEND);
         Uri screenshotUri = Uri.parse("file://" + wish.getAbsolutePath());

         sharingIntent.setType("image/jpg");
         sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
         context.startActivity(Intent.createChooser(sharingIntent, "Share image using"));
	}

	public static void share(File wish, Context c, String text) {
		// TODO Auto-generated method stub
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri screenshotUri = Uri.parse("file://" + wish.getAbsolutePath());
         text=text.replace("%22", "");
        sharingIntent.setType("image/jpg");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        sharingIntent.putExtra(Intent.EXTRA_TEXT," .........#despicableapps");
        c.startActivity(Intent.createChooser(sharingIntent, "Share image using"));
	}
}
