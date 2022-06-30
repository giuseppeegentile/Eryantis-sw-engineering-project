# Peer-Review 2: Protocollo di Comunicazione

Daniele Gagni, Simone Garbazio, Hitasha Ghai

Gruppo 64

Valutazione della documentazione del protocollo di comunicazione del gruppo 62.


## Lati positivi

Ogni messaggio contenente l'azione da parte del Player riceve una risposta:

- _invalid..._ nel caso in cui la scelta non sia consentita
- _display..._ in caso di scelta lecita si aggiorna la View

Il player viene notificato sempre dell'azione che deve compiere e delle informazioni necessarie a tale scopo mediante
un messaggio _display_ opportuno (per esempio _displayIslandMessage_ quando deve muovere madre natura).

Il server riceve come parametro dei messaggi riferiti all'azione dei players il relativo nickname in modo da 
identificare facilmente il giocatore.

### Login Phase

Il Server assegna il mazzo (con il corrispondente mago) a ogni giocatore: in questo modo l'utente ha una 
scelta in meno da fare, il server non deve effettuare controlli sulla disponibilità del mago e la fase di login è più fluida.

### Planning Phase

Ogni player può vedere il rispettivo "cimitero", con le carte che ha già utilizzato, grazie al messaggio _displayCloudsMessage_.

### Phase 1

All'inizio di questa mossa viene comunicato al Client la lista delle nuvole che si possono selezionare, in modo che il
player possa scegliere l'indice della nuvola corretto.

### Phase 2

Lo spostamento degli studenti è rappresentato da due messaggi differenti che esplicitano dove si intende spostare
gli studenti:

- _studentToHallMessage_ per lo spostamento nella sala da pranzo
- _studentToIslandMessage_ per lo spostamento su un'isola


### Phase 3

Il Client riceve aggiornamenti su tutti i cambiamenti che avvengono nelle isole:

- movimento di madre natura
- calcolo influenza e conseguente cambio torri
- merge isole

Le ultime due informazioni sono comunicate soltanto quando vi è un'effettiva modifica nel model e quindi si evitano
comunicazioni non rilevanti nel caso in cui non ci sia stato un cambio di torri oppure nessun isola sia stata unificata.

### Game's end

In caso di vittoria, viene passato il nickname del giocatore vincitore.

## Lati negativi
I messaggi _"display"_ sono mandati solo al Client associato al player che fa la mossa. Sarebbe più opportuno mandare
questi messaggi di aggiornamento del model a tutti i Client collegati alla partita in modo che possano "vedere", come
accade anche nel gioco reale, l'andamento del gioco e le mosse dei propri avversari.

Inoltre, è prevista la scelta della modalità di gioco ma non ci sono messaggi per poter utilizzare le carte personaggio.

### Login Phase

- Nel sequence diagram non viene modellata la parte dove il primo player che si connette sceglie il numero di giocatori e la modalità partita

- Dato che il diagramma rappresenta l'interazione tra un client e il server, la parte dove il server controlla il numero di client connessi prima di avviare la partita non è rappresentata

- Messaggi come *InvalidTowerMessage* e *InvalidNicknameMessage* possono essere unificati, dal momento che hanno lo scopo di comunicare al player la non validità della scelta effettuata, non è necessario essere specifici (potrebbe bastare un *InvalidChoiceMessage*)

### Planning Phase

Sarebbe opportuno aggiungere un messaggio per inviare al client le carte personaggio ancora disponibili (dopo il messaggio usato
per aggiornare il contenuto delle nuvole). Inoltre, ogni Player dovrebbe giocare una carta che non è già stata giocata da altri.
Manca dunque un controllo sulla scelta effettuata dal Player e un messaggio che possa eventualmente notificare al client 
di ripetere la scelta.

Potrebbe essere invece interessante per i giocatori sapere se un round sia l'ultimo. Questa informazione può essere inviata
dopo l'update delle nuvole (se il sacchetto si è svuotato) o dopo l'update del "cimitero" (se i giocatori non hanno più carte).

### Phase 1

Il primo step nell'Action Phase consiste nello spostamento degli studenti da Entrance alla Hall o sulle Islands.
Infatti, non è possibile scegliere una nuvola già all'inizio del proprio turno ed inserire ulteriori studenti
nell'Entrance se questa risulta già piena. Pertanto, il sequence diagram riferito alla scelta delle nuvole, denominato
come "Phase 1", dovrebbe essere riportato dopo al diagramma che illustra lo spostamento di madre natura e le modifiche
alle isole.

Selezionando direttamente l'id della nuvola da cui prendere studenti, risulta poco probabile che vengano selezionati
meno di 3 studenti perché la scelta degli studenti effettivamente non avviene. Di conseguenza, il controllo riportato
nel diagramma risulta non necessario.

### Phase 2

Nel messaggio _studentToIslandMessage_ non è presente alcun parametro che consenta di individuare l'isola su cui si
intende muovere lo studente.

### Phase 3

Il Client non riceve un aggiornamento sul numero di torri che gli sono ritornate/tolte dalla Board in seguito al
calcolo dell'influenza.

## Confronto

A differenza del nostro protocollo:
 - non tutti i messaggi "simili" sono raggruppati tra loro;
 - i controlli vengono fatti tutti lato server (non è previsto un controller lato client).

Consigliamo di aggiungere:
 - la gestione dell'ingresso di un player in una partita già esistente;
 - messaggi di acknowledgement dal client verso il server (utili per rendere conservativa la comunicazione);
 - un messaggio per notificare agli altri giocatori che un client si è disconnesso;
 - rendere parametrici i messaggi di aggiornamento della view e i controlli in base al numero di player nella partita.