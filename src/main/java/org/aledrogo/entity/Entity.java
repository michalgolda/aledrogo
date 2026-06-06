package org.aledrogo.entity;

import java.security.SecureRandom;

public abstract class Entity {
    private final SecureRandom secureRandom = new SecureRandom();
    private final Integer id = secureRandom.nextInt(1000000);

    public Integer getId() {
        return id;
    }
}
