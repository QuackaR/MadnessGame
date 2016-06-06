package de.krien.games.madness.game;

public enum GameState {

    State_Menu(0),
    State_SinglePlayer(1),
    State_Multiplayer(2),
    State_Editor(3);

    private int StateID;

    GameState(int i) {
        StateID = i;
    }

    public int GetID() {
        return StateID;
    }

}
