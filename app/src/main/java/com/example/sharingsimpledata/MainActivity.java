package com.example.sharingsimpledata;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider myShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Shared Massage Sample");
        shareIntent.setType("text/plain");
        myShareActionProvider.setShareIntent(shareIntent);

        return super.onCreateOptionsMenu(menu);
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            TextView textView = (TextView) findViewById(R.id.hello_text);
            textView.setText(sharedText);
        }
    }

    void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            ImageView imageView1 = (ImageView) findViewById(R.id.image_view_1);
            imageView1.setImageURI(imageUri);
        }
    }

    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            ImageView imageView1 = (ImageView) findViewById(R.id.image_view_1);
            imageView1.setImageURI(imageUris.get(0));
            ImageView imageView2 = (ImageView) findViewById(R.id.image_view_2);
            imageView2.setImageURI(imageUris.get(1));
        }
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