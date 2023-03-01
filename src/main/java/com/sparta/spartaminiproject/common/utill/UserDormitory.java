package com.sparta.spartaminiproject.common.utill;

public enum UserDormitory {
    NONE(Authority.NONE, 0),
    Gryffindor(Authority.Gryffindor, 1),
    Hufflepuff(Authority.Hufflepuff, 2),
    Ravenclaw(Authority.Ravenclaw, 3),
    Slytherin(Authority.Slytherin, 4);

    private final String authority;
    private final int num;

    UserDormitory(String authority, int num) {
        this.authority = authority;
        this.num = num;
    }

    public String getAuthority() {
        return this.authority;
    }
    public int getNum() {
        return this.num;
    }

    public static class Authority {
        public static final String NONE = "ROLE_NONE";
        public static final String Gryffindor = "ROLE_Gryffindor";
        public static final String Hufflepuff = "ROLE_Hufflepuff";
        public static final String Ravenclaw = "ROLE_Ravenclaw";
        public static final String Slytherin = "ROLE_Slytherin";

    }
}
