/*******************************************************************************
 * Copyright (c) 2008-2010 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Sonatype, Inc. - initial API and implementation
 *******************************************************************************/

package org.eclipse.m2e.core.project;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.apache.maven.execution.MavenExecutionRequest;


/**
 * Provides access to registry of Maven workspace projects
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IMavenProjectRegistry {

  /**
   * Returns IMavenProjectFacade corresponding to the pom. This method first looks in the project cache, then attempts
   * to load the pom if the pom is not found in the cache. In the latter case, workspace resolution is assumed to be
   * enabled for the pom but the pom will not be added to the cache.
   */
  public IMavenProjectFacade create(IFile pom, boolean load, IProgressMonitor monitor);

  public IMavenProjectFacade create(IProject project, IProgressMonitor monitor);

  /**
   * Performs requested Maven project update asynchronously, using background job. This method returns immediately.
   */
  public void refresh(MavenUpdateRequest request);

  /**
   * Performs requested Maven project update synchronously. In other words, this method does not return until all
   * affected projects have been updated and corresponding MavenProjectChangeEvent's broadcast. This method acquires a
   * lock on the workspace's root.
   */
  public void refresh(MavenUpdateRequest request, IProgressMonitor monitor) throws CoreException;

  /**
   * Returns IMavenProjectFacade for all opened Maven workspace projects.
   */
  public IMavenProjectFacade[] getProjects();

  /**
   * @return IMavenProjectFacade cached IMavenProjectFacade corresponding to the project or null if there is no cache
   *         entry for the project.
   */
  public IMavenProjectFacade getProject(IProject project);

  /**
   * Returns IMavenProjectFacade of the Maven workspace project that has given (groupId,artifactId,version) coordinates.
   * 
   * @TODO decide what to do if multiple workspace projects have the same g/a/v.
   */
  public IMavenProjectFacade getMavenProject(String groupId, String artifactId, String version);

  /**
   * PROVISIONAL
   */
  public MavenExecutionRequest createExecutionRequest(IFile pom, ResolverConfiguration resolverConfiguration,
      IProgressMonitor monitor) throws CoreException;

  /**
   * PROVISIONAL
   */
  public MavenExecutionRequest createExecutionRequest(IMavenProjectFacade project, IProgressMonitor monitor)
      throws CoreException;

  public void addMavenProjectChangedListener(IMavenProjectChangedListener listener);

  public void removeMavenProjectChangedListener(IMavenProjectChangedListener listener);
}
