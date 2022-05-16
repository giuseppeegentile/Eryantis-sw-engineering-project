package it.polimi.ingsw.model.enums;

public enum PhaseGame {
    ADD_STUDENT_CLOUD,
    PLAY_CARDS_ASSISTANT, //nb: differenza con play_card_assistant è che questo è il turno di gioco in cui tutti i giocatori giocano la carta. In player è il singolo player che gioca
    START,
    CHECK_WIN,
    CHECK_ISLAND,
    CHECK_JOIN,
    DECIDE_ORDER_PHASE,
    ADD_STUDENT_TO_ISLAND,
    ADD_STUDENT_TO_HALL,
    MOVE_MOTHER,
    PLAYER_MOVE_FROM_CLOUD_TO_ENTRANCE,
    MOVE_FROM_BAG_TO_CLOUD,
    INIT, LOGIN
}
