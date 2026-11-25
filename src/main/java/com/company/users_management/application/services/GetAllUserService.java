package com.company.users_management.application.services;

import com.company.users_management.application.port.in.GetAllUserUseCase;
import com.company.users_management.application.port.out.GetAllUserPort;
import com.company.users_management.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllUserService implements GetAllUserUseCase {
    private final GetAllUserPort getAllUserPort;

    @Override
    public List<UserModel> getAllUsers() {
        return getAllUserPort.getAllUsers();
    }
}
