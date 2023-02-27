package com.sparta.spartaminiproject.domain.user.entity;

public enum UserDormitory {
    NONE(Authority.NONE),
    Gryffindor(Authority.Gryffindor),
    Hufflepuff(Authority.Hufflepuff),
    Ravenclaw(Authority.Ravenclaw),
    Slytherin(Authority.Slytherin);

    private final String authority;

    UserDormitory(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String NONE = "ROLE_NONE";
        public static final String Gryffindor = "ROLE_Gryffindor";
        public static final String Hufflepuff = "ROLE_Hufflepuff";
        public static final String Ravenclaw = "ROLE_Ravenclaw";

        public static final String Slytherin = "ROLE_Slytherin";

    }
}
