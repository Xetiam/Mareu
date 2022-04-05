package com.example.mareu.ui.add_reservation;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class TestApi extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {

            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("https://api.twitter.com/2/users/1962699757/tweets?max_results=5&expansions=attachments.media_keys&tweet.fields=&user.fields=&media.fields=&place.fields=&poll.fields=")
                    .method("GET", null)
                    .addHeader(
                            "Authorization",
                            "Bearer AAAAAAAAAAAAAAAAAAAAAGzhaQEAAAAAKQU%2FgZ3dSqaNgI79SK56a0zKp9Q%3DQblQ2qyKOQvheAAzcYzzbiHuzlturVN7h6gWLDpJQpGpiIP0jP"
                    )
                    .addHeader("Cookie", "guest_id=v1%3A164788026555741755; BCSI-CS-c1fc0bbdf7399cc9=1")
                    .build();
            try {
                String response = client.newCall(request).execute().body().string();
                System.out.println(response);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return null;
    }
}
