package ru.gubber.citools.nitifiers;

import org.jetbrains.annotations.NotNull;

/**
 * Created by gubber on 23.11.2015.
 */
public class JiraUser {
	@NotNull
	private String host;
	@NotNull
	private String username;
	@NotNull
	private String password;

	public JiraUser(@NotNull String host, @NotNull String username, @NotNull String password) {
		this.host = host;
		this.username = username;
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		JiraUser jiraUser = (JiraUser) o;

		if (!host.equals(jiraUser.host)) return false;
		return username.equals(jiraUser.username);

	}

	@Override
	public int hashCode() {
		int result = host.hashCode();
		result = 31 * result + username.hashCode();
		return result;
	}
}