package ru.stasyan.interview_cl.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.stasyan.interview_cl.config.Initializer;
import ru.stasyan.interview_cl.config.WebAppConfig;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppConfig.class,Initializer.class})
@WebAppConfiguration
public class IndexControllerTest {

    @Test
    public void mapFromXml() {
    }
}