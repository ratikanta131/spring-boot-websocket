package in.co.sdrc.newsapp.service;

import org.sdrc.usermgmt.domain.Account;
import org.sdrc.usermgmt.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import in.co.sdrc.newsapp.domain.UserDetails;
import in.co.sdrc.newsapp.model.UserModel;
import in.co.sdrc.newsapp.repository.UserDetailsRepository;
import in.co.sdrc.newsapp.util.TokenInfoExtracter;

@Service
public class CommonService{

    @Autowired
    private TokenInfoExtracter tokenInfoExtracter;
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private AccountRepository accountRepository;

    public String getUsername(){

        OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext()
				.getAuthentication();
        UserModel userModel = tokenInfoExtracter.getUserModelInfo(authentication);
        return userModel.getUserName();
        
    }

    public String getNameFromUsername(String username) {
        Account account = accountRepository.findByUserName(username);
        UserDetails userDetails = userDetailsRepository.findByAccount(account);
        return userDetails.getName();
    }
}