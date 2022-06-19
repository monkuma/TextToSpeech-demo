package com.example.texttospeech_demo;

import android.annotation.SuppressLint;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;


import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener, TextToSpeech.OnInitListener{



    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tts = new TextToSpeech(this, this);

        Button ttsButton = findViewById(R.id.button_tts);
        ttsButton.setOnClickListener(this);
    }

    public void onInit(int status){
        if(TextToSpeech.SUCCESS == status){
            Log.d("Debug :", "initialized");
        }else{
            Log.e("DEBUG :", "Failed to initialize");
        }
    }


    @Override
    public void onClick(View view) {
        speechText();
    }

    private void shutDown(){
        if(null != tts){
            tts.shutdown();
        }
    }

    private void speechText(){
        @SuppressLint("WrongViewCast") EditText editor = findViewById(R.id.edit_text);
        editor.selectAll();
        String str = editor.getText().toString();

        if(0 < str.length()){
            if(tts.isSpeaking()){
                tts.stop();
                return;
            }
            setSpeechRate();
            setSpeechPitch();
            tts.speak(str, TextToSpeech.QUEUE_FLUSH, null, "MessageID");
            setTtsListener();
        }
    }

    private void setSpeechRate(){
        if(null != tts){
            tts.setSpeechRate((float) 1.0);
        }
    }
    private void setSpeechPitch(){
        if(null != tts){
            tts.setPitch((float) 1.0);
        }
    }

    private void setTtsListener(){
        int listenerResult =
                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String s) {
                        Log.d("debug","progress on Start " + s);

                    }

                    @Override
                    public void onDone(String s) {
                        Log.d("debug","progress on Done " + s);

                    }

                    @Override
                    public void onError(String s) {
                        Log.d("debug","progress on Error " + s);

                    }
                });
        if(listenerResult != TextToSpeech.SUCCESS){
            Log.e("debug", "failed to add utterance progress listener");
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        shutDown();
    }


}