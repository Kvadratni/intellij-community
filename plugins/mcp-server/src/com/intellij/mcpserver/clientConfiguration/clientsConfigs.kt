@file:OptIn(ExperimentalSerializationApi::class)

package com.intellij.mcpserver.clientConfiguration

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.serialization.json.JsonNames

// used for writing config
@Serializable
sealed class ServerConfig

@Serializable
class CursorSSEConfig(val url: String) : ServerConfig()

@Serializable
class WindsurfSSEConfig(val serverUrl: String) : ServerConfig()

@Serializable
class VSCodeSSEConfig(val url: String, val type: String) : ServerConfig()

@Serializable
class ClaudeCodeSSEConfig(val url: String, val type: String) : ServerConfig()

@Serializable
class GooseSSEConfig(val url: String, val type: String) : ServerConfig()

@Serializable
class STDIOServerConfig(
  val command: String? = null,
  val args: List<String>? = null,
  val env: Map<String, String>? = null,
) : ServerConfig()

// used for reading existing configs

@Serializable
@JsonIgnoreUnknownKeys
class ExistingConfig(
  val command: String? = null,
  val args: List<String>? = null,
  val env: Map<String, String>? = null,
  @JsonNames("url", "serverUrl")
  val url: String? = null,
  val type: String? = null,
)

@JsonIgnoreUnknownKeys
@Serializable
data class McpServers(
  @JsonNames("servers", "mcpServers")
  val mcpServers: Map<String, ExistingConfig>,
)

@JsonIgnoreUnknownKeys
@Serializable
data class VSCodeConfig(
  val servers: Map<String, ExistingConfig>? = null
)

// Note: Goose uses YAML config with extensions, not JSON with mcpServers
// This is a placeholder for compatibility - actual Goose detection would need YAML parsing
@JsonIgnoreUnknownKeys
@Serializable
data class GooseConfig(
  val extensions: Map<String, GooseExtension>? = null
)

@JsonIgnoreUnknownKeys
@Serializable
data class GooseExtension(
  val cmd: String? = null,
  val args: List<String>? = null,
  val envs: Map<String, String>? = null,
  val type: String? = null,
  val enabled: Boolean? = null
)