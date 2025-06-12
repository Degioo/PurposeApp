# PurposeApp

**PurposeApp** è un'app Android sviluppata in Kotlin per aiutare gli utenti a creare, condividere e tenere traccia dei propri propositi giornalieri o settimanali.

## 🧠 Funzionalità principali

- ✅ Registrazione e login tramite Firebase Authentication
- 📝 Creazione di propositi con titolo, giorni della settimana, orario
- 📍 Possibilità di associare la posizione corrente al proposito
- 👥 Condivisione con altri utenti (solo se registrati)
- 🔔 Notifiche personalizzabili (5, 10, 30 minuti prima dell'orario scelto)
- 🗺️ Mappa con localizzazione e possibilità di aprire in Google Maps

## 🛠️ Tecnologie utilizzate

- **Kotlin**
- **Android Studio**
- **Firebase Authentication**
- **Firebase Firestore**
- **Google Maps & Location Services**

## ▶️ Come eseguire l’app

1. Clona o scarica il repository su Android Studio
2. Inserisci la tua chiave API di Google Maps in `AndroidManifest.xml`:

```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="LA_TUA_API_KEY"/>
```

3. Verifica di avere il file `google-services.json` nella cartella `app/`
4. Esegui l’app su un dispositivo o emulatore con accesso a internet

## 📷 Screenshot
- `![Immagine 2025-06-12 222443](https://github.com/user-attachments/assets/693899b5-48c7-45fc-acea-e2214c384f2d)
- ![Immagine 2025-06-12 222505](https://github.com/user-attachments/assets/d53e8025-80d6-4040-b733-8c464dded83f)
- ![Immagine 2025-06-12 222533](https://github.com/user-attachments/assets/15d364eb-28f2-4527-b9bf-db271603e261)

## 🗂️ Struttura del progetto (principali file)

- `MainActivity.kt`: gestione tab e permessi
- `LoginActivity.kt`: login/registrazione utente
- `HomeFragment.kt`: visualizzazione propositi
- `CreatePurposeFragment.kt`: creazione propositi
- `PurposeAdapter.kt`: adapter per RecyclerView
- `ReminderReceiver.kt`: gestione notifiche
- `MapsActivity.kt`: mappa dettagliata

## 👨‍💻 Autore

Sviluppato da **Lorenzo De Giovanni** per il progetto universitario di **Dispositivi Mobili**.

## 🚀 Idee future

- Aggiunta IA per suggerire propositi personalizzati
- Statistiche sui propositi completati
- Supporto a immagini e note
