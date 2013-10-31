#define BLINK_LED         A2
#define SIGNAL_LED        A3

#define BIT_0             3
#define BIT_1             4
#define BIT_2             5
#define BIT_3             6

unsigned char phoneNumberBuffer[20];
volatile long lastRead = 0;
volatile unsigned char digit = 0;

void setup() {
  Serial.begin(9600);
  Serial.println("OK");
  pinMode(BLINK_LED, OUTPUT);
  pinMode(SIGNAL_LED, OUTPUT);
  pinMode(BIT_0, INPUT);
  pinMode(BIT_1, INPUT);
  pinMode(BIT_2, INPUT);
  pinMode(BIT_3, INPUT);
  attachInterrupt(0, readDigit, RISING);
}

void loop() {
  delay(995);
  digitalWrite(BLINK_LED, 1);
  delay(5);
  digitalWrite(BLINK_LED, 0);
}

void readDigit() {
  digit = 0;
  digitalWrite(SIGNAL_LED, HIGH);
  if (digitalRead(BIT_0)) {
    digit |= 1;
  }
  if (digitalRead(BIT_1)) {
    digit |= 2;
  }
  if (digitalRead(BIT_2)) {
    digit |= 4;
  }
  if (digitalRead(BIT_3)) {
    digit |= 8;
  }
  processDigit(digit);
  digitalWrite(SIGNAL_LED, LOW);
}

void processDigit(unsigned char digit) {
  Serial.println(digit, DEC);
  
}
