package com.alan.solution.test.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.alan.solution.test.entity.ScoreInput;
import com.alan.solution.test.entity.Item;
import com.alan.solution.test.entity.ScoreOutput;

@RestController
@RequestMapping("/test")
public class TestRestController {

    // create a list to store the output result
    private List<ScoreOutput> scoreOutputs = new ArrayList<>();

    @PostMapping("/assessment")
    public List<ScoreOutput> getScoreOutputs(@RequestBody List<ScoreInput> scoreInputs) throws IOException {

        // use jackson data binder to combine item json with Item class
        ObjectMapper mapper = new ObjectMapper();
        List<Item> items = mapper.readValue(new File("data\\item.json"), new TypeReference<List<Item>>() {
        });
        setOutput(scoreInputs, items);

        return scoreOutputs;
    }


    private double setOutput(List<ScoreInput> scoreInputs, List<Item> items) {

        // sum stand for sum score, n stand for the amount of subject
        // score stand for mean score
        double sum = 0;
        int n = 0;
        double score;

        // for each item
        for (Item item : items) {
            ScoreOutput scoreOutput = new ScoreOutput();

            // check whether items is exists or not
            if (item.getItems() == null) {
                for (ScoreInput scoreInput : scoreInputs) {

                    // match item id and score input id
                    if (item.getId().equals(scoreInput.getId())) {

                        // set up score output details
                        scoreOutput.setId(item.getId());
                        scoreOutput.setTitle(item.getTitle());
                        scoreOutput.setScore(scoreInput.getScore());
                        scoreOutputs.add(scoreOutput);

                        // calculate sum score
                        sum += scoreInput.getScore();
                        n++;
                    }
                }
            } else {

                // set up score output details
                scoreOutput.setId(item.getId());
                scoreOutput.setTitle(item.getTitle());
                scoreOutputs.add(scoreOutput);

                // recursive back to calculate mean score
                score = setOutput(scoreInputs, item.getItems());
                scoreOutput.setScore(score);

                // calculate sum score
                sum += score;
                n++;

            }
        }
        // calculate mean score
        score = sum / n;
        return score;
    }
}
