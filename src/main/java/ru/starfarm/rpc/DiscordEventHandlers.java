package ru.starfarm.rpc;

import com.sun.jna.Structure;
import ru.starfarm.rpc.callbacks.*;

import java.util.Arrays;
import java.util.List;

public class DiscordEventHandlers extends Structure {
    public ReadyCallback ready;

    public DisconnectedCallback disconnected;

    public ErroredCallback errored;

    public JoinGameCallback joinGame;

    public SpectateGameCallback spectateGame;

    public JoinRequestCallback joinRequest;

    public List<String> getFieldOrder() {
        return Arrays.asList("ready", "disconnected", "errored", "joinGame", "spectateGame", "joinRequest");
    }

    public class Builder {
        DiscordEventHandlers h = new DiscordEventHandlers();

        public Builder setReadyEventHandler(ReadyCallback r) {
            this.h.ready = r;
            return this;
        }

        public Builder setDisconnectedEventHandler(DisconnectedCallback d) {
            this.h.disconnected = d;
            return this;
        }

        public Builder setErroredEventHandler(ErroredCallback e) {
            this.h.errored = e;
            return this;
        }

        public Builder setJoinGameEventHandler(JoinGameCallback j) {
            this.h.joinGame = j;
            return this;
        }

        public Builder setSpectateGameEventHandler(SpectateGameCallback s) {
            this.h.spectateGame = s;
            return this;
        }

        public Builder setJoinRequestEventHandler(JoinRequestCallback j) {
            this.h.joinRequest = j;
            return this;
        }

        public DiscordEventHandlers build() {
            return this.h;
        }
    }
}
