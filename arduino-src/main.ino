#include <Servo.h> 
 
#define sizeArray 6
#define led7 7
#define led6 6
#define led5 5
#define led4 4
#define led3 3
#define led2 2
 
#define ldr0 0
#define ldr1 1
#define ldr2 2
#define ldr3 3
#define ldr4 4
#define ldr5 5
 
Servo servoEntry;
int photocellReading;
int ledPins[] = {led2, led3, led4, led5, led6, led7}; // digital
int ldrPins[] = {ldr0, ldr1, ldr2, ldr3, ldr4, ldr5}; // analogico
int servoEntryPos = 30;
String texto;
String valor;
int qtdVagas = 0;
 
void setup() {                  
  	initialize();
  	Serial.print("[\a]");
  	delay(100);
  
	Serial.print("[\f\f\f\f\f\f]");
  	delay(100);
  
  	Serial.print("[\r]");
	delay(100);
}
 
void initialize() {
  	Serial.begin(9600);
  	Serial3.begin(9600);
  	servoEntry.attach(8);
  	servoEntry.write(servoEntryPos);
  	initPinsLed(ledPins);
}
 
void initPinsLed(int ledPins[] ) {
 	for (int i =0 ; i < sizeArray; i++){
   		pinMode(ledPins[i], OUTPUT);
   		digitalWrite(ledPins[i], HIGH);
 	}
}
 
void loop() {
  	validate();
  	
  	if(Serial3.available() > 0) {
    	readCard();
    	Serial.print(valor);
    	texto = "";
    	valor = "";
    	openDoor();
  	}
  	testVacancy();
}
 
void validate() {
  	int vagas = 0;
  
	for (int i =0 ; i < 2; i++){
    	int val = digitalRead(ledPins[i]);
        if ( val == 1 ) {
      		vagas += 1;
   		 } 
	}
  
  	if ( qtdVagas !=vagas ) {
    	qtdVagas = vagas;
    	enviaLCD(String(vagas));
	}
}
 
void testVacancy() {
    seeDoor(ldrPins[0], 0);
    seeDoor(ldrPins[1], 1);
}
 
void seeDoor(int pin, int key) {
  	int valorLdr = analogRead(pin);
  	int pino = pin;
  	
  	if (valorLdr < 550  ) {
    	apagarLed(pino, key);
  	} else {
    	acenderLed(pino, key);    
  	}
}
 
void readCard() {
  	for (int i = 0 ; Serial3.available() > 0; i++) {      
    	if (i >= 7 && i <=9) {
      		valor += Serial3.read();
      		texto += "+";
    	} else {
      		texto += Serial3.read();
    	}      
    delay(10);
	} 
}
 
void openDoor() {
  	if(servoEntryPos == 30) {
    	for(servoEntryPos = 30; servoEntryPos < 150; servoEntryPos += 1) {
      		servoEntry.write(servoEntryPos);
      		delay(15);
    	}
    	delay(5000);
    	closeDoor();
  	}
}
 
void closeDoor() {
	if(servoEntryPos == 150) {
    	for(servoEntryPos = 150; servoEntryPos>=31; servoEntryPos-=1) {                                
      		servoEntry.write(servoEntryPos);
      		delay(15);
    	}
  	}
}
 
void enviaLCD(String textoLCD) {
	Serial.print("[\a]");
  	delay(100);
  	Serial.print("[");
  	Serial.print(textoLCD);
  	Serial.print("]");
  	delay(100);
}
 
void apagarLed(int pinLed, int key) {
  	digitalWrite(ledPins[key], LOW);
  	//Serial.print(ledPins[key] + ":1");
}
 
void acenderLed(int pinLed, int key) {
  	digitalWrite(ledPins[key], HIGH);
  	//Serial.print(ledPins[key] + ":0");    
}