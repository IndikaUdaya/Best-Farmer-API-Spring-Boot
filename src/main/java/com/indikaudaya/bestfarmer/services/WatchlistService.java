package com.indikaudaya.bestfarmer.services;

import com.indikaudaya.bestfarmer.entities.Watchlist;
import com.indikaudaya.bestfarmer.repositories.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {
    @Autowired
    private WatchlistRepository watchlistRepository;

    public List<Watchlist> getAllWatchlist() {
        return watchlistRepository.findAll();
    }

    public Watchlist getWatchlistById(Long id) {
        return watchlistRepository.findById(id).orElse(null);
    }

    public List<Watchlist> getWatchlistByUserId(Long id) {
        return watchlistRepository.findAllByUser_Id(id);
    }

    public Watchlist getWatchlistByUIdAndPid(Long uid,Long pid) {
        return watchlistRepository.findWatchlistByUidAndPid(uid,pid);
    }


    public Watchlist saveWatchlist(Watchlist watchlist) {
        return watchlistRepository.save(watchlist);
    }

    public void deleteWatchlistUser(Long id) {
        watchlistRepository.deleteById(id);
    }
}
