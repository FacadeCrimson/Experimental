package com.simon.spring.authentication;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalcController {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Data
    static class Result {
        private final int left;
        private final int right;
        private final long answer;
    }

    @RequestMapping("/calc")
    Result calc(@RequestParam(value = "left", defaultValue = "0") int left,
            @RequestParam(value = "right", defaultValue = "0") int right) {
        MapSqlParameterSource source = new MapSqlParameterSource().addValue("left", left).addValue("right", right);
        return jdbcTemplate.queryForObject("SELECT :left + :right AS answer", source,
                (rs, rowNum) -> new Result(left, right, rs.getLong("answer")));
    }
}
