package bookauthor.bookauthor.security;

import bookauthor.bookauthor.model.User;
import bookauthor.bookauthor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user  = userRepository.findUsersByEmail(s);
        if(user == null){
            throw new UsernameNotFoundException("User not found");

        }
        return new CurrentUser(user);
    }
}
