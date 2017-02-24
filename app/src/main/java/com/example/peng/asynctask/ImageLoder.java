package com.example.peng.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by peng on 2017/2/24.
 */

public class ImageLoder {
    private ImageView mImageView;
    private String mUrl;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mImageView.getTag().equals(mUrl)){
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };
    public void showImageByThread(ImageView imageView, final String url){
        mImageView=imageView;
        mUrl=url;
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Bitmap bitmap=getBitmapFromURL(url);
                    Message message=Message.obtain();
                    message.obj=bitmap;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public Bitmap getBitmapFromURL(String urlString) throws IOException {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url=new URL(urlString);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            is=new BufferedInputStream(connection.getInputStream());
            bitmap= BitmapFactory.decodeStream(is);
            connection.disconnect();
            Thread.sleep(1000);
            return bitmap;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
        return null;
    }
}
