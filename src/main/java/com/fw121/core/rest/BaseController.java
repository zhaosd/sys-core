package com.fw121.core.rest;

import com.fw121.core.domain.BaseEntity;
import com.fw121.core.domain.R;
import com.fw121.core.domain.SysRole;
import com.fw121.core.service.LocaleMessageService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Created by mvt-zhaosandong-mac on 2018/7/20.
 */
public abstract class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    private Mapper mapper;  // 公共查询类

    private String model;   // 实体名称

    @Autowired
    LocaleMessageService messageService;

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 初始配置mapper和model
     */
    public abstract void init();

    public R list(BaseEntity entity, Condition condition) {
        int offset = (entity.getPage() == null) ? 0 : (entity.getPage() - 1) * entity.getRows();
        RowBounds rowBounds = new RowBounds(offset, entity.getRows());
        List<SysRole> roles = mapper.selectByExampleAndRowBounds(condition, rowBounds);
        Integer total = mapper.selectCountByExample(condition);
        return R.pageData(roles, total);
    }

    public R create(@RequestBody BaseEntity entity) {
        try {
            int result = mapper.insert(entity);
            if (result > 0) {
                return R.ok(entity);
            }
        } catch (DuplicateKeyException e) {
            return R.error(messageService.getMessage(model + ".exist"));
        }
        return R.error(messageService.getMessage(model + ".create.failed"));
    }

    public R update(@RequestBody BaseEntity entity) {
        try {
            int result = mapper.updateByPrimaryKey(entity);
            if (result > 0) {
                return R.ok(entity);
            }
        } catch (DuplicateKeyException e) {
            return R.error(messageService.getMessage(model + ".exist"));
        }
        return R.error(messageService.getMessage(model + ".update.failed"));
    }

    public R remove(@PathVariable Object id) {
        int result = mapper.deleteByPrimaryKey(id);
        if (result > 0) {
            return R.ok(messageService.getMessage(model + ".delete.success"));
        }
        return R.error(messageService.getMessage(model + ".delete.failed"));
    }

}
