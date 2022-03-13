package at.aau.se2.einzelphase;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import at.aau.se2.einzelphase.network.TCP;

import java.io.IOException;
import java.util.concurrent.RejectedExecutionException;

public class MainActivity extends AppCompatActivity {

    private TextView tV_enter, tV_answerLabel, tV_serverAnswer;
    private EditText input;
    private Button button_send;

    private TCP tcp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNetwork();
        initLayout();
        initUiFunctions();
    }

    private void initNetwork() {
        tcp = new TCP(getResources().getString(R.string.server_domain), getResources().getInteger(R.integer.server_port));
    }

    private void initLayout() {
        tV_enter = findViewById(R.id.tV_EnterNumber);
        input = findViewById(R.id.edit_EnterNumber);
        tV_answerLabel = findViewById(R.id.tv_answerLabel);
        tV_serverAnswer = findViewById(R.id.tV_serverAnswer);
        button_send = findViewById(R.id.button_send);
    }

    private void initUiFunctions() {
        button_send.setOnClickListener((view) -> tcp.writeThenRead(input.getText().toString(),
                (response) -> tV_serverAnswer.setText(response),
                (error) -> {
                    if (error instanceof IOException) {
                        tV_serverAnswer.setText(R.string.network_error);
                        Log.e("Network Error", getResources().getString(R.string.network_error));
                    } else if (error instanceof RejectedExecutionException) {
                        tV_serverAnswer.setText(R.string.internal_error);
                        Log.e("Internal Error", getResources().getString(R.string.internal_error));
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tcp.shutdownExecutor();
    }

}