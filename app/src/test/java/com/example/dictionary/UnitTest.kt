package com.example.dictionary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Base UnitTest class for all Tests
 * Extended any test with this class
 */
@RunWith(MockitoJUnitRunner::class)
abstract class UnitTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Suppress("LeakingThis")
    @Rule
    @JvmField
    val injectMocks = InjectMocksRule.create(this@UnitTest)
}