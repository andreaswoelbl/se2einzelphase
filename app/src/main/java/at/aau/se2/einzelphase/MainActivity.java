package at.aau.se2.einzelphase;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import at.aau.se2.einzelphase.feature.MatMathOperations;
import at.aau.se2.einzelphase.network.TCP;

import java.io.IOException;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author Andreas Wölbl
 */
public class MainActivity extends AppCompatActivity {

    private TextView tV_serverAnswer, tV_checkSumBinary;
    private EditText input;
    private Button button_send, button_calculateChecksum;

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
        input = findViewById(R.id.edit_EnterNumber);
        tV_serverAnswer = findViewById(R.id.tV_serverAnswer);
        button_send = findViewById(R.id.button_send);
        tV_checkSumBinary = findViewById(R.id.tV_checkSumBinary);
        button_calculateChecksum = findViewById(R.id.button_calculateChecksum);
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

        button_calculateChecksum.setOnClickListener((view) -> {
            int checkSum = MatMathOperations.checksumOfMatriculationNumber(input.getText().toString());
            String binaryChecksum = checkSum == -1 ? getResources().getString(R.string.unknown_matriculation_number) : Integer.toBinaryString(checkSum);
            Log.d("Checksum", "Calculated checksum of matriculation number: " + checkSum + ", binary: " + binaryChecksum);
            tV_checkSumBinary.setText(binaryChecksum);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tcp.shutdownExecutor();
    }

}