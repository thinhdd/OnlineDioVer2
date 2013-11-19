package com.qsoft.ondio.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qsoft.ondio.model.User;

import java.io.File;
import java.io.IOException;

 
public class JacksonExample {
    public static void main(String[] args) {
 
	ObjectMapper mapper = new ObjectMapper();
 
	try {
 
		// read from file, convert it to user class
        User user = mapper.readValue(new File("c:\\user.json"), User.class);
 
		// display to console
		System.out.println(user);
 
	} catch (JsonGenerationException e) {
 
		e.printStackTrace();
 
	} catch (JsonMappingException e) {
 
		e.printStackTrace();
 
	} catch (IOException e) {
 
		e.printStackTrace();
 
	}
 
  }
 
}