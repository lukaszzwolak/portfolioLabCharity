package pl.coderslab.charity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entities.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT COALESCE(SUM(d.quantity), 0) FROM Donation d")
    Long getTotalBagsCount();

    @Query("SELECT COALESCE(SUM(d.quantity), 0) FROM Donation d")
    Long getTotalGiftsCount();
}
