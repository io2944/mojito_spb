package cryorbiter.mojito_spb.service;

import java.util.List;
import java.util.Optional;

import cryorbiter.mojito_spb.mapper.UserMapper;
import cryorbiter.mojito_spb.model.User;
import cryorbiter.mojito_spb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cryorbiter.mojito_spb.dto.UserDto;
import cryorbiter.mojito_spb.form.UserForm;

@Service
@Transactional
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService,  UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUser(UserForm userForm) {
        if (userForm == null) {
            throw new IllegalArgumentException("user doit être renseigné");
        }

        // Vérifier si l'utilisateur existe déjà par username
        if (userForm.getUsername() != null && userRepository.findByUsername(userForm.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User avec le username " + userForm.getUsername() + " existe déjà.");
        }

        // Vérifier si l'utilisateur existe déjà par email
        if (userForm.getEmail() != null && userRepository.findByEmail(userForm.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User avec l'email " + userForm.getEmail() + " existe déjà.");
        }

        UserDto newUser = new UserDto();
        newUser.setUsername(userForm.getUsername());
        newUser.setEmail(userForm.getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userForm.getPassword());
        newUser.setPassword(hashedPassword);
        newUser.setRoles(roleService.getRolesByIds(userForm.getRoles()));

        User entity = userMapper.toEntity(newUser);
        User savedEntity = userRepository.save(entity);
        return userMapper.toDto(savedEntity);
    }

    public UserDto getUserById(Long id) {
        if (id == null) {
            return null;
        }

        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.map(userMapper::toDto).orElse(null);
    }

    public UserDto updateUser(UserForm userForm) {
        if (userForm == null) {
            throw new IllegalArgumentException("user doit être renseigné");
        }

        if (userForm.getId() == null) {
            throw new IllegalArgumentException("L'identifiant de l'utilisateur doit être renseigné pour la modification");
        }

        Optional<User> existingEntityOpt = userRepository.findById(userForm.getId());
        if (existingEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("Aucun utilisateur trouvé avec l'identifiant " + userForm.getId());
        }

        UserDto existingUser = this.getUserById(userForm.getId());
        existingUser.setUsername(userForm.getUsername());
        existingUser.setEmail(userForm.getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userForm.getPassword());
        existingUser.setPassword(hashedPassword);

        existingUser.setRoles(roleService.getRolesByIds(userForm.getRoles()));

        User existingEntity = existingEntityOpt.get();
        userMapper.updateEntity(existingEntity, existingUser);
        User savedEntity = userRepository.save(existingEntity);
        return userMapper.toDto(savedEntity);
    }

    public void deleteUser(UserDto user) {
        if (user == null) {
            throw new IllegalArgumentException("user doit être renseigné");
        }

        if (user.getId() == null) {
            throw new IllegalArgumentException("L'identifiant de l'utilisateur doit être renseigné pour la suppression");
        }

        userRepository.deleteById(user.getId());
    }

    public List<UserDto> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }
}
