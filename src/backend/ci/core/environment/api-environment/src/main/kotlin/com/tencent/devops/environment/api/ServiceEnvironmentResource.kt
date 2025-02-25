/*
 * Tencent is pleased to support the open source community by making BK-CI 蓝鲸持续集成平台 available.
 *
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * BK-CI 蓝鲸持续集成平台 is licensed under the MIT license.
 *
 * A copy of the MIT License is included in this file.
 *
 *
 * Terms of the MIT License:
 * ---------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.tencent.devops.environment.api

import com.tencent.devops.common.api.auth.AUTH_HEADER_USER_ID
import com.tencent.devops.common.api.auth.AUTH_HEADER_USER_ID_DEFAULT_VALUE
import com.tencent.devops.common.api.pojo.OS
import com.tencent.devops.common.api.pojo.Result
import com.tencent.devops.environment.pojo.EnvCreateInfo
import com.tencent.devops.environment.pojo.EnvWithNodeCount
import com.tencent.devops.environment.pojo.EnvWithPermission
import com.tencent.devops.environment.pojo.EnvironmentId
import com.tencent.devops.environment.pojo.NodeBaseInfo
import com.tencent.devops.environment.pojo.SharedProjectInfoWrap
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.HeaderParam
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

@Tag(name = "SERVICE_ENVIRONMENT", description = "服务-环境服务")
@Path("/service/environment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
interface ServiceEnvironmentResource {
    @Operation(summary = "获取环境列表")
    @GET
    @Path("/projects/{projectId}")
    fun list(
        @Parameter(description = "用户ID", required = true, example = AUTH_HEADER_USER_ID_DEFAULT_VALUE)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "项目ID", required = true)
        @PathParam("projectId")
        projectId: String
    ): Result<List<EnvWithPermission>>

    @Operation(summary = "创建环境")
    @POST
    @Path("/projects/{projectId}")
    fun create(
        @Parameter(description = "用户ID", required = true, example = AUTH_HEADER_USER_ID_DEFAULT_VALUE)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "项目ID", required = true)
        @PathParam("projectId")
        projectId: String,
        @Parameter(description = "环境信息", required = true)
        environment: EnvCreateInfo
    ): Result<EnvironmentId>

    @Operation(summary = "删除环境")
    @DELETE
    @Path("/projects/{projectId}/envs/{envHashId}")
    fun delete(
        @Parameter(description = "用户ID", required = true, example = AUTH_HEADER_USER_ID_DEFAULT_VALUE)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "项目ID", required = true)
        @PathParam("projectId")
        projectId: String,
        @Parameter(description = "环境 hashId", required = true)
        @PathParam("envHashId")
        envHashId: String
    ): Result<Boolean>

    @Operation(summary = "添加节点到环境")
    @POST
    @Path("/projects/{projectId}/envs/{envHashId}/add_nodes")
    fun addNodes(
        @Parameter(description = "用户ID", required = true, example = AUTH_HEADER_USER_ID_DEFAULT_VALUE)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "项目ID", required = true)
        @PathParam("projectId")
        projectId: String,
        @Parameter(description = "环境 hashId", required = true)
        @PathParam("envHashId")
        envHashId: String,
        @Parameter(description = "节点 HashId", required = true)
        nodeHashIds: List<String>
    ): Result<Boolean>

    @Operation(summary = "从环境删除节点")
    @POST
    @Path("/projects/{projectId}/envs/{envHashId}/delete_nodes")
    fun deleteNodes(
        @Parameter(description = "用户ID", required = true, example = AUTH_HEADER_USER_ID_DEFAULT_VALUE)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "项目ID", required = true)
        @PathParam("projectId")
        projectId: String,
        @Parameter(description = "环境 hashId", required = true)
        @PathParam("envHashId")
        envHashId: String,
        @Parameter(description = "节点 HashId", required = true)
        nodeHashIds: List<String>
    ): Result<Boolean>

    @Operation(summary = "获取环境（多个）的节点列表")
    @POST
    @Path("/projects/{projectId}/listNodesByEnvIds")
    fun listNodesByEnvIds(
        @Parameter(description = "用户ID", required = true, example = AUTH_HEADER_USER_ID_DEFAULT_VALUE)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "项目ID", required = true)
        @PathParam("projectId")
        projectId: String,
        @Parameter(description = "环境 hashId(s)", required = true)
        envHashIds: List<String>
    ): Result<List<NodeBaseInfo>>

    @Operation(summary = "获取用户有权限使用的环境列表")
    @GET
    @Path("/projects/{projectId}/listUsableServerEnvs")
    fun listUsableServerEnvs(
        @Parameter(description = "用户ID", required = true, example = AUTH_HEADER_USER_ID_DEFAULT_VALUE)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "项目ID", required = true)
        @PathParam("projectId")
        projectId: String
    ): Result<List<EnvWithPermission>>

    @Operation(summary = "根据hashId(多个)获取环境信息(不校验权限)")
    @POST
    @Path("/projects/{projectId}/listRawByEnvHashIds")
    fun listRawByEnvHashIds(
        @Parameter(description = "用户ID", required = true, example = AUTH_HEADER_USER_ID_DEFAULT_VALUE)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "项目ID", required = true)
        @PathParam("projectId")
        projectId: String,
        @Parameter(description = "环境 hashId(s)", required = true)
        envHashIds: List<String>
    ): Result<List<EnvWithPermission>>

    @Operation(summary = "根据环境名称获取环境信息(不校验权限)")
    @POST
    @Path("/projects/{projectId}/listRawByEnvNames")
    fun listRawByEnvNames(
        @Parameter(description = "用户ID", required = true, example = AUTH_HEADER_USER_ID_DEFAULT_VALUE)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "项目ID", required = true)
        @PathParam("projectId")
        projectId: String,
        @Parameter(description = "环境名称(s)", required = true)
        envNames: List<String>
    ): Result<List<EnvWithPermission>>

    @Operation(summary = "根据OS获取第三方构建环境列表")
    @GET
    @Path("/projects/{projectId}/buildEnvs")
    fun listBuildEnvs(
        @Parameter(description = "用户ID", required = true, example = AUTH_HEADER_USER_ID_DEFAULT_VALUE)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "项目ID", required = true)
        @PathParam("projectId")
        projectId: String,
        @Parameter(description = "操作系统", required = true)
        @QueryParam("os")
        os: OS
    ): Result<List<EnvWithNodeCount>>

    @Operation(summary = "设置环境共享")
    @POST
    @Path("/{projectId}/{envHashId}/share")
    fun setShareEnv(
        @Parameter(description = "用户ID", required = true, example = AUTH_HEADER_USER_ID_DEFAULT_VALUE)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "项目ID", required = true)
        @PathParam("projectId")
        projectId: String,
        @Parameter(description = "环境 hashId", required = true)
        @PathParam("envHashId")
        envHashId: String,
        @Parameter(description = "共享的项目列表", required = true)
        sharedProjects: SharedProjectInfoWrap
    ): Result<Boolean>
}
