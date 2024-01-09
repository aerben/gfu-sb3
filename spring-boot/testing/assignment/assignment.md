### Übung 1: Erstellen und Testen eines einfachen Repository mit DataJpaTest

Erstelle einen Test für das `PersonRepository`, indem du die `@DataJpaTest` Annotation verwendest. Teste dabei das Speichern, Abrufen und Löschen von Personen-Objekten in der Datenbank.

### Übung 2: Testen eines Controller Endpoints mit WebMvcTest

Teste den `getAllPersons()` Endpunkt des `PersonController` mit der `@WebMvcTest` Annotation. Mocke dafür das `PersonService` und gib eine Liste von Personen-Objekten als Antwort zurück.

### Bonusaufgabe: Hinzufügen und Testen weiterer Controllermethoden

Füge in `PersonController` weitere Endpunkte wie z.B. für `DELETE` und `PUT` hinzu und teste sie.