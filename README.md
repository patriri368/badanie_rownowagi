# Aplikacja okienkowa służąca do obsługi układu do badania równowagi oraz wizualizacji zebranych danych

Aplikacja została napisana w języku Java z wykorzystaniem biblioteki JavaFX.

Głównym zadaniem aplikacji okienkowej jest sterowanie pracą układu (Arduino Nano i dwa czujniki MPU6050) – łączy i rozłącza aplikację z portem szeregowym oraz sygnalizuje o trybie, w jakim się znajduje. Aplikacja jest zbudowana z trzech okien, zawierających różne funkcjonalności. Pierwsze okno służy do zarządzania pracą systemu. W lewym górnym rogu widoku znajduje się obiekt ComboBox, po rozwinięciu którego ukazana zostaje lista dostępnych portów szeregowych. Należy wybrać numer portu, do którego podłączone jest urządzenie pomiarowe. Po prawej stronie od opisanego elementu znajduje się przycisk, który umożliwia połączenie lub rozłączenie aplikacji 
z portem szeregowym. Gdy aplikacja nie komunikuje się z portem na przycisku widnieje napis Połącz. W przeciwnym wypadku przycisk oznaczony jest etykietą Rozłącz. Pierwotnie przy uruchomieniu aplikacji dioda, znajdująca się w górnej części okna, nie jest załączona. Poprawne połączenie z portem szeregowym sygnalizowane jest zmianą koloru diody na zielony. Jeśli zakończono komunikację z portem, wirtualna dioda świeci kolorem czerwonym. Po uruchomieniu układu istnieje także możliwość podglądu aktualnych danych przesyłanych za pomocą portu szeregowego. Jest to realizowane poprzez kliknięcie przycisku Podgląd danych. Ukazuje się wtedy okno wyświetlające dane wypisywane w czasie rzeczywistym. Użytkownik w każdej chwili działania układu może rozpocząć zbieranie danych do pliku .txt. Żeby to zrobić, należy kliknąć na przycisk START. Zostanie wówczas utworzony nowy plik tekstowy 
w katalogu głównym projektu o nazwie data.txt, do którego dane są na bieżąco zapisywane. Po zakończeniu gromadzenia danych należy nacisnąć przycisk STOP.

Za pomocą przycisku Wykresy danych, który znajduje się na głównym widoku aplikacji, można przejść do okna przedstawiającego wykresy danych zgromadzonych podczas badania. Wykresy są rysowane w trybie offline, poprzez odczyt danych z pliku data.txt. Widoczne są trzy wykresy – dla osi X, Y oraz Z. Każdy z wykresów zawiera po dwie serie danych – odpowiadające każdemu 
z czujników. Na osi X znajduje się numer próbki, natomiast na osi Y – kąt Eulera wyrażony w stopniach. Po prawej stronie wykresów znajduje się legenda, która informuje o kolorze serii danych odpowiadającej czujnikowi 0 oraz czujnikowi 1. 
U dołu okna znajduje się przycisk Powrót, który pozwala na cofnięcie się do poprzedniego okna aplikacji.

Za pomocą przycisku Wizualizacja danych, znajdującym się na głównym widoku aplikacji, możliwe jest przejście do okna przedstawiającego wizualizację danych. Jest ona wykonana  w trybie offline. Zadanie zostało zrealizowane poprzez rzut z góry na osie X i Y. W ten sposób widoczne są punkty, które są przemieszczane zgodnie z kolejnymi położeniami odczytanymi z pliku data.txt. Reprezentuje to wzajemne wychylenie obu czujników. Poniżej wizualizacji wyświetlany jest pasek postępu, który informuje o etapie przebiegu wizualizacji. 
Z prawej strony okna znajduje się legenda, która informuje o kolorze punktu odpowiadającego danemu czujnikowi. Z lewej strony widoku znajduje się przycisk Powrót, po kliknięciu którego użytkownik zostanie przeniesiony do poprzedniego okna aplikacji. 
