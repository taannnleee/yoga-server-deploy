package org.example.yogabusinessmanagementweb.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.yogabusinessmanagementweb.exception.AppException;
import org.example.yogabusinessmanagementweb.exception.ErrorCode;
import org.example.yogabusinessmanagementweb.repositories.TokenRepository;
import org.example.yogabusinessmanagementweb.repositories.UserRepository;
import org.example.yogabusinessmanagementweb.service.TokenService;
import org.example.yogabusinessmanagementweb.common.entities.Token;
import org.example.yogabusinessmanagementweb.common.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class TokenServiceImpl implements TokenService {
    TokenRepository tokenRepository;
    UserRepository userRepository;

    @Override
    public Token save(Token token) {
        Optional<Token> optional =  tokenRepository.findByUsername(token.getUsername());
        if(optional.isEmpty()) {
            return tokenRepository.save(token);
        }else{
            Token currentToken = optional.get();
            currentToken.setAccessToken(token.getAccessToken());
            currentToken.setRefreshToken(token.getRefreshToken());
            return tokenRepository.save(currentToken);
        }

    }

    @Override
    public String delete(Token token) {

        User user = userRepository.findByToken(token).orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_FOUND));;
        user.setToken(null);
        userRepository.save(user);
        tokenRepository.delete(token);
        return  "Delete token success";
    }

    @Override
    public Token getTokenByUsername(String username) {
        return tokenRepository.findByUsername(username).orElse(null);
    }
}
