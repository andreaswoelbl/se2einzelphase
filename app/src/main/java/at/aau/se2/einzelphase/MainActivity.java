package at.aau.se2.einzelphase;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.concurrent.RejectedExecutionException;

public class MainActivity extends AppCompatActivity {

    private TextView tV_enter, tV_answerLabel, tV_serverAnswer;
    private EditText input;
    private Button button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        initUiFunctions();
    }

    private void initLayout() {
        tV_enter = findViewById(R.id.tV_EnterNumber);
        input = findViewById(R.id.edit_EnterNumber);
        tV_answerLabel = findViewById(R.id.tv_answerLabel);
        tV_serverAnswer = findViewById(R.id.tV_serverAnswer);
        button_send = findViewById(R.id.button_send);
    }

    private void initUiFunctions() {
        button_send.setOnClickListener((view) -> {

        });
    }

}