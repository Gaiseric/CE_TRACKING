#include <SevSeg.h>
SevSeg sevseg;
int pinBuzzer = 13;
String Mensaje="";
void setup(){
  byte Numdigits = 4;
  byte Comunes[]={2,3,4,5};
  byte Segmentos[]={6,7,8,9,10,11,12,1};
  byte Tipo=COMMON_ANODE;
  sevseg.begin(Tipo,Numdigits,Comunes,Segmentos);

  Serial.begin(9600);
  }

void loop(){
  
  while(Serial.available()>0){
      
    Mensaje=Mensaje+Decimal_to_ASCII(Serial.read());
        sonido();
  }
  sevseg.setNumber(Mensaje.toInt(),1);
  sevseg.refreshDisplay();


}
  
void sonido(){
  //generar tono de 440Hz durante 1000 ms
  tone(pinBuzzer, 440);
  delay(1000);
  //detener tono durante 500ms  
  noTone(pinBuzzer);
  delay(500);
  //generar tono de 523Hz durante 500ms, y detenerlo durante 500ms.
  tone(pinBuzzer, 523, 300);
  delay(500);
}

char Decimal_to_ASCII(int entrada){
  char salida=' ';
  switch(entrada){
    case 48:  salida='0'; 
    break; 
    case 49:  salida='1'; 
    break; 
    case 50:  salida='2'; 
    break; 
    case 51:  salida='3'; 
    break; 
    case 52:  salida='4'; 
    break; 
    case 53:  salida='5'; 
    break; 
    case 54:  salida='6'; 
    break; 
    case 55:  salida='7'; 
    break; 
    case 56:  salida='8'; 
    break; 
    case 57:  salida='9'; 
    break;  
  }
  return salida;
}
