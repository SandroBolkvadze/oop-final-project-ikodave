package com.example.submissions.DAO;

import com.example.submissions.DTO.CodeLanguage;

import java.util.List;

public interface CodeLanguageDAO {

    int getCodeLanguageIdByName(String codeLanguage);

    List<CodeLanguage> getCodeLanguages();

}
