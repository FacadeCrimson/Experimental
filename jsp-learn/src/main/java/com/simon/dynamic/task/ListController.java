package com.simon.dynamic.task;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ListController {
        @Autowired
        JdbcTemplate jdbcTemplate;

        @RequestMapping("/artistlist")
        public String artistList(Model model) {
                ArrayList<String> artists = new ArrayList<String>();
                jdbcTemplate.query("SELECT * FROM artists;",
                                (rs, rowNum) -> new Artist(rs.getInt("id"), rs.getString("name"), rs.getString("city"),
                                                rs.getString("state"), rs.getString("phone"), rs.getString("website"),
                                                rs.getString("genres")))
                                .forEach(artist -> artists.add(artist.toString()));
                model.addAttribute("list", artists);
                model.addAttribute("title", "Artist");
                return "List";
        }

        @RequestMapping("/venuelist")
        public String venueList(Model model) {
                ArrayList<String> venues = new ArrayList<String>();
                jdbcTemplate.query("SELECT * FROM venues;",
                                (rs, rowNum) -> new Venue(rs.getInt("id"), rs.getString("name"),
                                                rs.getString("address"), rs.getString("city"), rs.getString("state"),
                                                rs.getString("phone"), rs.getString("website"), rs.getString("genres")))
                                .forEach(venue -> venues.add(venue.toString()));
                model.addAttribute("list", venues);
                model.addAttribute("title", "Venue");
                return "List";
        }

        @RequestMapping("/showlist")
        public String showList(Model model) {
                ArrayList<String> shows = new ArrayList<String>();
                jdbcTemplate.query("SELECT shows.id,artists.name,venues.name,shows.time FROM shows "
                                + "join artists on shows.artist=artists.id " + "join venues on shows.venue=venues.id;",
                                (rs, rowNum) -> new Show(rs.getInt("id"), rs.getString("artists.name"),
                                                rs.getString("venues.name"), rs.getString("time")))
                                .forEach(show -> shows.add(show.toString()));
                model.addAttribute("list", shows);
                model.addAttribute("title", "Show");
                return "List";
        }

}
