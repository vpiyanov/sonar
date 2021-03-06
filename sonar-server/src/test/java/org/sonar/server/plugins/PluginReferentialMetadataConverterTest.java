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
package org.sonar.server.plugins;

import org.junit.Test;
import org.sonar.api.platform.PluginMetadata;
import org.sonar.core.plugins.DefaultPluginMetadata;
import org.sonar.updatecenter.common.PluginReferential;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PluginReferentialMetadataConverterTest {

  @Test
  public void should_convert_metadata_to_plugin_referential() {
    PluginMetadata metadata = mock(DefaultPluginMetadata.class);
    when(metadata.getKey()).thenReturn("foo");

    PluginReferential pluginReferential = PluginReferentialMetadataConverter.getInstalledPluginReferential(newArrayList(metadata));
    assertThat(pluginReferential).isNotNull();
    assertThat(pluginReferential.getPlugins()).hasSize(1);
    assertThat(pluginReferential.getPlugins().get(0).getKey()).isEqualTo("foo");
  }

  @Test
  public void should_not_add_core_plugin() {
    PluginMetadata metadata = mock(DefaultPluginMetadata.class);
    when(metadata.getKey()).thenReturn("foo");
    when(metadata.isCore()).thenReturn(true);

    PluginReferential pluginReferential = PluginReferentialMetadataConverter.getInstalledPluginReferential(newArrayList(metadata));
    assertThat(pluginReferential).isNotNull();
    assertThat(pluginReferential.getPlugins()).hasSize(0);
  }
}
