
#include "BluetoothSerial.h"

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

int LED = 23;
String inString = "";
int pos = 0;
BluetoothSerial SerialBT;

void setup() {
  Serial.begin(115200);
  SerialBT.begin("ESP32test"); //Bluetooth device name
  Serial.println("The device started, now you can pair it with bluetooth!");

  pinMode(LED, OUTPUT);
}

void loop() {
  //  digitalWrite(LED, HIGH);
  //  delay(500);
  //  digitalWrite(LED, LOW);
  //  delay(500);

  if (SerialBT.available()) {
    int inChar = SerialBT.read();
    if (isDigit(inChar)) {
      // convert the incoming byte to a char and add it to the string:
      inString += (char)inChar;
    }
    // if you get a newline, print the string, then the string's value:
    if (inChar == '\n') {
      pos = inString.toInt();
      Serial.println(pos);
      SerialBT.write(pos);

      if (pos == 180) {
        digitalWrite(LED, HIGH);
      }
      else if (pos == 0) {
        digitalWrite(LED, LOW);
      }

      inString = "";
    }
  }
}
