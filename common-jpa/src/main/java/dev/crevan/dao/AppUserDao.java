package dev.crevan.dao;

import dev.crevan.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserDao extends JpaRepository<AppUser, Long> {

    AppUser findAppUserByTelegramId(final Long id);
}
