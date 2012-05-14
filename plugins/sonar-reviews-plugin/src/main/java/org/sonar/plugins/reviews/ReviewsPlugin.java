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
package org.sonar.plugins.reviews;

import com.google.common.collect.Lists;
import org.sonar.api.Extension;
import org.sonar.api.SonarPlugin;
import org.sonar.plugins.reviews.jira.JiraLinkReviewAction;
import org.sonar.plugins.reviews.jira.JiraLinkReviewCommand;
import org.sonar.plugins.reviews.jira.soap.JiraSOAPClient;

import java.util.List;

public final class ReviewsPlugin extends SonarPlugin {

  @SuppressWarnings({"rawtypes", "unchecked"})
  public List<Class<? extends Extension>> getExtensions() {
    List extensions = Lists.newLinkedList();

    extensions.add(JiraLinkReviewCommand.class);
    extensions.add(JiraLinkReviewAction.class);
    extensions.add(JiraSOAPClient.class);

    return extensions;
  }
}