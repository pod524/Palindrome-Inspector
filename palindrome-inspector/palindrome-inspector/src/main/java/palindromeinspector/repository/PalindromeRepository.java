package palindromeinspector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import palindromeinspector.model.PalindromeRequest;

@Repository
public interface PalindromeRepository extends JpaRepository<PalindromeRequest, Long> {
}