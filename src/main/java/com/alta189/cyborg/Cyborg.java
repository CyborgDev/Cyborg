/*
 * Copyright (C) 2012 CyborgDev <cyborg@alta189.com>
 *
 * This file is part of cyborg
 *
 * cyborg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * cyborg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alta189.cyborg;

import com.alta189.cyborg.api.event.EventManager;
import com.alta189.cyborg.api.event.SimpleEventManager;
import com.alta189.cyborg.api.plugin.CommonPluginLoader;
import com.alta189.cyborg.api.plugin.CommonPluginManager;
import com.alta189.cyborg.api.plugin.Plugin;
import com.alta189.cyborg.api.plugin.PluginManager;

import java.io.File;

public class Cyborg {
	private static Cyborg instance;
	private final File pluginDir = new File("plugins");
	private final CommonPluginManager pluginManager;
	private final SimpleEventManager eventManager;
	

	public Cyborg() {
		if (instance != null)
			throw new IllegalAccessError("There is already an instance of Cyborg!");
		pluginManager = new CommonPluginManager(this);
		pluginManager.registerPluginLoader(CommonPluginLoader.class);
		eventManager = new SimpleEventManager();
		instance = this;
	}

	public static Cyborg getInstance() {
		return instance;
	}

	private final void loadPlugins() {
		if (!pluginDir.exists())
			pluginDir.mkdirs();
		pluginManager.loadPlugins(pluginDir);
	}

	private final void enablePlugins() {
		for (Plugin plugin : pluginManager.getPlugins()) {
			pluginManager.enablePlugin(plugin);
		}
	}
	
	public EventManager getEventManager() {
		return eventManager;
	}
	
	public PluginManager getPluginManager() {
		return pluginManager;
	}

	public File getUpdateFolder() {
		return new File(pluginDir, "updates");
	}

}
