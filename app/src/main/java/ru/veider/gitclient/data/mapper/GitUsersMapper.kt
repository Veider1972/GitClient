package ru.veider.gitclient.data.mapper

import ru.veider.gitclient.data.entity.GitUsersEntity
import ru.veider.gitclient.domain.entity.GitUsersData

interface GitUsersMapper {
    fun toGitUsersData(gitUsersEntity: List<GitUsersEntity>): List<GitUsersData>
}