# VisualCrypto

Task is written in Polish.  

Zadanie domowe – kryptografia wizualna  

Napisać program Mask “maskujący” obrazy – przykład kryptografii wizualnej. Program, operując na poje-  
dynczych pikselach, powinien przekształcać plik graficzny (w jednym z typowych formatów: png, gif, etc.)  
na dwa pliki (w tym samym formacie) zgodnie z zasadami podanymi tutaj:  
http://users.telenet.be/d.rijmenants/en/visualcrypto.htm.  
Można założyć, że wejściowy obraz jest czarno-biały. Ścieżka do pliku wejściowego podawana jest jako ar-  
gument programu. Obrazy wynikowe powinny być tworzone w tym samym katalogu co plik wejściowy, a ich  
nazwy mają być konkatenacją nazwy właściwej pliku wejściowego oraz liczb: 1 i 2 (przykładowo: dla pliku  
img.png powinny być utworzone pliki img1.png oraz img2.png).  
Do wczytywania i zapisywania obrazów można użyć klasy (z rozszerzenia biblioteki klas) ImageIO.  
Obrazy w pamięci programu mogą być przechowywane w obiektach klasy BufferedImage.  

Dodatkowo napisać program Unmask “składający” dwa pliki stworzone w opisany wcześniej sposób w jeden  
plik wynikowy. Nazwy obrazów wejściowych powinny być podane jako argumenty programu. Wynikowy obraz  
nie musi być tożsamy z obrazem przed wykonaniem procedury “maskującej”.

