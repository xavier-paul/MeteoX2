package xavierpaul.meteox2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by xpaul on 10/08/2016.
 */
public class BitmapHelper extends AsyncTask<String, Void, Bitmap> {
    ImageView m_imageContainer;

    public BitmapHelper(ImageView p_imageContainter) {
        this.m_imageContainer = p_imageContainter;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        if (m_imageContainer != null) {
            if (result != null)
                m_imageContainer.setImageBitmap(result);
         }
    }
}