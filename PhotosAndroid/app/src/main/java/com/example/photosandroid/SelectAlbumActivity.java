package com.example.photosandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * The {@code SelectAlbumActivity} class is an activity class allowing the user to select an album to move a photo to.
 * <p>
 * @author Bansari Mehta (netid: bm844) and Anvay Patel (netid: acp205)
 */
public class SelectAlbumActivity extends AppCompatActivity {

    private UserData userData;
    private ArrayList<Album> myAlbums;
    private ArrayList<String> albumList;
    private ArrayAdapter<String> adapter;
    private int currAlbumIndex;
    Album currAlbum;
    Photo photo;

    /**
     * Called when the activity is first created. Initializes the UI and sets up event listeners.
     *
     * @param savedInstanceState The saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_select);
        userData= UserData.getUserdata(getApplicationContext());
        currAlbum = userData.getAlbumList().get((int) getIntent().getSerializableExtra("albumPos"));
        photo=currAlbum.getPhoto((int) getIntent().getSerializableExtra("photoPos"));
        currAlbumIndex = (int) getIntent().getSerializableExtra("albumPos");
        if (albumList == null || albumList.isEmpty()) {
            albumList = new ArrayList<>();
            myAlbums = userData.getAlbumList();
            for (Album a : myAlbums) {
                albumList.add(a.getName());
            }

        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, albumList);

        ListView listView = findViewById(R.id.SelectAlbumlistview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                (p, v, pos, id) -> moveAlbum(pos)
        );


    }

    /**
     * Move the photo to the selected album.
     *
     * @param index The index of the selected album.
     */
    private void moveAlbum(int index){
        if(myAlbums.get(index).equals(currAlbum)){
            Toast.makeText(this, "photo is already in this album", Toast.LENGTH_SHORT).show();
            return;
        }
        currAlbum.getPhotos().remove(photo);
        myAlbums.get(index).addPhoto(photo);
        UserData.store(getApplicationContext());
        Intent intent= new Intent(this,AlbumViewActivity.class);
        intent.putExtra("albumPos",currAlbumIndex);
        startActivity(intent);
    }
}
