package com.zwq.controller;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @Author Acer
 * @Date 2021/08/21 14:12
 * @Version 1.0
 */

@RestController
public class GatewayFlowRulesController {
    /**
     * 获取当前系统的限流策略
     */
    @GetMapping("/gw/flow/rules")
    public Set<GatewayFlowRule> getCurrentGatewayFlowRules(){
        return GatewayRuleManager.getRules();
    }
    /**
     *获取定义API分组
     */
    @GetMapping("/gw/api/groups")
    public Set<ApiDefinition> getApiGoups(){
        return GatewayApiDefinitionManager.getApiDefinitions();
    }
}
