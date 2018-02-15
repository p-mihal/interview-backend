package de.bringmeister.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Unit {

    @JsonProperty("package")
    PACKAGE,
    @JsonProperty("piece")
    PIECE
}
