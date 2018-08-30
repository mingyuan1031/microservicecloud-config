package com.lwxf.newstore.webapp.facade.quickshare.impl;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.common.UploadFilesService;
import com.lwxf.newstore.webapp.bizservice.quickshare.MicroblogCommentService;
import com.lwxf.newstore.webapp.bizservice.quickshare.MicroblogPraiseService;
import com.lwxf.newstore.webapp.bizservice.quickshare.MicroblogService;
import com.lwxf.newstore.webapp.bizservice.user.UserService;
import com.lwxf.newstore.webapp.common.component.BaseFileUploadComponent;
import com.lwxf.newstore.webapp.common.component.UploadInfo;
import com.lwxf.newstore.webapp.common.component.UploadType;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.quickshare.MicroblogStatus;
import com.lwxf.newstore.webapp.common.enums.quickshare.MicrologType;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;
import com.lwxf.newstore.webapp.domain.entity.quickshare.Microblog;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogComment;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogPraise;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.quickshare.MicroblogFacade;
import com.lwxf.newstore.webapp.facade.user.impl.UserFacadeImpl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：
 *
 * @author：panchenxiao
 * @create：2018/7/3 19:36
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("microblogFacade")
public class MicroblogFacadeImpl extends BaseFacadeImpl implements MicroblogFacade {
	private static final Logger logger = LoggerFactory.getLogger(UserFacadeImpl.class);

	@Resource(name = "microblogService")
	private MicroblogService microblogService;
	@Resource(name = "uploadFilesService")
	private UploadFilesService uploadFilesService;
	@Resource(name = "microblogPraiseService")
	private MicroblogPraiseService microblogPraiseService;
	@Resource(name = "microblogCommentService")
	private MicroblogCommentService microblogCommentService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource
	private BaseFileUploadComponent baseFileUploadComponent;


	/**
	 *  上传帖子并修改图片为正式图片
	 * @param microblog
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult addMicroblogAndImage(Microblog microblog,String[] ids) {

		microblog.setType(MicrologType.COMMON.getValue());
		microblog.setStatus(MicroblogStatus.ENABLED.getValue().intValue());
		microblog.setCreated(DateUtil.getSystemDate());
		microblog.setCreator(WebUtils.getCurrUserId());
		this.microblogService.add(microblog);
		if(null != ids && ids.length != 0){
			String resourceId=microblog.getId();
			String belongId=microblog.getId();
			this.uploadFilesService.updateMicImageStatusAndresourceIdAndbelongId(ids,resourceId,belongId);
		}
		//返回当前帖子
		return ResultFactory.generateRequestResult(this.microblogService.findById(microblog.getId()));
	}


	/**
	 * 上传帖子图片的临时文件
	 * 作者：潘晨霄
	 * @param files
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult uploadMicroblogImage(List<MultipartFile> files) {
		List<UploadFiles> list = new ArrayList<>();
		for (MultipartFile multipartFile : files){
			UploadInfo uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.TEMP,multipartFile,UploadResourceType.QUICKSHARE,"");
			//创建
			UploadFiles  uploadFiles = UploadFiles.create(null,null,uploadInfo,UploadResourceType.QUICKSHARE,UploadType.TEMP);
			//获取上传的ID并赋值
			uploadFiles.setCreator(WebUtils.getCurrUserId());
			//获取上传的时间并赋值
			uploadFiles.setCreated(DateUtil.getSystemDate());
			//获取相对路径
			uploadFiles.setPath(uploadInfo.getRelativePath());
			this.uploadFilesService.add(uploadFiles);
			list.add(this.uploadFilesService.findById(uploadFiles.getId()));
		}
		return ResultFactory.generateRequestResult(list);
	}

	/**
	 * 查询上传的贴子的时候图片是否为空（没用）
	 * @param ids
	 * @return
	 */
	@Override
	public RequestResult isNullByIds(String[] ids) {
		return ResultFactory.generateRequestResult(this.uploadFilesService.isNullByIds(ids));
	}

