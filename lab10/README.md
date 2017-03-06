This an example of bluetooth spp library simple usage 

• Import the library to your workspace and include in to your android project:

For Eclipse ADT : Download this library and import into your workspace and include this library to your project.
For Android Studio : Use Gradle to download this library from Maven: add the line below to dependencies of file "build.gradle(Module:app)"

compile 'com.akexorcist:bluetoothspp:1.0.0'

• Declare permission for library

\<uses-permission android:name="android.permission.BLUETOOTH" />
\<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />

• Declare BluetoothSPP like this

BluetoothSPP bt = new BluetoothSPP(Context);

• Check if bluetooth is now available

if(!bt.isBluetoothAvailable()) {
    // any command for bluetooth is not available
}

• Check if bluetooth is not enable when activity is onStart

public void onStart() {
    super.onStart();
    if(!bt.isBluetoothEnable()) {
        // Do somthing if bluetooth is disable
    } else {
        // Do something if bluetooth is already enable
    }
}

• if bluetooth is ready call this method to start service

For connection with android device

bt.startService(BluetoothState.DEVICE_ANDROID);
Communicate with android

For connection with any microcontroller which communication with bluetooth serial port profile module

bt.startService(BluetoothState.DEVICE_OTHER);
Communicate with microcontroller

Bluetooth module with SPP

• Stop service with

bt.stopService();

• Intent to choose device activity

Intent intent = new Intent(getApplicationContext(), DeviceList.class);
startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
don't forget declare library activty like this

/<activity android:name="app.akexorcist.bluetoothspp.DeviceList" />

• After intent to choose device activity and finish that activity. You need to check result data on onActivityResult

public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
        if(resultCode == Activity.RESULT_OK)
            bt.connect(data);
    } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
        if(resultCode == Activity.RESULT_OK) {
            bt.setupService();
            bt.startService(BluetoothState.DEVICE_ANDROID);
            setup();
        } else {
            // Do something if user doesn't choose any device (Pressed back)
        }
    }
}

• If you want to send any data. boolean parameter is mean that data will send with ending by LF and CR or not. If yes your data will added by LF & CR

bt.send("Message", true);
or

bt.send(new byte[] { 0x30, 0x38, ....}, false);
• Listener for data receiving

bt.setOnDataReceivedListener(new OnDataReceivedListener() {
    public void onDataReceived(byte[] data, String message) {
        // Do something when data incoming
    }
});

• Listener for bluetooth connection atatus

bt.setBluetoothConnectionListener(new BluetoothConnectionListener() {
    public void onDeviceConnected(String name, String address) {
        // Do something when successfully connected
    }

    public void onDeviceDisconnected() {
        // Do something when connection was disconnected
    }

    public void onDeviceConnectionFailed() {
        // Do something when connection failed
    }
});

• Listener when bluetooth connection has changed

bt.setBluetoothStateListener(new BluetoothStateListener() {
    public void onServiceStateChanged(int state) {
        if(state == BluetoothState.STATE_CONNECTED)
            // Do something when successfully connected
        else if(state == BluetoothState.STATE_CONNECTING)
            // Do something while connecting
        else if(state == BluetoothState.STATE_LISTEN)
            // Do something when device is waiting for connection
        else if(state == BluetoothState.STATE_NONE)
            // Do something when device don't have any connection
    }
});

• Using auto connection

bt.autoConnect("Keyword for filter paired device");
• Listener for auto connection

bt.setAutoConnectionListener(new AutoConnectionListener() {
    public void onNewConnection(String name, String address) {
        // Do something when earching for new connection device
    }

    public void onAutoConnectionStarted() {
        // Do something when auto connection has started
    }
});

• Customize device list's layout by create layout which include

list view with id name = "list_devices"

button with id name = "button_scan"
