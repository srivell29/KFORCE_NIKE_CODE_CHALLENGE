package com.example.dictionary

import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations

/**
 * Wrapper class TestRules with Mockito
 * Mock any tests before test rules with [create]
 */
class InjectMocksRule {

    companion object {
        fun create(testClass: Any) = TestRule { statement, _ ->
            MockitoAnnotations.initMocks(testClass)
            statement
        }
    }
}