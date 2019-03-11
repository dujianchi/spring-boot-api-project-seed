package com.company.project.service.impl;

import com.company.project.dao.SeedUserMapper;
import com.company.project.model.SeedUser;
import com.company.project.service.SeedUserService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/03/11.
 */
@Service
@Transactional
public class SeedUserServiceImpl extends AbstractService<SeedUser> implements SeedUserService {
    @Resource
    private SeedUserMapper seedUserMapper;

}
