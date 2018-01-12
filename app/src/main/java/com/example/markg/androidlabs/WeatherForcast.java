package com.example.markg.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.*;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeatherForcast extends Activity {

    protected static final String ACTIVITY_NAME = "WeatherForecast";

    public ImageView weatherImage;
    public ProgressBar progressBar;
    public TextView currentTemp;
    public TextView minTemp;
    public TextView maxTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forcast);

        weatherImage = (ImageView) findViewById(R.id.weatherImage);
        progressBar = (ProgressBar) findViewById(R.id.weatherProgressBar);
        currentTemp = (TextView) findViewById(R.id.currentTemperature);
        currentTemp.setHint("Current Temperature");
        minTemp = (TextView) findViewById(R.id.minTemperature);
        minTemp.setHint("Minimum Temperature");
        maxTemp = (TextView) findViewById(R.id.maxTemperature);
        maxTemp.setHint("Max Temperature");

        new ForecastQuery().execute();

        progressBar.setVisibility(View.VISIBLE);
    }


    private class ForecastQuery extends AsyncTask<String, Integer, String>
    {
        String minTemp2;
        String maxTemp2;
        String currentTemp2;
        String iconName;
        Bitmap bm;

        @Override
        protected String doInBackground(String... params) {


            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
            URL url = null;
            ArrayList ar;
            String back ="";
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setDoInput(true);
                // Starts the query
            try {
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }


            InputStream in = null;
            try {
                in = conn.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                    XmlPullParser parser = Xml.newPullParser();
                    try {
                        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        parser.setInput(in, null);
                        parser.nextTag();
                        //ArrayList<String> entries = new ArrayList();
                        parser.require(XmlPullParser.START_TAG, null, "current");
                        while (parser.next() != XmlPullParser.END_TAG) {
                            if (parser.getEventType() != XmlPullParser.START_TAG) {
                                continue;
                            }
                            String name = parser.getName();
                            if (name.equals("temperature")) {
                                currentTemp2 = parser.getAttributeValue(null, "value").toString();

                                publishProgress(25);
                                minTemp2 = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                maxTemp2 = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            } else {
                                skip(parser);
                            }
                        }
                            //parser.nextTag();
                        while (parser.next() != XmlPullParser.END_TAG){
                            if (parser.getEventType() != XmlPullParser.START_TAG) {
                                continue;
                            }
                            String name2 = parser.getName();
                            if (name2.equals("weather")) {
                                iconName = parser.getAttributeValue(null, "icon");
                                publishProgress(100);
                                bm = getBitmap(iconName);
                            } else {
                                skip(parser);
                            }
                        }

                    } catch (XmlPullParserException e) {
                        e.printStackTrace();

                    } finally {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }



            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... value)
        {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            progressBar.setVisibility(View.INVISIBLE);

            currentTemp.setText(currentTemp2+currentTemp.getHint());

            minTemp.setText(minTemp2+minTemp.getHint());
            maxTemp.setText(maxTemp2+maxTemp.getHint());

            weatherImage.setImageBitmap(bm);
            weatherImage.setBackground(null);



        }


        protected void saveBitmap(InputStream bitmapStream, String fname)
        {
            try
            {
                Bitmap image = BitmapFactory.decodeStream(bitmapStream);
                FileOutputStream outputStream = openFileOutput(fname, Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
                Log.i(ACTIVITY_NAME, "Saved bitmap: " + fname);
            }
            catch (IOException e)
            {
                Log.e(ACTIVITY_NAME, "IOException while saving bitmap");
            }
        }

        protected Bitmap getBitmap(String name)
        {
            Bitmap image = null;
            InputStream bitmapStream;
            String fname = name + ".png";

            try
            {
                bitmapStream = openFileInput(fname);
            }
            catch (FileNotFoundException e)
            {
                Log.d(ACTIVITY_NAME, "FileNotFound, will download it now");
                saveBitmap(GET("http://openweathermap.org/img/w/" + fname), fname);
                bitmapStream = GET("http://openweathermap.org/img/w/" + fname);
            }

            if (bitmapStream != null)
            {
                image = BitmapFactory.decodeStream(bitmapStream);
            }

            return image;
        }

        InputStream GET(String url_addr)
        {
            InputStream inputStream = null;

            try
            {
                URL url = new URL(url_addr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                inputStream = urlConnection.getInputStream();
            }
            catch (MalformedURLException e)
            {
                Log.e(ACTIVITY_NAME, "URL is malformed");
            }
            catch (IOException e)
            {
                Log.e(ACTIVITY_NAME, "IOException when opening connection");
            }

            return inputStream;
        }


    }






    //----------------------------------------------------------------------------------------
    //**SKIP**//
    //----------------------------------------------------------------------------------------
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
