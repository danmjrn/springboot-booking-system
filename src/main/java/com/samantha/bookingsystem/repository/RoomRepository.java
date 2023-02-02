package com.samantha.bookingsystem.repository;

import com.samantha.bookingsystem.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByAlias(String alias);

    Optional<Room> findByUuid(UUID roomUuid);
}
