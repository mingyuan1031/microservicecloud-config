package com.lwxf.newstore.webapp.controller.api.clerks;

import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.facade.clerk.ClerkFacade;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author dazhen（1299501979@qq.com）
 * @create 2018-07-03 14:30
 * @desc 店员管理controller
 **/

@RestController
@RequestMapping(value = "/api",produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class ClerkController {
    @Resource(name = "clerkFacade")
    private ClerkFacade clerkFacade;
//    @Resource
//    private UserThirdInfoService userThirdInfoService;

    /**
     * 删除店员（修改该用户的店员角色为普通用户角色）
     * @param id
     * @return
     */
    @DeleteMapping("/clerks/{id}")
    public RequestResult deleteClerk(@PathVariable String id){

        return clerkFacade.updateUserState(id);
    }

    @GetMapping("/clerks")
    public RequestResult findListByParams(){
        return clerkFacade.findClerkListByStateAndRole();//获取的所有的user对象
    }

//    @GetMapping("/clerks")
//    public RequestResult findaaa(){
//
//        return this.clerkFacade.findAllClerks();//获取的是所有的userThirdInfo对象
//    }

    @PutMapping("/clerks/{id}/roles/{roleValue}")
    public RequestResult editClerk(@PathVariable String id,@PathVariable Integer roleValue){
        return clerkFacade.updateUser(id,roleValue);
    }

}

