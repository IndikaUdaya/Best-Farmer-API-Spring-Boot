package com.indikaudaya.bestfarmer.repositories;

import com.indikaudaya.bestfarmer.entities.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    @Query("SELECT w FROM Watchlist w WHERE w.user.id = :uid AND w.products.id = :pid")
    Watchlist findWatchlistByUidAndPid(@Param("uid") Long uid, @Param("pid") Long pid);

    List<Watchlist> findAllByUser_Id(Long id);
}

