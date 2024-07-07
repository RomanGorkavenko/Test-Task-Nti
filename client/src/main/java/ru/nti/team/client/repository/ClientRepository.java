package ru.nti.team.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nti.team.client.model.entity.Cursor;

public interface ClientRepository extends JpaRepository<Cursor, Long> {
}
