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
package org.sonar.wsclient.services;

/**
 * @since 2.11
 */
public final class ProjectDeleteQuery extends DeleteQuery {

  private String key;
  private static final String BASE_URL = "/api/projects/";

  private ProjectDeleteQuery(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  @Override
  public String getUrl() {
    StringBuilder url = new StringBuilder();
    url.append(BASE_URL);
    url.append(encode(key));
    return url.toString();
  }

  public static ProjectDeleteQuery create(String projectKeyOrId) {
    return new ProjectDeleteQuery(projectKeyOrId);
  }
}
