package com.example.problems.DAO;

import com.example.problems.DTO.Topic;

import java.util.List;

public interface TopicDAO {

    List<Topic> getTopics();

    Topic getTopicById(int topicId);

    Topic getTopicByName(String topicName);

}
