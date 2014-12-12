package com.bangla.natok.prova;

import java.io.File;
import java.io.IOException;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Wallpaper {

	public static void wall(Context ctx, File b){
		
        // below line of code will set your current visible pager item to wallpaper
        // first we have to fetch bitmap from visible view and then we can pass it to wallpaper
     //   myWallpaperManager.set
        WallpaperManager myWallpaperManager1 = WallpaperManager
        .getInstance(ctx);  
String imageFilePath = b.getAbsolutePath().toString();
Bitmap myBitmap = BitmapFactory.decodeFile(imageFilePath);
if (myBitmap != null) {  
    try {  
        myWallpaperManager1.setBitmap(myBitmap);  
    } catch (IOException e) {  
       
    }  
}
        // below line of code will set input stream data directly to wallpaper
        // myWallpaperManager.setStream(InputStream Data);

        // below line of code will set any image which is in the drawable folder 
    
	}
}
