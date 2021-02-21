package com.example.mediaplayerdemo;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private ImageButton playButton,preButton,nextButton;
    private  TextView songTitle;
    private MediaPlayer mediaPlayer;
    SeekBar seekBar;
    //private  Runnable runnable;
    int currentIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton=findViewById(R.id.nextButtonId);
        playButton = findViewById(R.id.playbuttonId);
        preButton=findViewById(R.id.preButtonId);
        songTitle=findViewById(R.id.songId);
        seekBar = findViewById(R.id.seekbarId);



        ///creating on array list to store our songs
        final ArrayList<Integer> songs = new ArrayList<>();

        songs.add(0, R.raw.christina_perri_a_thousand_years);
        songs.add(1, R.raw.ed_sheeran_happier);
        songs.add(2, R.raw.hero_enrique_iglesias);
        songs.add(3, R.raw.i_m_yours_jason_mraz);
        songs.add(4, R.raw.i_will_be_loving_you_original_by_chester_see);
        songs.add(5, R.raw.james_arthur_say_you_will_not_let_go);
        songs.add(6, R.raw.john_denver_annies_song);
        songs.add(7, R.raw.john_legend_all_of_me_qoret_com);
        songs.add(8, R.raw.just_the_way_you_are_bruno_mars);
        songs.add(9, R.raw.marry_you_bruno_mars);
        songs.add(10, R.raw.michael_jackson_you_are_not_alone);
        songs.add(11, R.raw.michael_learns_tock_take_me_to_your_heart);
        songs.add(12, R.raw.where_are_you_now_chester_see);




               ////initializing media player////
        mediaPlayer = MediaPlayer.create(getApplicationContext(),songs.get(currentIndex));




        playButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        seekBar.setMax(mediaPlayer.getDuration());
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        } else {

            mediaPlayer.start();
            playButton.setImageResource(R.drawable.ic_baseline_pause_24);
        }

        songnames();


      }
    });
        ///OnClickListener////

    preButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (mediaPlayer != null) {
            playButton.setImageResource(R.drawable.ic_baseline_pause_24);
        }
        if (currentIndex > 0) {
            currentIndex=currentIndex-1;
        } else {
            currentIndex = songs.size()-1;
        }

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(currentIndex));
        mediaPlayer.start();

        songnames();

    }

   });

    nextButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (mediaPlayer != null) {
            playButton.setImageResource(R.drawable.ic_baseline_pause_24);
        }
        if (currentIndex < songs.size()-1) {
            currentIndex=currentIndex+1;
        } else {
            currentIndex = 0;
        }

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();

        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(currentIndex));
        mediaPlayer.start();

        songnames();

    }

   });


    }


   private void songnames()
   {
    if (currentIndex == 0) {
        songTitle.setText("1.A Thousand Years by Christina perri");
    }
    if (currentIndex == 1) {
        songTitle.setText("2.Happier by Ed Sheeran .");
    }
    if (currentIndex == 2) {
        songTitle.setText("3.Hero by Enrique Iglesias.");
    }
    if (currentIndex == 3) {
        songTitle.setText("4.I m Yours by Jason Mraz .");
    }
    if(currentIndex==4)
    {
        songTitle.setText("5.I Will Be Loving You Original by Chester See .");
    }
    if(currentIndex==5)
    {
        songTitle.setText("6.Say You Won't Let Go by James Arthur.");
    }
    if(currentIndex==6)
    {
        songTitle.setText("7.Annies by John Denver.");
    }
    if(currentIndex==7)
    {
       songTitle.setText("8.All of Me by John_Legend. ");
    }
    if(currentIndex==8)
    {
        songTitle.setText("9.Just The Way You Are by Bruno Mars . ");
    }
    if(currentIndex==9)
    {
        songTitle.setText("10.Marry You by Bruno Mars.");
    }
    if(currentIndex==10)
    {
        songTitle.setText("11.You Are Not Alone by Michael Jackson . ");
    }
    if(currentIndex==11)
    {
        songTitle.setText("12.To Rock Take Me To Your Heart by Michael Learns ");
    }
    if(currentIndex==12)
    {
        songTitle.setText(" 13.Where Are You Now by Chester See. ");
    }


       ///////seekbar //////////
       seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

               if (fromUser) {
                   mediaPlayer.seekTo(progress);
                   seekBar.setProgress(progress);
               }
           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {

           }
       });


       ///seekbar duration/////
    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {

            seekBar.setMax(mediaPlayer.getDuration());
            mediaPlayer.start();

        }
     });

         ///seekbar for progress///

    new Thread(new Runnable() {
        @Override
        public void run() {
            while (mediaPlayer != null) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        Message message = new Message();


                        message.what = mediaPlayer.getCurrentPosition();

                        hendler.sendMessage(message);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }).start();

   }

    @SuppressLint({"HandlerLeak"})
    Handler hendler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            seekBar.setProgress(msg.what);
        }
    };


    @Override
    protected void onDestroy() {
        if(mediaPlayer!=null&&mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (mediaPlayer == null) ;
        }
        super.onDestroy();
    }
}