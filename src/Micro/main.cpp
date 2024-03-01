#include <Arduino.h>

#include <WiFi.h>
#include <HTTPClient.h>
#include <DHT.h>
#include <ArduinoJson.h>

#define DHT_PIN 14         
#define DHT_TYPE DHT22
#define ID_APPAREIL 1 // on renseigne l'id du microcontrolleur fournit par l'application
#define NBRE_CAPTEUR 1
#define NBRE_CHANNEL1 2
#define NBRE_CHANNEL2 0
#define ID_CAPTEUR 2  //on renseigne l'id du capteur fournit par l'application
#define ID_CHANNEL11 1  // //on renseigne les id des channels fournit par l'application  
#define ID_CHANNEL12 2    

DHT dht(DHT_PIN, DHT_TYPE);

const char* ssid = "Wifi Rogers";      
const char* password = "Bathurst2024"; 
const char* serverUrl = "http://192.168.1.104/api"; // URL de l'endpoint de réception HTTP

void setup() {
  Serial.begin(115200);
  delay(100);
  
  // Connexion au réseau WiFi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connexion au WiFi en cours...");
  }
  Serial.println("Connecté au réseau WiFi");
}

void loop() {

  // Lecture de  température et humidité

  float temperature = dht.readTemperature(); 
  float humidity = dht.readHumidity();       
  
  // Vérification de la lecture réussie
  if (isnan(temperature) || isnan(humidity)) {
    Serial.println("Erreur lors de la lecture du capteur DHT !");
    return;
  }
  
  Serial.print("Température: ");
  Serial.print(temperature);
  Serial.print("°C, Humidité: ");
  Serial.print(humidity);
  Serial.println("%");
  
  

  // Création de l'objet JSON 1
  StaticJsonDocument<200> jsonDocument1;
  jsonDocument1["idCapteur"] = ID_CAPTEUR;
  jsonDocument1["idChannel"] = ID_CHANNEL11;
  jsonDocument1["readValue"] = temperature;

  // Création de l'objet JSON 2
  
  StaticJsonDocument<200> jsonDocument2;
  jsonDocument2["idCapteur"] = ID_CAPTEUR;
  jsonDocument2["idChannel"] = ID_CHANNEL12;
  jsonDocument2["readValue"] = humidity;

  // Sérialisation de l'objet JSON
  String jsonString1, jsonString2;
  serializeJson(jsonDocument1, jsonString1);
  serializeJson(jsonDocument2, jsonString2);
  
  // Envoi des données via HTTP POST
  HTTPClient http;
  http.begin(serverUrl);
  http.addHeader("Content-Type", "application/json");
  
  int httpResponseCode = http.POST(jsonString1);
  if (httpResponseCode > 0) {
    String response = http.getString();
    Serial.println("Réponse du serveur : " + response);
  } else {
    Serial.println("Erreur lors de l'envoi de la requête HTTP !");
  }

  httpResponseCode = http.POST(jsonString2);
  if (httpResponseCode > 0) {
    String response = http.getString();
    Serial.println("Réponse du serveur : " + response);
  } else {
    Serial.println("Erreur lors de l'envoi de la requête HTTP !");
  }
  
  http.end();
  
  delay(5000); // Attendre une minute avant la prochaine lecture et envoi
}
