package servo.project.miwtoo.servo180;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.harjot.crollerTest.Croller;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;


public class MainActivity extends Activity {
    BluetoothSPP bt;
    TextView txtNum;
    int pos;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linearLayout);
        final ImageButton btnConnect = (ImageButton)findViewById(R.id.imageButton4);


        bt = new BluetoothSPP(this);

        if(!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext(), "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) { Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext(), "Connected to " + name + "\n" + address, Toast.LENGTH_SHORT).show();


                mainLayout.setVisibility(View.VISIBLE);
                btnConnect.setVisibility(View.INVISIBLE);
            }

            public void onDeviceDisconnected() {
                Toast.makeText(getApplicationContext(), "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() {
                Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });


        btnConnect.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                if(bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        bt.stopService();
    }

    public void onStart() {

        super.onStart();
        if(!bt.isBluetoothEnabled()) {
            Intent intent= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if(!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);

                setup();
            }
        }
    }

    public void setup() {
        Button btn0 = (Button)findViewById(R.id.button7);
        Button btn45 = (Button)findViewById(R.id.button9);
        Button btn90 = (Button)findViewById(R.id.button8);
        Button btn135 = (Button)findViewById(R.id.button);
        Button btn180 = (Button)findViewById(R.id.button6);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        txtNum = (TextView) findViewById(R.id.textView4);
        final Croller croller = (Croller) findViewById(R.id.croller);

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener(){

            @Override
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener(){

            @Override
            public void onDataReceived(byte[] data, String message) {

            }
        });

        croller.setOnProgressChangedListener(new Croller.onProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                pos = progress;
                seekBar.setProgress(pos);
                bt.send(pos + "", true);
                txtNum.setText(pos+"");

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pos = progress;
                croller.setProgress(pos);
                bt.send(pos + "", true);
                txtNum.setText(pos+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn0.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                //bt.send("0", true);
                seekBar.setProgress(0);
                croller.setProgress(0);
                //Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
                txtNum.setText("0");

            }
        });
        btn45.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                //bt.send("45", true);
                seekBar.setProgress(45);
                croller.setProgress(45);
                //Toast.makeText(getApplicationContext(), "45", Toast.LENGTH_SHORT).show();
                txtNum.setText("45");

            }
        });
        btn90.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                //bt.send("90", true);
                seekBar.setProgress(90);
                croller.setProgress(90);
                //Toast.makeText(getApplicationContext(), "90", Toast.LENGTH_SHORT).show();
                txtNum.setText("90");

            }
        });
        btn135.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                //bt.send("135", true);
                seekBar.setProgress(135);
                croller.setProgress(135);
                //Toast.makeText(getApplicationContext(), "135", Toast.LENGTH_SHORT).show();
                txtNum.setText("135");

            }
        });
        btn180.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                //bt.send("180", true);
                txtNum.setText("180");
                croller.setProgress(180);
                seekBar.setProgress(180);
                //Toast.makeText(getApplicationContext(), "180", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}