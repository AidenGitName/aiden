package com.wonders.hms.autocomplete;

import com.wonders.hms.autocomplete.controller.AutocompleteController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AutocompleteController.class)
public class AutocompleteControllerTest {

    @Autowired
    MockMvc mock;

    @Test
    public void testHello() throws Exception {

        MvcResult result = mock.perform(get("/autocomplete"))
                .andExpect(status().isOk())
//                .andExpect(content().string("Hello World"))
                .andReturn();
    }
}
