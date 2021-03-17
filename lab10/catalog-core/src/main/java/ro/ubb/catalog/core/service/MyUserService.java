package ro.ubb.catalog.core.service;

import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.User;

/** Created by radu. */
@Service
public interface MyUserService {

  User getUserByUserName(String userName);
}
