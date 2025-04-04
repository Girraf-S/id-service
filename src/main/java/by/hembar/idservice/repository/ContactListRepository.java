package by.hembar.idservice.repository;

import by.hembar.idservice.entity.ContactList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactListRepository extends JpaRepository<ContactList, Long> {
}
