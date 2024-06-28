package com.trusticket.trusticketcore.example;



import com.trusticket.trusticketcore.domain.Example;

import java.util.List;

public interface ExampleCustomRepository {

    List<Example> findExamplesWithKeywordContains(String keyword);
}
