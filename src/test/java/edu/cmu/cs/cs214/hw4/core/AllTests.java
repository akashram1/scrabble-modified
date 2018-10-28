package edu.cmu.cs.cs214.hw4.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ FirstPlayerOnlyTests.class, SetUp.class,
MultiplePlayerTests.class })
public class AllTests {

}
