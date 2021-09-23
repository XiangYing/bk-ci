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

package com.tencent.devops.store.resources.common

import com.tencent.devops.common.api.constant.CommonMessageCode
import com.tencent.devops.common.api.exception.ErrorCodeException
import com.tencent.devops.common.api.pojo.Result
import com.tencent.devops.common.client.ClientTokenService
import com.tencent.devops.common.service.utils.SpringContextUtil
import com.tencent.devops.common.web.RestResource
import com.tencent.devops.store.api.common.ServiceStoreResource
import com.tencent.devops.store.pojo.common.SensitiveConfResp
import com.tencent.devops.store.pojo.common.StoreBuildResultRequest
import com.tencent.devops.store.pojo.common.enums.StoreTypeEnum
import com.tencent.devops.store.service.common.StoreBuildService
import com.tencent.devops.store.service.common.StoreCommonService
import com.tencent.devops.store.service.common.StoreMemberService
import com.tencent.devops.store.service.common.StoreProjectService
import com.tencent.devops.store.service.common.UserSensitiveConfService
import org.springframework.beans.factory.annotation.Autowired

@RestResource
class ServiceStoreResourceImpl @Autowired constructor(
    private val storeProjectService: StoreProjectService,
    private val sensitiveConfService: UserSensitiveConfService,
    private val storeBuildService: StoreBuildService,
    private val storeCommonService: StoreCommonService,
    private val clientTokenService: ClientTokenService
) : ServiceStoreResource {

    override fun uninstall(storeCode: String, storeType: StoreTypeEnum, projectCode: String): Result<Boolean> {
        return storeProjectService.uninstall(storeType, storeCode, projectCode)
    }

    override fun getSensitiveConf(storeType: StoreTypeEnum, storeCode: String): Result<List<SensitiveConfResp>?> {
        return sensitiveConfService.list("", storeType, storeCode, true)
    }

    override fun handleStoreBuildResult(
        pipelineId: String,
        buildId: String,
        storeBuildResultRequest: StoreBuildResultRequest
    ): Result<Boolean> {
        return storeBuildService.handleStoreBuildResult(pipelineId, buildId, storeBuildResultRequest)
    }

    override fun isStoreMember(storeCode: String, storeType: StoreTypeEnum, userId: String): Result<Boolean> {
        return Result(
            SpringContextUtil.getBean(
                clazz = StoreMemberService::class.java,
                beanName = "${storeType.name.toLowerCase()}MemberService"
            ).isStoreMember(userId, storeCode, storeType.type.toByte())
        )
    }

    override fun validateProjectAtomPermission(
        token: String,
        projectCode: String,
        storeCode: String,
        storeType: StoreTypeEnum
    ): Result<Boolean> {
        // 校验token是否合法
        val validateTokenFlag = clientTokenService.checkToken(null, token)
        if (!validateTokenFlag) {
            throw ErrorCodeException(
                errorCode = CommonMessageCode.PARAMETER_IS_INVALID,
                params = arrayOf(token)
            )
        }
        return Result(
            storeCommonService.getStorePublicFlagByCode(storeCode, storeType) ||
                storeProjectService.isInstalledByProject(
                    projectCode = projectCode,
                    storeCode = storeCode,
                    storeType = StoreTypeEnum.ATOM.type.toByte()
                )
        )
    }
}
