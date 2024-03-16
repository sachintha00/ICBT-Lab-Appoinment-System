package com.icbt.abc.services;

import com.icbt.abc.dtos.UserBankDetailsDto;
import com.icbt.abc.entities.UserBankDetails;
import com.icbt.abc.repositories.UserBankDetailsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBankDetailsService {
    @Autowired
    private UserBankDetailsRepository userBankDetailsRepository;

    @Transactional()
    public UserBankDetailsDto getUserBankDetails(Integer userId) {
        UserBankDetails bankDetails = userBankDetailsRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Bank details not found for user with ID: " + userId));

        UserBankDetailsDto bankDetailsDTO = new UserBankDetailsDto();
        BeanUtils.copyProperties(bankDetails, bankDetailsDTO);
        return bankDetailsDTO;
    }

    @Transactional
    public void saveUserBankDetails(UserBankDetailsDto bankDetailsDTO) {
        UserBankDetails bankDetails = new UserBankDetails();
        BeanUtils.copyProperties(bankDetailsDTO, bankDetails);
        userBankDetailsRepository.save(bankDetails);
    }
}
