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
package org.sonar.batch.scan.filesystem;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import org.sonar.api.resources.Project;

import java.io.File;
import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeprecatedFileSystemAdapterTest {
  @Rule
  public TemporaryFolder temp = new TemporaryFolder();

  @Test
  public void should_wrap_module_file_system() {
    DefaultModuleFileSystem target = mock(DefaultModuleFileSystem.class, Mockito.RETURNS_SMART_NULLS);
    DeprecatedFileSystemAdapter adapter = new DeprecatedFileSystemAdapter(target, new Project("my-project"));

    assertThat(adapter.getBasedir()).isNotNull();
    verify(target).baseDir();

    assertThat(adapter.getSourceDirs()).isNotNull();
    verify(target).sourceDirs();

    assertThat(adapter.getTestDirs()).isNotNull();
    verify(target).testDirs();

    assertThat(adapter.getSourceCharset()).isNotNull();
    verify(target).sourceCharset();

    assertThat(adapter.getBuildDir()).isNotNull();
    verify(target).buildDir();
  }

  @Test
  public void should_create_default_build_dir() throws IOException {
    File workingDir = temp.newFile("work");
    DefaultModuleFileSystem target = mock(DefaultModuleFileSystem.class);
    when(target.workingDir()).thenReturn(workingDir);
    DeprecatedFileSystemAdapter adapter = new DeprecatedFileSystemAdapter(target, new Project("my-project"));

    File buildDir = adapter.getBuildDir();
    assertThat(buildDir.getParentFile().getCanonicalPath()).isEqualTo(workingDir.getCanonicalPath());
  }
}
