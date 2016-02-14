package com.example.rupesh.alfred;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.speech.RecognizerIntent;
import java.util.List;
import java.text.NumberFormat;

public class MainActivity extends Activity {

    private TextView mTextView;
    private static final int SPEECH_RECOGNIZER_REQUEST_CODE = 0;
    private String request = null;
    private static final String TAG = "wearMainActivity";
    private static final String apology = "Pardon me hearing, can you repeat ?";
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                mButton = (Button)stub.findViewById(R.id.button);
                mButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startSpeechRecognition();
                        }
                    });

            }
        });


    }

    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_RECOGNIZER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SPEECH_RECOGNIZER_REQUEST_CODE) {

            if(resultCode == RESULT_OK) {

                List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                request = results.get(0);
                Log.d(TAG, "voice to text = " + request);
                mTextView.setText(request);

            }else {
                Log.d(TAG, apology);
                mTextView.setText(apology);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
