/******************************************************************************
 *  Copyright (c) 2014 GitHub Inc.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *    Matias Giorgio - GitHub API v3 partial Activity Starring implementation
 *****************************************************************************/
package org.eclipse.egit.github.core.service;

import java.io.IOException;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.IGitHubConstants;

/**
 * Activity service class.
 *
 * @see <a href="http://developer.github.com/v3/users">GitHub users API
 *      documentation</a>
 * @see <a href="http://developer.github.com/v3/users/followers">GitHub
 *      followers API documentation</a>
 * @see <a href="http://developer.github.com/v3/users/emails">GitHub user email
 *      API documentation</a>
 * @see <a href="http://developer.github.com/v3/users/keys">GitHub user keys API
 *      documentation</a>
 */
public class ActivityService extends GitHubService {

	/**
	 * Create activity service.
	 */
	public ActivityService() {
		super();
	}

	/**
	 * Create activity service
	 *
	 * @param client
	 */
	public ActivityService(GitHubClient client) {
		super(client);
	}

	/**
	 * @see <a
	 *      href="https://developer.github.com/v3/activity/starring/#star-a-repository">Star
	 *      a repository</a>
	 *
	 * @param repository
	 * @throws IOException
	 */
	public void starRepository(IRepositoryIdProvider repository) throws IOException {
		if (repository == null) {
			throw new IllegalArgumentException("Repository cannot be null"); //$NON-NLS-1$
		}

		StringBuilder uri = new StringBuilder();
		uri.append(IGitHubConstants.SEGMENT_USER).append(IGitHubConstants.SEGMENT_STARRED);
		uri.append("/").append(repository.generateId()); //$NON-NLS-1$
		client.put(uri.toString());
	}

	/**
	 * @see <a
	 *      href="https://developer.github.com/v3/activity/starring/#unstar-a-repository">Unstar
	 *      a repository</a>
	 *
	 * @param repository
	 * @throws IOException
	 */
	public void unstarRepository(IRepositoryIdProvider repository) throws IOException {
		if (repository == null) {
			throw new IllegalArgumentException("Repository cannot be null"); //$NON-NLS-1$
		}

		StringBuilder uri = new StringBuilder();
		uri.append(IGitHubConstants.SEGMENT_USER).append(IGitHubConstants.SEGMENT_STARRED);
		uri.append("/").append(repository.generateId()); //$NON-NLS-1$
		client.delete(uri.toString());
	}

	/**
	 * @see <a
	 *      https://developer.github.com/v3/activity/starring/#check-if-you-are
	 *      -starring-a-repository">Check if you are starring a repository</a>
	 *
	 * @param repository
	 * @throws IOException
	 */
	public boolean iAmStarringRepository(IRepositoryIdProvider repository) throws IOException {
		if (repository == null) {
			throw new IllegalArgumentException("Repository cannot be null"); //$NON-NLS-1$
		}

		StringBuilder uri = new StringBuilder();
		uri.append(IGitHubConstants.SEGMENT_USER).append(IGitHubConstants.SEGMENT_STARRED);
		uri.append("/").append(repository.generateId()); //$NON-NLS-1$
		return check(uri.toString());
	}
}
