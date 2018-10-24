/*
package com.carrosserieafpa.carrosserie.security;

import com.carrosserieafpa.carrosserie.dao.UserRepository;
import com.carrosserieafpa.carrosserie.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

*/
/* class utilisée pour retourner l’utilisateur qui demande une authentification.
Le fait d’implémenter UserDetailsService est obligatoire afin que Spring Security puisse traiter l’authentification correctement.*//*

@Service("userService")
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("Aucun utilisateur à ce nom : " + username);
    } else {
      return user;
    }
  }
}
*/
