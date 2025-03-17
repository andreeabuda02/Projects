package com.example.mongocrudapp.app;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.example.mongocrudapp.view.MainGUI;

import org.slf4j.LoggerFactory;

public class MongoCrudAppApplication {

	public static void main(String[] args) {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		Logger logger = loggerContext.getLogger("org.mongodb.driver");
		logger.setLevel(Level.WARN);
		MainGUI mainGUI = new MainGUI();
		mainGUI.setVisible(true);
	}

}
