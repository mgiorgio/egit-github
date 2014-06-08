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
package org.eclipse.egit.github.core.tests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import junit.framework.Assert;

import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.GitHubRequest;
import org.eclipse.egit.github.core.client.GitHubResponse;
import org.eclipse.egit.github.core.service.ActivityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests of {@link ActivityService}
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceTest {

	@Mock
	private GitHubClient client;

	@Mock
	private GitHubResponse response;

	private ActivityService service;

	/**
	 * Test case set up
	 *
	 * @throws IOException
	 */
	@Before
	public void before() throws IOException {
		doReturn(response).when(client).get(any(GitHubRequest.class));
		service = new ActivityService(client);
	}

	/**
	 * <p>
	 * Given the {@link ActivityService} for the GitHub API v3
	 * </p>
	 * <p>
	 * when a repository is marked to be starred
	 * </p>
	 * <p>
	 * then the Activity-Starring API should be invoked using PUT and including
	 * the owner and repository name.
	 * </p>
	 *
	 * @throws IOException
	 */
	@Test
	public void givenAPIv3WhenRepositoryIsStarredThenActivityStarringAPIInvoked() throws IOException {
		RepositoryId repo = new RepositoryId("u", "p"); //$NON-NLS-1$ $NON-NLS-2$

		service.starRepository(repo);
		verify(client).put("/user/starred/u/p"); //$NON-NLS-1$
	}

	@Test
	public void givenANullRepositoryWhenStarringThenIllegalArgumentExceptionMustBeThrown() throws IOException {
		try {
			service.starRepository(null);
			Assert.fail("IllegalArgumentException should have been thrown."); //$NON-NLS-1$
		} catch (IllegalArgumentException e) {
			// expected.
		}
	}

	/**
	 * <p>
	 * Given the {@link ActivityService} for the GitHub API v3
	 * </p>
	 * <p>
	 * when a repository is marked to be unstarred
	 * </p>
	 * <p>
	 * then the Activity-Starring API should be invoked using DELETE and
	 * including the owner and repository name.
	 * </p>
	 *
	 * @throws IOException
	 */
	@Test
	public void givenAPIv3WhenRepositoryIsUnstarredThenActivityStarringAPIInvoked() throws IOException {
		RepositoryId repo = new RepositoryId("u", "p"); //$NON-NLS-1$ $NON-NLS-2$

		service.unstarRepository(repo);
		verify(client).delete("/user/starred/u/p"); //$NON-NLS-1$
	}

	/**
	 * <p>
	 * Given the {@link ActivityService} for the GitHub API v3
	 * </p>
	 * <p>
	 * when a repository is queried to know if it is starred by a user
	 * </p>
	 * <p>
	 * then the Activity-Starring API should be invoked using GET and including
	 * the owner and repository name.
	 * </p>
	 *
	 * @throws IOException
	 */
	@Test
	public void givenAPIv3WhenRepositoryIsQueriedForStarredThenActivityStarringAPIInvoked() throws IOException {
		RepositoryId repo = new RepositoryId("u", "p"); //$NON-NLS-1$ $NON-NLS-2$

		service.iAmStarringRepository(repo);
		GitHubRequest request = new GitHubRequest();
		request.setUri("/user/starred/u/p"); //$NON-NLS-1$
		verify(client).get(request);
	}
}