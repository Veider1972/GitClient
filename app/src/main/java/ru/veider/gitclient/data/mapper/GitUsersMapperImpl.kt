package ru.veider.gitclient.data.mapper

import ru.veider.gitclient.data.entity.GitUsersEntity
import ru.veider.gitclient.domain.entity.GitUsersData

class GitUsersMapperImpl : GitUsersMapper {
    override fun toGitUsersData(gitUsersEntity: List<GitUsersEntity>): List<GitUsersData> {
        return gitUsersEntity.map {
            GitUsersData(it.login, it.id, it.avatarURL, it.htmlURL)
        }
    }
}