package com.first.team2052.stronghold.auto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import com.first.team2052.lib.trajectory.Path;
import com.first.team2052.lib.trajectory.TextFileDeserializer;
import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

public class AutoPaths {
	String[] pathsNames = new String[] { "LowBarHighGoalPath", "TestPath", "Position2ToCenterPath", "Position3ToCenterPath", "Position4ToCenterPath", "Position5ToCenterPath",
			"Position5bToCenterPath" };
	HashMap<String, Path> paths = Maps.newHashMap();

	public AutoPaths() {
		loadPaths();
	}

	private void loadPaths() {
		System.out.println("Loading Paths...");
		TextFileDeserializer deserializer = new TextFileDeserializer();

		for (String pathName : pathsNames) {
			String content = null;

			try {
				File file = new File(String.format("/home/lvuser/paths/%s.txt", pathName));
				content = Files.toString(file, Charsets.UTF_8);
			} catch (FileNotFoundException exception) {
				System.out.println("Error: Couldn't find " + pathName + " path not loaded");
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (content == null) {
				continue;
			}

			Path path = deserializer.deserialize(content);

			paths.put(pathName, path);
		}
	}

	public Optional<Path> getPath(String pathName) {
		return paths.containsKey(pathName) ? Optional.of(paths.get(pathName)) : Optional.absent();
	}
}
