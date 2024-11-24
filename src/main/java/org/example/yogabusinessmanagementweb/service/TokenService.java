package org.example.yogabusinessmanagementweb.service;

import org.example.yogabusinessmanagementweb.common.entities.Token;

public interface TokenService {

    Token save(Token token);
    String delete(Token token);
    Token getTokenByUsername(String username);
}
