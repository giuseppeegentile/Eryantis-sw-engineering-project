package it.polimi.ingsw.network.message;

public enum MessageType {
    WIN,
    LOSE,
    PLAYERS_REQUEST,
    PLAYERS_RESPONSE,
    ERROR,
    DISCONNECTION,
    LOGIN_REQUEST,
    LOGIN_REPLY,
    GENERIC_MESSAGE,
    DISPLAY,
    TEXT,
    PING,
    MOVE,
    ORDER,
    ISLANDS,
    LOBBY, END_TURN, START_TURN, INIT, PLAYERNUMBER_REQUEST, PLAYERNUMBER_REPLY,
    CHOSEN_TOWER, GAMEMODE_REQUEST, GAMEMODE_RES, PLAYED_ASSISTANT_CARD, PLAYER_MOVED_STUDENTS_ON_ISLAND,
    PLAYER_MOVED_STUDENTS_ON_HALL,
    PLAYER_MOVED_MOTHER, MOVE_MOTHER_REQ, MOVE_CLOUD_TO_ENTRANCE, MOVED_CLOUD_TO_ENTRANCE, REQ_ENTRANCE_TO_HALL,
    REQ_ENTRANCE_TO_ISLAND,
    CHARACTER_CARD_PLAYED, SKIPPING_INFLUENCE, WHO_PLAY, REQ_PLAY_CHAR_CARD;
}
