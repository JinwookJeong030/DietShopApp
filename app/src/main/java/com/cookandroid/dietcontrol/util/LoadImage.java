package com.cookandroid.dietcontrol.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.dietmanagement.ListDietLogItem;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class LoadImage extends AsyncTask<Bitmap, Void, Bitmap>{


    /**
     *  사용 방법
     *  인수 => String 이미지가 있는 URL , ImageView 이미지를 출력할 ImageView
     *  사용 예시
     *
     *  LoadImage image = new LoadImage("http://hsdiet.dothome.co.kr/img/diet_log/이미지명.PNG" , imageView);
     *
     *  image.execute();
     *
     *
     *  // execute(); 실행시 실행순서 doInBackground()가 끝나면 -> onPostExcute()
     *
     */

        private String Url ;
        private ImageView imageView;


        //url 과 이미지뷰를 담음
        public LoadImage(String Url, ImageView imageView) {
            this.Url = Url;
            this.imageView =imageView;
        }


        //url값으로 웹상(hsdiet.dothome.co.kr)에 있는 이미지를 비트맵으로 불러옴
        //이 구간은 스레드로 작동됨
        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            Bitmap bitmap = null;

            try {
                URL imgURL = new URL(Url);
                URLConnection conn = imgURL.openConnection();
                conn.connect();
                int nSize = conn.getContentLength();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), nSize);
                bitmap = BitmapFactory.decodeStream(bis);
                bis.close();
            } catch (IOException e) {

            }

            return bitmap;
        }


        //doInBackground 함수(스레드)의 결과 값이 bitmap 매개변수로 들어옴
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap != null)
                //웹상에 이미지가 정상적으로 있을경우
                imageView.setImageBitmap(bitmap);
            else
                //웹상에 이미지가 없는경우 안드로이드에 있는 대체이미지가 들어감
                imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.ic_diet_camera));


        }

    }

