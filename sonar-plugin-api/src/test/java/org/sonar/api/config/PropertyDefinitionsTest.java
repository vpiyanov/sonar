/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2008-2012 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.api.config;

import org.junit.Test;
import org.sonar.api.Properties;
import org.sonar.api.Property;

import static org.fest.assertions.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class PropertyDefinitionsTest {

  @Test
  public void shouldInspectPluginObjects() {
    PropertyDefinitions def = new PropertyDefinitions(new PluginWithProperty(), new PluginWithProperties());

    assertProperties(def);
  }

  @Test
  public void shouldInspectPluginClasses() {
    PropertyDefinitions def = new PropertyDefinitions(PluginWithProperty.class, PluginWithProperties.class);

    assertProperties(def);
  }

  private void assertProperties(PropertyDefinitions definitions) {
    assertThat(definitions.get("foo").getName(), is("Foo"));
    assertThat(definitions.get("one").getName(), is("One"));
    assertThat(definitions.get("two").getName(), is("Two"));
    assertThat(definitions.get("unknown"), nullValue());

    assertThat(definitions.getDefaultValue("foo"), nullValue());
    assertThat(definitions.getDefaultValue("two"), is("2"));

    assertThat(definitions.getAll().size(), is(3));
  }

  @Property(key = "foo", name = "Foo")
  static final class PluginWithProperty {
  }

  @Properties({
    @Property(key = "one", name = "One"),
    @Property(key = "two", name = "Two", defaultValue = "2")
  })
  static final class PluginWithProperties {
  }

  @Test
  public void testCategories() {
    PropertyDefinitions def = new PropertyDefinitions(Categories.class);
    assertThat(def.getCategory("inCateg"), is("categ"));
    assertThat(def.getCategory("noCateg"), is(""));
  }

  @Test
  public void testDefaultCategory() {
    PropertyDefinitions def = new PropertyDefinitions();
    def.addComponent(Categories.class, "default");
    assertThat(def.getCategory("inCateg"), is("categ"));
    assertThat(def.getCategory("noCateg"), is("default"));
  }

  @Properties({
    @Property(key = "inCateg", name = "In Categ", category = "categ"),
    @Property(key = "noCateg", name = "No categ")
  })
  static final class Categories {
  }

  @Test
  public void should_group_by_category() {
    PropertyDefinitions def = new PropertyDefinitions(ByCategory.class);

    assertThat(def.getGlobalPropertiesByCategory().keySet()).containsOnly("catGlobal1", "catGlobal2");
    assertThat(def.getProjectPropertiesByCategory().keySet()).containsOnly("catProject");
    assertThat(def.getModulePropertiesByCategory().keySet()).containsOnly("catModule");
  }

  @Properties({
    @Property(key = "global1", name = "Global1", category = "catGlobal1", global = true, project = false, module = false),
    @Property(key = "global2", name = "Global2", category = "catGlobal1", global = true, project = false, module = false),
    @Property(key = "global3", name = "Global3", category = "catGlobal2", global = true, project = false, module = false),
    @Property(key = "project", name = "Project", category = "catProject", global = false, project = true, module = false),
    @Property(key = "module", name = "Module", category = "catModule", global = false, project = false, module = true)
  })
  static final class ByCategory {
  }
}
