package com.natha.dev.Dao;

import com.natha.dev.Model.Channel;
import com.natha.dev.Model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelDao extends JpaRepository<Channel,Long> {
    List<Channel> findByTeam(Team team);
}
