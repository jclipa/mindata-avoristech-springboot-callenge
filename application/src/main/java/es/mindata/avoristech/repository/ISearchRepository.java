package es.mindata.avoristech.repository;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mindata.avoristech.domain.SearchEntity;

@Repository
public interface ISearchRepository extends JpaRepository<SearchEntity, String> {

	public List<SearchEntity> findByHotelIdIsAndCheckInIsAndCheckOutIsAndAgesIn(String hotelId, Date checkIn,
			Date checkOut, Collection<Integer> ages);

}
