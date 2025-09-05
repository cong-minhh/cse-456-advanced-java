package vn.edu.eiu.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String displayName;

    @Override
    public String toString() {
        return displayName;
    }
}