	/**
	 * 删除帖子的图片(无用)
	 * @param ids
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult deleteMicImage(String[] ids) {
		//删除快享图片
		this.uploadFilesService.deleteMicImage(ids);
		//返回剩余的图片
		String creator = WebUtils.getCurrUserId();
		return ResultFactory.generateRequestResult(this.uploadFilesService.selectByCreatorAndTempAndResourceType(creator));
	}

	/**
	 * 删除帖子
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult deleteMicroblog(String id) {
		Microblog microblog = this.microblogService.findById(id);
		if(null == microblog){
			return ResultFactory.generateSuccessResult();
		}
		User user = WebUtils.getCurrUser();
		// 验证普通用户权限
		if(user.getRole().intValue() == UserRole.MEMBER.getValue()){
			if(!microblog.getCreator().equals(WebUtils.getCurrUserId())){
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NO_PERMISSION_10003,AppBeanInjector.i18nUtil.getMessage("BIZ_NO_PERMISSION_10003"));
			}
		}

		//埋xxxxxxxxxx_baisi
		TSManualData tsManualData = WebUtils.getTSManualData();
		tsManualData.setClazz(Microblog.class);
		tsManualData.setParams(id);
		tsManualData.setEvent(SystemActivityEvent.QUICKSHARE_DELETE.getValue());
		tsManualData.put(WebConstant.KEY_ENTITY_ID,id);
		List<String> listMicImageId = this.uploadFilesService.selectMicImageIdByBlogId(id);
		List<UploadFiles> uploadFiles = this.uploadFilesService.findByBelongId(id);
		if(listMicImageId.size()!=0){
			//list集合转成数组
			String[] MicImageId = listMicImageId.toArray(new String[listMicImageId.size()]);
			//删除帖子图片 TODO：帖子的图片文件也要删除（磁盘上的文件）
			Map<String, UploadResourceType> map = new HashMap<>();
			for (UploadFiles files : uploadFiles) {
				map.put(files.getPath(), UploadResourceType.QUICKSHARE);
			}
			baseFileUploadComponent.deleteFiles(map, (long) uploadFiles.size());
			this.uploadFilesService.deleteMicImage(MicImageId);
		}
		//删除帖子点赞
		this.microblogPraiseService.deleteByBlogId(id);
		//删除帖子评论
		this.microblogCommentService.deleteMicroblogCommentByBlogId(id);
		//删除帖子
		this.microblogService.deleteById(id);
		return ResultFactory.generateSuccessResult(); // TODO：返回成功即可	√
	}




	@Override
	public RequestResult findByLimit(Integer pageNum, Integer pageSize,MapContext mapContext) {
		PaginatedFilter filter = PaginatedFilter.newOne();
		String name = (String) mapContext.get(WebConstant.KEY_ENTITY_NAME);
		List<String> userIds = new ArrayList<>();
		if(null != name){
			userIds = this.userService.findIdByName(name);
			mapContext.remove(WebConstant.KEY_ENTITY_NAME);
		}else if(null != mapContext.get(WebConstant.KEY_ENTITY_CREATOR)){
			userIds.add(WebUtils.getCurrUserId());
		}else {
			userIds = null;
		}
		mapContext.put(WebConstant.KEY_ENTITY_CREATOR, userIds);
		filter.setFilters(mapContext);
		Pagination pagination = Pagination.newOne();
		pagination.setPageNum(pageNum);
		pagination.setPageSize(pageSize);
		filter.setPagination(pagination);
		Map<String,Object> result = new HashMap<>();
		PaginatedList<Microblog> list = this.microblogService.findByType(filter);

		result.put("microblogs", list.getRows());
		Set<String> blogIds = new HashSet<>();
		for(Microblog m : list.getRows()){
			blogIds.add(m.getId());
		}
		// 查询评论列表
		List<MicroblogComment> comments;
		List<UploadFiles> files;
		List<MicroblogPraise> praises;
		if(blogIds.size() == 0){
			comments = new ArrayList<>();
			files = new ArrayList<>();
			praises = new ArrayList<>();
		}else{
			comments = this.microblogCommentService.selectMicroblogCommentByBlogIds(blogIds);
			files = this.uploadFilesService.findByBlogIds(blogIds);
			praises = this.microblogPraiseService.findByBlogIds(blogIds);
		}
		result.put("comments", comments);
		// 查询快享帖子图片
		result.put("files", files);
		// 查询帖子点赞信息
		result.put("praises", praises);
		// 处理当前用户点过赞的帖子ID
		List<MicroblogPraise> userPraises = new ArrayList<>();
		praises.stream().forEach(up ->{
			if(up.getMemberId().equals(WebUtils.getCurrUserId())){
				userPraises.add(up);
			}
		});
		result.put("userPraises", userPraises);
		return ResultFactory.generateRequestResult(result, list.getPagination());
	}

	@Override
	public RequestResult findById(String id) {
		Map<String, Object> result = new HashMap<>();
		result.put("microblogs", this.microblogService.findById(id));
		result.put("comments", this.microblogCommentService.selectMicroblogCommentByBlogId(id));
		result.put("files", this.uploadFilesService.findByBelongId(id));
		Set<String> blogs = new HashSet<>();
		blogs.add(id);
		List<MicroblogPraise> praises;
		if(blogs.size() == 0){
			praises = new ArrayList<>();
		}else {
			praises = this.microblogPraiseService.findByBlogIds(blogs);
		}
		result.put("praises", praises);
		result.put("userPraises", this.microblogPraiseService.findByMemberId(WebUtils.getCurrUserId()));
		return ResultFactory.generateRequestResult(result);
	}



	@Override
	@Transactional
	public RequestResult updateStatus(String id, MapContext mapContext) {
		Object status = mapContext.get(WebConstant.KEY_ENTITY_STATUS);
		// 非法数据验证
		if(null == status || !MicroblogStatus.contains(Byte.valueOf(status.toString()))){
			return ResultFactory.generateErrorResult(ErrorCodes.SYS_ILLEGAL_DATA_00002, AppBeanInjector.i18nUtil.getMessage("SYS_ILLEGAL_DATA_00002"));
		}
		Microblog microblog = this.microblogService.findById(id);
		if(null == microblog){
			return ResultFactory.generateResNotFoundResult();
		}
		// 权限验证
		User currUser = WebUtils.getCurrUser();
		if(currUser.getRole().intValue() == UserRole.MEMBER.getValue()){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NO_PERMISSION_10003,AppBeanInjector.i18nUtil.getMessage("BIZ_NO_PERMISSION_10003"));
		}
		mapContext.clear();
		mapContext.put(WebConstant.KEY_ENTITY_STATUS,status);
		mapContext.put(WebConstant.KEY_ENTITY_ID, id);
		this.microblogService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult() ;
	}

	@Override
	public RequestResult findNameByCreator(String name) {
		List<String> ids = this.userService.findIdByName(name);
		return null;
	}
}
