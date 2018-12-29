#include <SoftwareSerial.h>
#include <Servo.h>

Servo myservo; // servo name
String inString = "";
int pos = 0;

int bluetoothTx = 10; // bluetooth tx to 10 pin
int bluetoothRx = 11; // bluetooth rx to 11 pin

SoftwareSerial bluetooth(bluetoothTx, bluetoothRx);

void setup()
{
  myservo.attach(9);
  Serial.begin(9600);

  bluetooth.begin(9600);
}

void loop()
{
  if (bluetooth.available() > 0 )
  {
    int inChar = SerialBT.read();
    if (isDigit(inChar)) {
      // convert the incoming byte to a char and add it to the string:
      inString += (char)inChar;
    }
    // if you get a newline, print the string, then the string's value:
    if (inChar == '\n') {
      pos = inString.toInt();
      Serial.println(pos);
      myservo.write(servopos);

      inString = "";
    }
  }


}
