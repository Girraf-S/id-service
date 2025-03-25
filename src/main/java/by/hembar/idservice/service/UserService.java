package by.hembar.idservice.service;

import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.entity.UserArchive;
import com.solbeg.nuserservice.exception.AppException;
import com.solbeg.nuserservice.mapper.UserMapper;
import com.solbeg.nuserservice.model.UserArchiveResponse;
import com.solbeg.nuserservice.repository.UserArchiveRepository;
import com.solbeg.nuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserArchiveRepository userArchiveRepository;
    private final UserMapper userMapper;

    @Transactional
    public Optional<UserArchive> findNonActiveById(Long id) {
        return userArchiveRepository.findById(id);
    }

    @Transactional
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void activateUserById(Long id) {
        userArchiveRepository.findById(id).orElseThrow(
                () -> new AppException("Not found user with id " + id, HttpStatus.NOT_FOUND)
        );
        userRepository.activateUserById(id);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<UserArchiveResponse> findAllByActive(Boolean isActive, Pageable pageable) {
        return isActive == null ? findAll(pageable)
                .map(userMapper::userToUserArchiveResponse) :
                isActive ? userRepository.findAll(pageable).map(userMapper::userToUserArchiveResponse)
                        : userArchiveRepository.findAll(pageable).map(userMapper::userArchiveToUserArchiveResponse);
    }
}
