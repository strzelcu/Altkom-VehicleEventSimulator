1. Wybór technologii:

    Do realizacji projektu wybrałem następujące technologie:

        - Frameworki: Spring, Hibernate, JPA,
        - Serwer aplikacji: TOMCAT (aplikacja była również testowana na Payara),
        - Interfejs webowy: JSP,
        - Wyświetlanie mapy: Leaflet, GeoJson, OpenStreetMap,
        - Dodatkowe biblioteki:

            * https://github.com/graphhopper/directions-api-java-client
              Biblioteka została użyta w celu wykorzystania API
              GraphHoppera dzięki któremu udało mi się rozwiązać problem
              geokodowania. Wykorzystałem tą bibliotekę również do skorzystania
              z algorytmu routingu, dzięki któremu pobierałem punkty trasy
              między dwoma geolokacjami,

            * https://github.com/opendatalab-de/geojson-jackson
              Biblioteka została wykorzystana w celu przygotowania pliku GeoJson,
              dzięki któremu zdarzenia zostały wyświelone na mapie. Markery obrazujące
              zdarzenia po kliknięciu wyświetlają istotne informacje zawarte w pliku.

2. Plik konfiguracyjny resources.configuration.properties zawiera wszystkie niezbędne
   dane potrzebne do działania aplikacji. Wszystkie dane konfiguracyjne zostały
   ustawione zgodnie z wytycznymi.

3. Założyłem, że zdarzenie nie związane z ruchem pojazdu przedstawię w postaci ogólnej
   jako klasę AdditionalWorkEvent. Moim zdaniem w działającym rozwiązaniu produkcyjnym
   należałoby dla każdego zdarzenia nie związanego z ruchem pojazdów utworzyć nową klasę
   dziedziczącą po abstrakcyjnej klasie Event. Każde zdarzenie pracy dodatkowej ma swoje
   indywidualne cechy, które nie powinny być uogólniane.

4. Encje w modelu zostały powiązane w taki sposób, aby usunięcie obiektu klasy usuwało
   również obiekty zależne od niego. Usunięcie obiektu klasy Car usunie z bazy
   wszystkie przypisane do niego obiekty klasy Event. Usunięcie obiektu klasy Event
   spowoduje usunięcie wszystkich przypisanych do niego obiektów klasy GeoPoint.

5. Do pliku README.txt dołączyłem kilka zrzutów ekranu z testów działania aplikacji, które
   wykonałem, aby wstępnie zobrazować działanie aplikacji. Znajdują się w katalogu screenshots
   w głównym katalogu projektu.

6. Każda klasa zawiera wytłumaczenie do czego służy. Działanie bardziej złożonych metod
   zostało również opisane w klasach.


7. Pozostałe problemy do rozwiązania:

    * Problem polskich znaków diaktyrcznych wynikający prawdopodobnie z kodowania
      zapisanych plików. Starałem się rozwiązać ten problem ale nie podołałem.
      Podobnie jest z wpisywaniem polskich znaków w formularzach *.JSP. Wszystkie
      polskie znaki są wyświetlane jako krzaki.

    * Brak możliwości importu i exportu zdarzeń w formie usług sieciowych REST.
      GeoJson jest implementowany na sztywno w skrypt JS za pomocą ShowMapController.
      Należy zaimplementować usługę REST a następnie w JS wywołać żądanie do usługi w
      celu otrzymania danych GeoJson. Niestety nie zdążyłem wykonać tej funkcjonalności.

    * Aby symulator lepiej się prezentował należałoby popracować nad urozmaiceniem
      widoku. Widok jest dosyć toporny i kwadratowy ale przejrzysty. W późniejszym
      czasie spróbuję popracować z Angularem.

    * Jeśli chodzi o nawigację po stronach aplikacji należałoby poukrywać przekazywane
      między stronami parametry, w sesji lub w redirectAttributes lecz na potrzeby
      tego projektu postanowiłem pozostawić je jawne w URL bo tak było mi wygodniej.

    * Podczas dodawania zdarzenia jazdy istnieje możliwość uzupełnienia adresów
      wszystkich puntków pośrednich. Trwa to dosyć długo ponieważ dla każdego punktu
      pośredniego wykonywane jest zapytanie do GraphHoppera o jego adres. Aby usprawnić
      rozwiązanie należałoby zaimplementować scheduler, które w określonych odstępach
      czasu pobierałby z bazy punkty niezawierające adresów i uzupełniał je danymi.
      Zapytania do GraphHopperApi wykonywałyby się w tle działania aplikacji.

    * ID dla każdego rekordu w bazie generuje się automatycznie. Wykorzystałem do tego
      problemu adnotację Hibernatową @GeneratedValue, jednakże powoduje ona, że dla
      wszystkich encji istnieje jeden wspólny licznik. Aby każda encja posiadała swój
      unikalny generator ID należałoby zaimplementować osobny generator dla każdej encji.
