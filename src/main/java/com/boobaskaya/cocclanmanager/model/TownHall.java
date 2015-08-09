/*
 * Copyright (C) 2015 booba.skaya@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.boobaskaya.cocclanmanager.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TownHall extends Building{

    public TownHall(){
        super();
    }
    
    public TownHall(int level){
        super(level);
    }
    
    @Override
    public int getMaxLevel(TownHall hdv) {
        return 10;
    }
    
    @Override
    public BuildingType getType() {
        return BuildingType.TOWN_HALL;
    }

	@Override
	public Config getConfig() {
		return Config.defaultConfig();
	}
}