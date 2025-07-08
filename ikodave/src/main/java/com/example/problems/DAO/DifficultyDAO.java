package com.example.problems.DAO;

import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Topic;

import java.util.List;

public interface DifficultyDAO {

    List<Difficulty> getDifficulties();

    Difficulty getDifficultyById(int difficultyId);

    Difficulty getDifficultyByName(String difficultyName);

}
