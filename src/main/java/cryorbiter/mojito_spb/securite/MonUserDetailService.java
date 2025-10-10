package cryorbiter.mojito_spb.securite;

import cryorbiter.mojito_spb.model.User;
import cryorbiter.mojito_spb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MonUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MonUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        MonUserDetails monUser = new MonUserDetails(user);
        return monUser;
    }
}
