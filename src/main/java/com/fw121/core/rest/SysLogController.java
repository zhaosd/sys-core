package com.fw121.core.rest;

import com.fw121.core.domain.R;
import com.fw121.core.domain.SysLog;
import com.fw121.core.mapper.SysLogMapper;
import com.fw121.core.service.LocaleMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags ="日志管理")
@RequiresPermissions("sys.log.view")
@RestController
@RequestMapping(value="/log")
public class SysLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SysLogController.class);

	private static final String model = "log";

	@Autowired
	SysLogMapper logMapper;

    @Autowired
    LocaleMessageService messageService;

	@Override
	public void init() {
		super.setMapper(logMapper);
		super.setModel(model);
	}

	@ApiOperation(value="获取列表")
	@RequestMapping(value="list",method = RequestMethod.GET)
    public R list(SysLog log) {
		int offset = (log.getPage() == null) ? 0 : (log.getPage() - 1) * log.getRows();
		if (log.getAction() != null) {
			String actionCondition = "%" + log.getAction() + "%";
			List<SysLog> logs = logMapper.selectPageDataByAction(actionCondition, offset, log.getRows());
			Integer total = logMapper.selectCountByAction(actionCondition);
			return R.pageData(logs, total);
		} else {
			List<SysLog> logs = logMapper.selectAllPageData(offset, log.getRows());
			Integer total = logMapper.selectCount(log);
			return R.pageData(logs, total);
		}
    }

	@ApiOperation(value="添加日志")
	@RequestMapping(value="create",method = RequestMethod.POST)
	public R create(@RequestBody SysLog log) {
		init();
		return super.create(log);
	}

	@ApiOperation(value="编辑日志")
	@RequestMapping(value="update",method = RequestMethod.POST)
	public R update(@RequestBody SysLog log) {
		init();
		return super.update(log);
	}

	@ApiOperation(value="删除日志")
	@GetMapping("/remove/{id}")
	public R remove(@PathVariable Object id) {
		init();
		return super.remove(id);
	}

}
