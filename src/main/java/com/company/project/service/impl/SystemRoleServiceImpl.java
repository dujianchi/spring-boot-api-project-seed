package com.company.project.service.impl;

import com.company.project.dao.SystemRoleMapper;
import com.company.project.model.SystemRole;
import com.company.project.service.SystemRoleService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/03/11.
 */
@Service
@Transactional
public class SystemRoleServiceImpl extends AbstractService<SystemRole> implements SystemRoleService {
    @Resource
    private SystemRoleMapper systemRoleMapper;

}
