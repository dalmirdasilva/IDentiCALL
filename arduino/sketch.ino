#define BLINK_LED           A2
#define SIGNAL_LED          A3

#define BIT_0               3
#define BIT_1               4
#define BIT_2               5
#define BIT_3               6

#define MAX_NUMBER_LENGHT   15
#define LAST_DIGIT_MARK     ('0' + 15)
#define FIST_DISCARD_DIGITS 2

unsigned char phoneNumberBufferPointer = 0;
unsigned char phoneNumberBuffer[20];
volatile long lastRead = 0;
volatile unsigned char digit = 0;
unsigned long lastDigitTime = millis();
unsigned long maxAcceptableTimeBetweenDigits = 500;
unsigned char digitMap[16] = {
  48, // 00 -> '0'
  49, // 01 -> '1'
  50, // 02 -> '2'
  51, // 03 -> '3'
  52, // 04 -> '4'
  53, // 05 -> '5'
  54, // 06 -> '6'
  55, // 07 -> '7'
  56, // 08 -> '8'
  57, // 09 -> '9'
  58, // 10 -> ':'
  59, // 11 -> ';'
  60, // 12 -> '<'
  61, // 13 -> '='
  62, // 14 -> '>'
  63, // 15 -> '?'
}

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
  unsigned long rightNow = millis();
  if (rightNow - lastDigitTime > maxAcceptableTimeBetweenDigits) {
    discardBuffer();
  }
  consumeDigit(digit);
  lastDigitTime = rightNow;
}

void discardBuffer() {
  phoneNumberBufferPointer = 0;
}

void consumeDigit(unsigned char digit) {
  if (digit > 15) {
    return;
  }
  char character = digitMap[digit];
  phoneNumberBuffer[phoneNumberBufferPointer++] = character;
  checkNumberCompletion();
}

void checkNumberCompletion() {
  if (phoneNumberBufferPointer >= MAX_NUMBER_LENGHT || phoneNumberBuffer[phoneNumberBufferPointer - 1] == LAST_DIGIT_MARK) {
    Serial.write(&phoneNumberBuffer[FIST_DISCARD_DIGITS], phoneNumberBufferPointer - 1);
    Serial.write('\n');
    discardBuffer();
  }
}
