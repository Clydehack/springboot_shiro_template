package com.template.shirotemplate.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

	/**
	 * shiro核心3个api：
	 * 
	 * 		(方法都是自上而下)
	 * 		Subject： 			用户主体		把操作交给SecurityManager
	 * 		SecurityManager：	安全管理器		关联Realm
	 * 		Realm:				认证器		这是Shiro连接数据的桥梁
	 */
	
	/** ShiroFilterFactoryBean - shiro过滤器配置 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();	// 1.定义ShiroFilterFactoryBean
		shiroFilter.setSecurityManager(getSecurityManager());				// 2.设置安全管理器 - SecurityManager
		
		/**
		 * 3.配置拦截器 - 添加shiro内置过滤器,可实现权限相关的拦截:
		 * 
		 *  (常用过滤器)
		 * 		1.anon 	无需登陆(认证)可以访问
		 * 		2.authc 必须认证才可访问
		 * 		3.user 	使用rememberMe功能可以直接访问
		 * 		4.perms	改资源必须得到资源权限才可访问
		 * 		5.role 	必须得到角色权限才可访问
		 */
		Map<String, String> filterMap = new LinkedHashMap<>(); // 设置拦截权限和路径
		filterMap.put("/insert", "authc");
		filterMap.put("/update", "authc");
		shiroFilter.setFilterChainDefinitionMap(filterMap);
		// 4.返回
		return shiroFilter;
	}
	
	/** DefaultWebSecurityManagerBean - 安全管理器配置 */
	@Bean
	public DefaultWebSecurityManager getSecurityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(getShiroRelam());		// 关联下面的自定义Realm
		return securityManager;
	}
	
	/** Realm - 认证器配置 */
	@Bean
	public Realm getShiroRelam() {
		Realm realm = new Realm();
		realm.setCredentialsMatcher(hashedCredentialsMatcher()); // 设置加密方式
		return realm;
	}
	
	/* Realm - 加密方式配置 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher(){
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");	// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);			// 散列的次数，比如散列两次，相当于 md5(md5(""));
		return hashedCredentialsMatcher;
	}

}