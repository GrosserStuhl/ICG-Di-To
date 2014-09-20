package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import mathe.Color;
import mathe.Vector3f;
import general.Scene;

public class SceneLoader {

	public static Scene loadScene() {
		try {
			// BufferedReader reader = null;
			LineNumberReader reader = null;
			try {
				reader = new LineNumberReader(new FileReader(new File("")));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int rowCount = 1;
			ArrayList<ShapePlan> shapes = new ArrayList<>();

			String line = "";
			int lineNumber;
			while (!line.equals("</scene>")) {
				line = reader.readLine();
				if (line.contains("<shape")) {
					lineNumber = reader.getLineNumber();
					ShapePlan shape = null;
					ArrayList<ShapePlan> children = new ArrayList<>();
					String[] attributes = line.split("#");
					String[] planet = attributes[1].split("\"");
					float scale = 0;
					if (attributes.length > 2) {
						String[] scale1 = attributes[2].split("=");
						String[] scale2 = scale1[1].split(">");
						String scale3 = scale2[0];
						scale = Float.parseFloat(scale3);
					} 
					
					if (reader.readLine().equals("</shape>")) {
						shape = new ShapePlan(planet[1]);
					} else {
						rowCount++;
						reader.setLineNumber(reader.getLineNumber() - 1);
						line = reader.readLine();
						while(!line.equals("</shape>")) {
							ShapePlan child = null;
							ArrayList<ShapePlan> children2 = new ArrayList<>();
							String[] attributes2 = line.split("#");
							String[] planet2 = attributes[1].split("\"");
							if (attributes.length > 2) {
								String[] scale1 = attributes[2].split("=");
								String[] scale2 = scale1[1].split(">");
								String scale3 = scale2[0];
								float scaleTwo = Float.parseFloat(scale3);

								shape = new ShapePlan(planet[1], scale);
							} 
							if (reader.readLine().equals("</shape>")) {
								shape = new ShapePlan(planet[1]);
							} else {
								reader.setLineNumber(reader.getLineNumber() - 1);
								line = reader.readLine();
								while(!line.equals("</shape>")) {
									
								}
							}
						}
					}
					shapes.add(shape);
				}
			}

			reader.close();
			return new Scene();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}