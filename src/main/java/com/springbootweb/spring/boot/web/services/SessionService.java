package com.springbootweb.spring.boot.web.services;

import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import com.springbootweb.spring.boot.web.entities.SessionEntity;
import com.springbootweb.spring.boot.web.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private  final  int SESSION_LIMIT = 2;

    public void generateNewSession(EmployeeEntity user , String refreshToken) {
        List<SessionEntity> userSession = sessionRepository.findByUser(user);
        if(userSession.size() == SESSION_LIMIT) {
            userSession.sort(Comparator.comparing(SessionEntity::getLastUsedAt));
            SessionEntity recentlyUsed = userSession.getFirst();
            sessionRepository.delete(recentlyUsed);
        }

        SessionEntity newSession = SessionEntity.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
        sessionRepository.save(newSession);
    }

    public void validateSession(String refreshToken) {
        SessionEntity session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("refresh token not found"));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);

    }
    public void logout(String refreshToken) {
        SessionEntity session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("refresh token not found"));
        sessionRepository.delete(session);
    }
}
