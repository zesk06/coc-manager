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

import java.text.ParseException;

import org.junit.Test;

import junit.framework.Assert;

public class ConfigTest {

	private final String[] CONFIG_FILES = new String[] {
			"archer_tower"
			,"cannon"
			,"default"
			, "gold_mine"
			, "hidden_tesla"
			, "inferno_tower"
			, "mortar"
			, "town_hall"
			, "wizard_tower"
			, "x_bow"
			};

    public ConfigTest() {
    }

    @Test
    public void testCannonConfig() throws ParseException {
        //parse cannon config
		Config c = Config.parse("cannon");

		Assert.assertEquals(13, c.getLevelNumber());
		Assert.assertEquals("9", c.getLevel(1).get(Config.DPS));
		Assert.assertEquals(9, c.getDPS(1));
		Assert.assertEquals("11", c.getLevel(2).get(Config.DPS));
		Assert.assertEquals(11, c.getDPS(2));

		Assert.assertEquals(420, c.getHitPoints(1));
		Assert.assertEquals(470, c.getHitPoints(2));
		Assert.assertEquals(1260, c.getHitPoints(13));

    }

	@Test
	public void testAllConfigs() throws ParseException {
		for (String s : CONFIG_FILES) {
			Config.parse(s);
		}
	}

	@Test
	public void testGetMaxLevel() throws ParseException {
		Config c = Config.parse("cannon");
		Assert.assertEquals(2, c.getMaxLevel(1));
		Assert.assertEquals(3, c.getMaxLevel(2));
		Assert.assertEquals(4, c.getMaxLevel(3));
		Assert.assertEquals(5, c.getMaxLevel(4));
		Assert.assertEquals(6, c.getMaxLevel(5));
		Assert.assertEquals(7, c.getMaxLevel(6));
		Assert.assertEquals(8, c.getMaxLevel(7));
		Assert.assertEquals(10, c.getMaxLevel(8));
		Assert.assertEquals(11, c.getMaxLevel(9));
		Assert.assertEquals(11, c.getMaxLevel(9));
		Assert.assertEquals(13, c.getMaxLevel(10));
	}

	@Test
	public void testGetMaxNumberPerTHLevel() throws ParseException {
		Config c = Config.parse("gold_mine");
		Assert.assertEquals(1, c.getMaxNumberPerTHLevel(1));
		Assert.assertEquals(2, c.getMaxNumberPerTHLevel(2));
		Assert.assertEquals(3, c.getMaxNumberPerTHLevel(3));
		Assert.assertEquals(4, c.getMaxNumberPerTHLevel(4));
		Assert.assertEquals(5, c.getMaxNumberPerTHLevel(5));
		Assert.assertEquals(6, c.getMaxNumberPerTHLevel(6));
		Assert.assertEquals(6, c.getMaxNumberPerTHLevel(7));
		Assert.assertEquals(6, c.getMaxNumberPerTHLevel(8));
		Assert.assertEquals(6, c.getMaxNumberPerTHLevel(9));
		Assert.assertEquals(6, c.getMaxNumberPerTHLevel(9));
		Assert.assertEquals(7, c.getMaxNumberPerTHLevel(10));

		c = Config.parse("mortar");
		Assert.assertEquals(4, c.getMaxNumberPerTHLevel(8));
	}

}
