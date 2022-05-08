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
    LOBBY;
}
