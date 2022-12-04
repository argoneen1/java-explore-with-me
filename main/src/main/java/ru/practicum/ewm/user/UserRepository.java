package ru.practicum.ewm.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select new ru.practicum.ewm.user.dto.UserDto(u.id, u.name, u.email) from User u")
    Page<UserDto> findAllDto(Pageable pageable);

    @Query("select new ru.practicum.ewm.user.dto.UserDto(u.id, u.name, u.email) from User u where u.id in :ids")
    List<UserDto> findAllByIdDto(List<Long> ids);
}
