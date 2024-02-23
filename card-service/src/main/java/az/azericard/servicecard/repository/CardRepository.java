package az.azericard.servicecard.repository;

import az.azericard.servicecard.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findCardByCardNumber(String cardNumber);
}
