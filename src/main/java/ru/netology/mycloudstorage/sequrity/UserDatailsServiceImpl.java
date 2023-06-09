package ru.netology.mycloudstorage.sequrity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.netology.mycloudstorage.modele.User;
import ru.netology.mycloudstorage.repositopy.MyUserRepository;

@Service
@Slf4j
public class UserDatailsServiceImpl implements UserDetailsService {
    @Autowired
    private MyUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User myUser = userRepository.findByLogin(username);
        if (myUser == null) {
            log.info("Unknown user");
            throw new UsernameNotFoundException("Unknown user: " + username);
        }
        UserDetails user = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getLogin())
                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();
        return user;
    }

}

