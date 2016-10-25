package com.example.sharingsimpledata;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendText(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }

    public void sendBinary(View view) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM,
                Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.image_1));
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
    }

    public void sendMultiple(View view) {
        ArrayList<Uri> imageUris = new ArrayList<Uri>();
        imageUris.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.image_1));
        imageUris.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.image_2));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "Share images to.."));
    }
}