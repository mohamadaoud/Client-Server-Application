# Client-Server-Application

En Server/Client applikation som kommunicerar using sockets. Som en uppgift så skall klienten skicka en fråga till servern 
som i sin tur ska svara på det och skicka svaret tillbaks till klienten.

### För att använda prgrammet följer dessa steg : 
- 1- Öppna en  command prompt
- 2- Skriv in "telnet localhost 35000" och tryck på enter
- 3- Om en tom skärm visas är porten öppen och testet lyckas.
-Nu ansluter du till servern. 
- För att det ska vara mer intressant så har vi en miniräknare för att använda så kan man utföra enkla uträkningar som "2+2"  som två giltiga nummer med en operation däremellan.
- Server tar meddelandet eller resultatet från miniräknaren och skickar tillbaka till klienten. Programmet fungerar på port 35000. 
- Om du vill avsluta programmet och avsluta anslutningen skriver du quit. Efter du har skrivit "quit", så kommer anslutningen till porten att avslutas.

### Ifall telnet är inte inställerat på datorn (Om vi tar Mac Os som ett till ex.), så bör man gör som i följande steg : 
- 1- Öppna terminal från launchpad:
- 2- Du kanske behöver installera Homebrew först innan du går vidare för att installera Telnet.
- 3- Kopiera och klistra in på terminalen den genom att köra: "/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
- 4- Install telnet genom att köra: "brew install telnet"

Då är det klart för att köra applikationen...
