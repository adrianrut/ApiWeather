# WeatherAPI

Mała aplikacja webowa MVC w `Java` i `Spring Boot`, która pobiera aktualną pogodę dla podanego miasta z OpenWeather API i wyświetla wynik w widoku `Thymeleaf`.

## Stack technologiczny

- Java 17
- Spring Boot 3
- Spring MVC
- Thymeleaf
- RestTemplate
- Maven
- JUnit 5

## Jak działa aplikacja

Przepływ użytkownika:

1. Użytkownik otwiera stronę główną `/`.
2. W formularzu wpisuje nazwę miasta.
3. Formularz wysyła żądanie `POST /city`.
4. Aplikacja przekierowuje na `/weather?city=Warsaw`.
5. `WeatherService` pobiera dane z OpenWeather API.
6. Widok `weather.html` renderuje temperaturę, kraj, ciśnienie i prędkość wiatru.

## Konfiguracja API key

Aplikacja korzysta ze zmiennej środowiskowej `OPENWEATHER_API_KEY`.

Przykład dla macOS / Linux:

```bash
export OPENWEATHER_API_KEY=twoj_klucz_api
```

Przykład dla Windows PowerShell:

```powershell
$env:OPENWEATHER_API_KEY="twoj_klucz_api"
```

Jeśli klucz nie jest ustawiony, aplikacja nie przerywa startu. Zamiast tego przy próbie pobrania pogody wyświetli stronę błędu z informacją o brakującej konfiguracji.

## Uruchomienie projektu

Uruchom aplikację:

```bash
./mvnw spring-boot:run
```

Po starcie otwórz:

```text
http://localhost:8080/
```

## Uruchomienie testów

```bash
./mvnw test
```

## Przykładowe użycie

1. Wejdź na stronę główną.
2. Wpisz `Warsaw`.
3. Zobacz wynik pod adresem:

```text
http://localhost:8080/weather?city=Warsaw
```

## Obsługa błędów

Aplikacja obsługuje podstawowe przypadki błędów:

- brak podanego miasta
- nieznane miasto zwrócone przez OpenWeather API
- brak klucza API
- problemy z połączeniem lub błędy zewnętrznego API

W takich przypadkach renderowany jest widok `error.html`.
