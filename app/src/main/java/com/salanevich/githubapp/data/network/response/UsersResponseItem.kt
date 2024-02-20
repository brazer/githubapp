package com.salanevich.githubapp.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponseItem(
	@SerialName("gists_url") val gistsUrl: String,
	@SerialName("repos_url") val reposUrl: String,
	@SerialName("following_url") val followingUrl: String,
	@SerialName("starred_url") val starredUrl: String,
	@SerialName("login") val login: String,
	@SerialName("followers_url") val followersUrl: String,
	@SerialName("type") val type: String,
	@SerialName("url") val url: String,
	@SerialName("subscriptions_url") val subscriptionsUrl: String,
	@SerialName("received_events_url") val receivedEventsUrl: String,
	@SerialName("avatar_url") val avatarUrl: String,
	@SerialName("events_url") val eventsUrl: String,
	@SerialName("html_url") val htmlUrl: String,
	@SerialName("site_admin") val siteAdmin: Boolean,
	@SerialName("id") val id: Int,
	@SerialName("gravatar_id") val gravatarId: String,
	@SerialName("node_id") val nodeId: String,
	@SerialName("organizations_url") val organizationsUrl: String
)
