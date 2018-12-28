#include <SoftwareSerial.h>
#include <Servo.h> 

Servo myservo; // servo name

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
  if(bluetooth.available()> 0 ) 
  {
    int servopos = bluetooth.read(); 
    Serial.println(servopos); 
    myservo.write(servopos); 
  }


}